package ru.clevertec.news.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.clevertec.news.utils.constants.MessageConstants.*;

/**
 * News entity for DB
 */
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
    @NotBlank(message = EMPTY_NEWS_TITLE)
    @Size(max = 200, message = TOO_LONG_NEWS_TITLE)
    @Column(name = "title", nullable = false, length = 200)
    private String title;
    @NotBlank(message = EMPTY_NEWS_TEXT)
    @Column(name = "text", nullable = false)
    private String text;
    @NotBlank(message = NULL_NEWS_USERNAME)
    @Size(max = 50, message = TOO_LONG_NEWS_USERNAME)
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
