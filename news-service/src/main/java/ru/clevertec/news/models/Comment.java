package ru.clevertec.news.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

import static ru.clevertec.news.utils.constants.MessageConstants.*;

/**
 * Comment entity for DB
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = EMPTY_COMMENT_TEXT)
    @Column(name = "text", nullable = false)
    private String text;
    @NotBlank(message = NULL_COMMENT_USERNAME)
    @Size(max = 50, message = TOO_LONG_NEWS_USERNAME)
    @Column(name = "username", nullable = false, updatable = false, length = 50)
    private String username;
    @ManyToOne
    @JoinColumn(name="news_id")
    private News news;
    @Column(name = "created_time", nullable = false)
    private LocalDateTime time;

    @PrePersist
    public void addTimeOfCreation() {
        this.time = LocalDateTime.now();
    }
}
