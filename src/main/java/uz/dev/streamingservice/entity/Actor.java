package uz.dev.streamingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.streamingservice.entity.template.AbsLongEntity;

import java.util.Set;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 15:16
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Actor extends AbsLongEntity {

    private String fullName;

    @ManyToMany(mappedBy = "actors")
    @ToString.Exclude
    private Set<Content> contents;
}
