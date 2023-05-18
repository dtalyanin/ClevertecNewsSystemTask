package generators.factories.comments;

import generators.builders.CommentTestBuilder;
import ru.clevertec.news.models.Comment;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class CommentFactory {

    public static Comment getComment1() {
        return CommentTestBuilder.builder().build();
    }

    public static Comment getComment2() {
        return CommentTestBuilder.builder()
                .withId(2L)
                .withText("Comment 2")
                .withUsername("User 2")
                .withTime(LocalDateTime.parse("2023-01-01T12:00:00"))
                .build();
    }

    public static Comment getCommentWithoutIdAndNews() {
        return CommentTestBuilder.builder()
                .withId(null)
                .withNews(null)
                .build();
    }

    public static Comment getCreatedComment() {
        return CommentTestBuilder.builder()
                .withId(null)
                .withText("Comment created")
                .withNews(null)
                .withTime(null)
                .build();
    }

    public static Comment getUpdateComment() {
        return CommentTestBuilder.builder()
                .withText("Comment updated")
                .build();
    }

    public static List<Comment> getAll2Comments() {
        return List.of(getComment1(), getComment2());
    }

    public static List<Comment> getCommentsEmptyList() {
        return Collections.emptyList();
    }
}
