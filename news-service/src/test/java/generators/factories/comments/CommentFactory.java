package generators.factories.comments;

import generators.builders.CommentTestBuilder;
import ru.clevertec.news.models.Comment;

import java.util.Collections;
import java.util.List;

public class CommentFactory {

    public static Comment getComment1() {
        return CommentTestBuilder.builder().build();
    }

    public static Comment getComment2() {
        return CommentTestBuilder.builder()
                .withId(2L)
                .withUsername("User 2")
                .build();
    }

    public static List<Comment> getAll2Comments() {
        return List.of(getComment1(), getComment2());
    }

    public static List<Comment> getCommentsEmptyList() {
        return Collections.emptyList();
    }
}
