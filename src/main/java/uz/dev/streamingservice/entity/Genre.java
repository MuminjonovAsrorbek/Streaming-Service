package uz.dev.streamingservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.streamingservice.entity.template.AbsLongEntity;

import java.util.Set;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 15:11
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Genre extends AbsLongEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    @ToString.Exclude
    private Set<Content> content;
}
