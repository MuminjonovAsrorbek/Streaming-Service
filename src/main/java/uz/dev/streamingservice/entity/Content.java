package uz.dev.streamingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.streamingservice.entity.template.AbsLongEntity;
import uz.dev.streamingservice.enums.AgeLimitEnum;
import uz.dev.streamingservice.enums.ContentTypeEnum;
import uz.dev.streamingservice.enums.PremiumStatus;

import java.util.List;
import java.util.Set;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 15:05
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Content extends AbsLongEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer publishedYear;

    @Column(nullable = false)
    private Integer duration;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ContentTypeEnum contentType;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AgeLimitEnum ageLimit;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PremiumStatus premiumStatus;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(
            name = "content_genre",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;


    @ManyToMany
    @ToString.Exclude
    @JoinTable(
            name = "content_actor",
            joinColumns = @JoinColumn(name = "content_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Rating> ratings;

    @OneToMany(mappedBy = "content", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<WatchHistory> histories;
}
