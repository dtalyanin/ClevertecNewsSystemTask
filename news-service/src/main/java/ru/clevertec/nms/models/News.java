package ru.clevertec.nms.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "news")
public class News implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "News title cannot be empty")
    @Max(value = 200, message = "Max length of title is 200 characters")
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    @NotBlank(message = "News body cannot be empty")
    @Column(name = "text", nullable = false)
    private String text;
    @NotBlank(message = "News must contain user's name")
    @Max(value = 50, message = "Max length of user's name is 200 characters")
    @Column(name = "username", nullable = false)
    private String userName;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy="news")
    private List<Comment> comments = new ArrayList<>();
    @Column(name = "created_time", nullable = false)
    private LocalDateTime time;

    @PrePersist
    public void addTimeOfCreation() {
        this.time = LocalDateTime.now();
    }
}
