package uz.dev.streamingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.streamingservice.entity.template.AbsLongEntity;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 15:19
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Rating extends AbsLongEntity {

    @Column(nullable = false)
    private Integer rate;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    private User user;

    @ManyToOne
    private Content content;
}
