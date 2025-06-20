package uz.dev.streamingservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.streamingservice.dto.request.BuySubscriptionDTO;
import uz.dev.streamingservice.dto.response.*;
import uz.dev.streamingservice.entity.Content;
import uz.dev.streamingservice.entity.Subscription;
import uz.dev.streamingservice.entity.User;
import uz.dev.streamingservice.entity.template.AbsLongEntity;
import uz.dev.streamingservice.enums.PremiumStatus;
import uz.dev.streamingservice.enums.SubscriptionStatusEnum;
import uz.dev.streamingservice.exceptions.EntityAlreadyExist;
import uz.dev.streamingservice.exceptions.EntityNotFound;
import uz.dev.streamingservice.mapper.*;
import uz.dev.streamingservice.repository.ContentRepository;
import uz.dev.streamingservice.repository.UserRepository;
import uz.dev.streamingservice.service.template.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 20:35
 **/

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final SubscriptionMapper subscriptionMapper;

    private final WatchHistoryMapper watchHistoryMapper;

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    private final UserMapper userMapper;

    private final ContinueWatchingMapper continueWatchingMapper;

    @Override
    public PageableDTO getAll(Integer page, Integer pageSize) {

        Sort sort = Sort.by(AbsLongEntity.Fields.id).ascending();

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<User> userPage = userRepository.findAll(pageable);

        List<User> users = userPage.getContent();

        List<UserDTO> userDTOS = users.stream().map(userMapper::toDTO).toList();

        return new PageableDTO(
                userPage.getTotalPages(),
                userPage.getTotalElements(),
                !userPage.isLast(),
                !userPage.isFirst(),
                userDTOS
        );

    }

    @Override
    @Transactional
    public void buySubscription(Long id, BuySubscriptionDTO subscriptionDTO) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + id, HttpStatus.NOT_FOUND);

        User user = optionalUser.get();

        Subscription subscription = user.getSubscription();

        subscription.setType(subscriptionDTO.getSubscriptionType());
        subscription.setStartTime(LocalDate.now());
        subscription.setEndTime(LocalDate.now().plusMonths(subscriptionDTO.getDurationMonths()));
        subscription.setStatus(SubscriptionStatusEnum.ACTIVE);

        user.setSubscription(subscription);

        userRepository.save(user);
    }

    @Override
    public SubscriptionDTO getUserSubscription(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + id, HttpStatus.NOT_FOUND);

        User user = optionalUser.get();

        return subscriptionMapper.toDTO(user.getSubscription());
    }

    @Override
    public void cancelSubscription(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + id, HttpStatus.NOT_FOUND);

        User user = optionalUser.get();

        Subscription subscription = user.getSubscription();

        subscription.setStatus(SubscriptionStatusEnum.CANCELLED);
        subscription.setType(PremiumStatus.FREE);
        subscription.setStartTime(null);
        subscription.setEndTime(null);

        user.setSubscription(subscription);

        userRepository.save(user);

    }

    @Override
    public List<WatchHistoryDTO> getUserWatchHistory(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + id, HttpStatus.NOT_FOUND);

        User user = optionalUser.get();

        return user.getHistories().stream().map(watchHistoryMapper::toDTO).toList();
    }

    @Override
    public List<ContentDTO> getRecommendations(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + id, HttpStatus.NOT_FOUND);

        Pageable top10 = PageRequest.of(0, 10);

        List<Content> recommendedContents = contentRepository.findRecommendedContentByUserId(id, top10);

        return recommendedContents.stream().map(contentMapper::toDTO).toList();

    }

    @Override
    public List<ContinueWatchingDTO> getUserWatching(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + id, HttpStatus.NOT_FOUND);

        List<Content> contents = contentRepository.findIncompleteContentByUserId(id);

        List<ContinueWatchingDTO> watchingDTOS = new ArrayList<>();


        for (Content content : contents) {

            ContinueWatchingDTO continueWatchingDTO = continueWatchingMapper.toDTO(content, id);

            watchingDTOS.add(continueWatchingDTO);

        }

        return watchingDTOS;
    }

    @Override
    public UserDTO getById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + id, HttpStatus.NOT_FOUND);

        User user = optionalUser.get();

        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public void create(UserDTO userDTO) {

        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());

        if (optionalUser.isPresent())
            throw new EntityAlreadyExist("User already exist by email : " + userDTO.getEmail(), HttpStatus.CONFLICT);

        User user = new User();

        Subscription subscription = new Subscription();

        subscription.setType(PremiumStatus.FREE);

        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setSubscription(subscription);

        userRepository.save(user);

    }

    @Override
    @Transactional
    public void update(UserDTO userDTO, Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + id, HttpStatus.NOT_FOUND);

        User user = optionalUser.get();

        Optional<User> optional = userRepository.findByEmailAndIdNot(userDTO.getEmail(), id);

        if (optional.isPresent())
            throw new EntityAlreadyExist("User already exist by email : " + userDTO.getEmail(), HttpStatus.CONFLICT);

        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            throw new EntityNotFound("User not found with ID : " + id, HttpStatus.NOT_FOUND);

        User user = optionalUser.get();

        userRepository.delete(user);

    }
}
