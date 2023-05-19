package generators.factories;

import generators.factories.comments.CommentFactory;
import generators.factories.news.NewsFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.clevertec.news.models.Comment;
import ru.clevertec.news.models.News;

public class PageFactory {

    public static Page<Comment> getPageWith2Comments() {
        return new PageImpl<>(CommentFactory.getAll2Comments());
    }

    public static Page<News> getPageWith2News() {
        return new PageImpl<>(NewsFactory.getAllNews());
    }
}
