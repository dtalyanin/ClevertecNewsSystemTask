package generators.builders;

import generators.factories.news.NewsFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.news.models.Comment;
import ru.clevertec.news.models.News;

import java.time.LocalDateTime;

import static generators.factories.news.NewsFactory.*;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class CommentTestBuilder implements TestBuilder<Comment> {

    private Long id = 1L;
    private String text = "Comment";
    private String username = "User";
    private News news = getNews1();
    private LocalDateTime time = LocalDateTime.parse("2023-01-01T11:00:00");

    @Override
    public Comment build() {
        return new Comment(id, text, username, news, time);
    }
}
