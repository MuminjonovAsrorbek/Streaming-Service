package uz.dev.streamingservice.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.streamingservice.dto.response.ContentDTO;
import uz.dev.streamingservice.dto.response.PageableDTO;
import uz.dev.streamingservice.dto.request.*;
import uz.dev.streamingservice.entity.*;
import uz.dev.streamingservice.entity.template.AbsLongEntity;
import uz.dev.streamingservice.exceptions.EntityNotFound;
import uz.dev.streamingservice.mapper.ContentMapper;
import uz.dev.streamingservice.repository.*;
import uz.dev.streamingservice.service.template.ContentService;
import uz.dev.streamingservice.utils.CommonUtil;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 16:20
 **/

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    private final GenreRepository genreRepository;

    private final ActorRepository actorRepository;

    private final EntityManager entityManager;

    private final UserRepository userRepository;

    private final RatingRepository ratingRepository;

    @Override
    public PageableDTO getAll(Integer page, Integer pageSize) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<Content> contentPage = contentRepository.findAll(pageable);

        List<Content> contents = contentPage.getContent();

        List<ContentDTO> contentDTOS = contents.stream().map(contentMapper::toDTO).toList();

        return new PageableDTO(
                contentPage.getTotalPages(),
                contentPage.getTotalElements(),
                !contentPage.isLast(),
                !contentPage.isFirst(),
                contentDTOS
        );

    }

    @Override
    public PageableDTO getAllByFilter(ContentFilterDTO dto) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Content> cq = cb.createQuery(Content.class);
        Root<Content> root = cq.from(Content.class);

        List<Predicate> predicates = buildPredicates(cb, root, dto);

        cq.where(predicates.toArray(new Predicate[0])).distinct(true);
        cq.orderBy(cb.asc(root.get(AbsLongEntity.Fields.id)));

        TypedQuery<Content> query = entityManager.createQuery(cq);

        int page = dto.getPage() != null ? dto.getPage() : 0;
        int size = 10;
        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Content> contents = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Content> countRoot = countQuery.from(Content.class);
        List<Predicate> countPredicates = buildPredicates(cb, countRoot, dto);

        countQuery.select(cb.countDistinct(countRoot)).where(countPredicates.toArray(new Predicate[0]));
        Long totalElements = entityManager.createQuery(countQuery).getSingleResult();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        PageableDTO pageableDTO = new PageableDTO();
        pageableDTO.setObjects(new ArrayList<>(contents));
        pageableDTO.setTotalElements(totalElements);
        pageableDTO.setTotalPages(totalPages);
        pageableDTO.setHasNext(page + 1 < totalPages);
        pageableDTO.setHasPrevious(page > 0);

        return pageableDTO;
    }

    @Override
    @Transactional
    public void rateContent(Long contentId, RateContentDTO rateContentDTO) {

        Optional<Content> optionalContent = contentRepository.findById(contentId);

        if (optionalContent.isEmpty())
            throw new EntityNotFound("Content not found with ID : " + contentId, HttpStatus.NOT_FOUND);

        Content content = optionalContent.get();

        Optional<User> optionalUser = userRepository.findById(rateContentDTO.getUserId());

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + rateContentDTO.getUserId(), HttpStatus.NOT_FOUND);

        User user = optionalUser.get();

        Rating rating = new Rating(
                rateContentDTO.getRate(),
                rateContentDTO.getComment(),
                user,
                content
        );

        content.getRatings().add(rating);

        contentRepository.save(content);

    }

    @Override
    @Transactional
    public void watchContent(Long id, WatchContentDTO contentDTO) {

        Optional<Content> optionalContent = contentRepository.findById(id);

        if (optionalContent.isEmpty())
            throw new EntityNotFound("Content not found with ID : " + id, HttpStatus.NOT_FOUND);

        Content content = optionalContent.get();

        Optional<User> optionalUser = userRepository.findById(contentDTO.getUserId());

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + contentDTO.getUserId(), HttpStatus.NOT_FOUND);

        User user = optionalUser.get();

        List<WatchHistory> histories = user.getHistories();

        for (WatchHistory history : histories) {

            if (history.getContent().equals(content)) {

                history.setProgress(contentDTO.getProgressInMinutes());
                history.setWatchDate(LocalDate.now());

                userRepository.save(user);

                return;

            }

        }

        WatchHistory watchHistory = new WatchHistory();

        watchHistory.setWatchDate(LocalDate.now());
        watchHistory.setContent(content);
        watchHistory.setProgress(contentDTO.getProgressInMinutes());
        watchHistory.setUser(user);

        histories.add(watchHistory);

        user.setHistories(histories);

        userRepository.save(user);

    }

    @Override
    public Double getAvgRating(Long id) {

        Optional<Content> optionalContent = contentRepository.findById(id);

        if (optionalContent.isEmpty())
            throw new EntityNotFound("Content not found with ID : " + id, HttpStatus.NOT_FOUND);


        return ratingRepository.findAverageRatingByKContentId(id);
    }

    @Override
    @Transactional
    public void addActors(CreateActorInContentDTO actorDTO, Long id) {

        Optional<Content> optionalContent = contentRepository.findById(id);

        if (optionalContent.isEmpty())
            throw new EntityNotFound("Content not found with ID : " + id, HttpStatus.NOT_FOUND);

        Content content = optionalContent.get();

        Set<Actor> actors = CommonUtil.getOrDefault(content.getActors(), new HashSet<>());

        for (Long actorId : actorDTO.getActorIds()) {

            Optional<Actor> optionalActor = actorRepository.findById(actorId);

            if (optionalActor.isEmpty())
                throw new EntityNotFound("Actor not found with ID : " + actorId, HttpStatus.NOT_FOUND);

            actors.add(optionalActor.get());
        }

        content.setActors(actors);

        contentRepository.save(content);

    }

    @Override
    @Transactional
    public void addGenres(CreateGenreInContentDTO genreDTO, Long id) {

        Optional<Content> optionalContent = contentRepository.findById(id);

        if (optionalContent.isEmpty())
            throw new EntityNotFound("Content not found with ID : " + id, HttpStatus.NOT_FOUND);

        Content content = optionalContent.get();

        Set<Genre> genres = CommonUtil.getOrDefault(content.getGenres(), new HashSet<>());

        for (Long genreId : genreDTO.getGenreIds()) {

            Optional<Genre> optionalGenre = genreRepository.findById(genreId);

            if (optionalGenre.isEmpty())
                throw new EntityNotFound("Genre not found with ID : " + genreId, HttpStatus.NOT_FOUND);

            genres.add(optionalGenre.get());
        }

        content.setGenres(genres);

    }

    @Override
    @Transactional
    public void create(CreateContentDTO contentDTO) {

        Set<Genre> genres = getGenres(contentDTO);

        Set<Actor> actors = getActors(contentDTO);

        Content content = new Content(
                contentDTO.getName(),
                contentDTO.getDescription(),
                contentDTO.getPublishedYear(),
                contentDTO.getDuration(),
                contentDTO.getContentType(),
                contentDTO.getAgeLimit(),
                contentDTO.getPremiumStatus(),
                genres,
                actors,
                new ArrayList<>(),
                new ArrayList<>()
        );

        contentRepository.save(content);

    }

    @Override
    @Transactional
    public void update(CreateContentDTO contentDTO, Long id) {

        Optional<Content> optionalContent = contentRepository.findById(id);

        if (optionalContent.isEmpty())
            throw new EntityNotFound("Content not found with ID : " + id, HttpStatus.BAD_REQUEST);

        Content content = optionalContent.get();

        content.setName(contentDTO.getName());
        content.setDescription(contentDTO.getDescription());
        content.setPremiumStatus(contentDTO.getPremiumStatus());
        content.setDuration(contentDTO.getDuration());
        content.setContentType(contentDTO.getContentType());
        content.setAgeLimit(contentDTO.getAgeLimit());
        content.setPremiumStatus(contentDTO.getPremiumStatus());
        content.setActors(getActors(contentDTO));
        content.setGenres(getGenres(contentDTO));

        contentRepository.save(content);

    }

    @Override
    @Transactional
    public ContentDTO getById(Long id) {

        Optional<Content> optionalContent = contentRepository.findById(id);

        if (optionalContent.isPresent()) {

            Content content = optionalContent.get();

            return contentMapper.toDTO(content);

        }

        throw new EntityNotFound("Content not found with ID : " + id, HttpStatus.BAD_REQUEST);

    }

    @Override
    public void delete(Long id) {

        Optional<Content> optionalContent = contentRepository.findById(id);

        if (optionalContent.isEmpty())
            throw new EntityNotFound("Content not found with ID : " + id, HttpStatus.BAD_REQUEST);

        contentRepository.delete(optionalContent.get());

    }

    private Set<Actor> getActors(CreateContentDTO contentDTO) {
        Set<Actor> actors = new HashSet<>();

        for (Long id : contentDTO.getActorsId()) {

            Optional<Actor> actorOptional = actorRepository.findById(id);

            if (actorOptional.isEmpty()) {

                throw new EntityNotFound("Actor not found with ID : " + id, HttpStatus.BAD_REQUEST);

            }

            actors.add(actorOptional.get());

        }
        return actors;
    }

    private Set<Genre> getGenres(CreateContentDTO contentDTO) {
        Set<Genre> genres = new HashSet<>();

        for (Long id : contentDTO.getGenresId()) {

            Optional<Genre> genreOptional = genreRepository.findById(id);

            if (genreOptional.isEmpty()) {

                throw new EntityNotFound("Genre not found with ID : " + id, HttpStatus.BAD_REQUEST);

            }

            genres.add(genreOptional.get());
        }
        return genres;
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Content> root, ContentFilterDTO dto) {
        List<Predicate> predicates = new ArrayList<>();

        if (dto.getName() != null && !dto.getName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get(Content.Fields.name)), "%" + dto.getName().toLowerCase() + "%"));
        }
        if (dto.getPublishedYear() != null) {
            predicates.add(cb.equal(root.get(Content.Fields.publishedYear), dto.getPublishedYear()));
        }
        if (dto.getContentType() != null) {
            predicates.add(cb.equal(root.get(Content.Fields.contentType), dto.getContentType()));
        }
        if (dto.getAgeLimit() != null) {
            predicates.add(cb.equal(root.get(Content.Fields.ageLimit), dto.getAgeLimit()));
        }
        if (dto.getPremiumStatus() != null) {
            predicates.add(cb.equal(root.get(Content.Fields.premiumStatus), dto.getPremiumStatus()));
        }
        if (dto.getGenre() != null && !dto.getGenre().isBlank()) {
            Join<Content, Genre> genreJoin = root.join(Content.Fields.genres, JoinType.LEFT);
            predicates.add(cb.like(cb.lower(genreJoin.get(Genre.Fields.name)), "%" + dto.getGenre().toLowerCase() + "%"));
        }
        if (dto.getActor() != null && !dto.getActor().isBlank()) {
            Join<Content, Actor> actorJoin = root.join(Content.Fields.actors, JoinType.LEFT);
            predicates.add(cb.like(cb.lower(actorJoin.get(Actor.Fields.fullName)), "%" + dto.getActor().toLowerCase() + "%"));
        }
        if (dto.getMinRating() != null) {
            Join<Content, Rating> ratingJoin = root.join(Content.Fields.ratings, JoinType.LEFT);
            predicates.add(cb.greaterThanOrEqualTo(ratingJoin.get(Rating.Fields.rate), dto.getMinRating()));
        }
        if (dto.getMaxRating() != null) {
            Join<Content, Rating> ratingJoin = root.join(Content.Fields.ratings, JoinType.LEFT);
            predicates.add(cb.lessThanOrEqualTo(ratingJoin.get(Rating.Fields.rate), dto.getMaxRating()));
        }
        return predicates;
    }
}