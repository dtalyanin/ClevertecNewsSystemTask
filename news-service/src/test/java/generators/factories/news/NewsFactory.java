package generators.factories.news;

import generators.builders.NewsTestBuilder;
import ru.clevertec.news.models.News;

import java.time.LocalDateTime;
import java.util.List;

import static generators.factories.comments.CommentFactory.*;

public class NewsFactory {

    public static News getNews1() {
        return NewsTestBuilder.builder().build();
    }

    public static News getNews2() {
        return NewsTestBuilder.builder()
                .withId(2L)
                .withText("Text 2")
                .withTitle("News 2")
                .withUsername("User 2")
                .withTime(LocalDateTime.parse("2023-02-01T10:00:00"))
                .build();
    }

    public static News getNews3WithComments() {
        return NewsTestBuilder.builder()
                .withId(3L)
                .withUsername("User 2")
                .withComments(getAll2Comments())
                .build();
    }

    public static News getCreatedNews() {
        return NewsTestBuilder.builder()
                .withId(null)
                .withTitle("News new")
                .withText("Text new")
                .withUsername("User")
                .withComments(null)
                .withTime(null)
                .build();
    }

    public static News getUpdatedNews() {
        return NewsTestBuilder.builder()
                .withTitle("News new")
                .withText("Text new")
                .build();
    }

    public static News getNewsWithoutId() {
        return NewsTestBuilder.builder()
                .withId(null)
                .withUsername(null)
                .build();
    }

    public static List<News> getAllNews() {
        return List.of(getNews1(), getNews2());
    }
}
