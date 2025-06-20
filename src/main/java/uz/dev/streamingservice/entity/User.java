package uz.dev.streamingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.dev.streamingservice.entity.template.AbsLongEntity;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/18/25 15:02
 **/


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User extends AbsLongEntity {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Subscription subscription;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Rating> ratings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<WatchHistory> histories;
}
