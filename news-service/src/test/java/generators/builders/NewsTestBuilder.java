package generators.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.news.models.Comment;
import ru.clevertec.news.models.News;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class NewsTestBuilder implements TestBuilder<News> {

    private Long id = 1L;
    private String title = "Title";
    private String text = "Text";
    private String username = "User";
    private List<Comment> comments = new ArrayList<>();
    private LocalDateTime time = LocalDateTime.parse("2023-01-01T11:00:00");

    @Override
    public News build() {
        return new News(id, title, text, username, comments, time);
    }
}
