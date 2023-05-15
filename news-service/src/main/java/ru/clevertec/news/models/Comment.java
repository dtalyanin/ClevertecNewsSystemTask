package ru.clevertec.news.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

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
    @NotBlank(message = "Comment cannot be empty")
    @Column(name = "text", nullable = false)
    private String text;
    @NotBlank(message = "Comment must contain username")
    @Size(max = 50, message = "Max length of username is 50 characters")
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
