package ru.clevertec.news.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(max = 200, message = "Max length of title is 200 characters")
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    @NotBlank(message = "News body cannot be empty")
    @Column(name = "text", nullable = false)
    private String text;
    @NotBlank(message = "News must contain username")
    @Size(max = 50, message = "Max length of username is 50 characters")
    @Column(name = "username", nullable = false, updatable = false, length = 50)
    private String username;
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