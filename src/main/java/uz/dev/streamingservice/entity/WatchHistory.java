package uz.dev.streamingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.dev.streamingservice.entity.template.AbsLongEntity;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 15:20
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class WatchHistory extends AbsLongEntity {

    private LocalDate watchDate;

    private Integer progress;

    @ManyToOne
    private User user;

    @ManyToOne
    private Content content;
}
