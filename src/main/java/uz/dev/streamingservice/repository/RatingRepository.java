package uz.dev.streamingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.dev.streamingservice.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT AVG(r.rate) FROM Rating r WHERE r.content.id =:contentId")
    Double findAverageRatingByKContentId(Long contentId);

}