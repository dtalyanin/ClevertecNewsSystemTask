package generators.factories;

import generators.factories.comments.CommentFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.clevertec.news.models.Comment;

public class PageFactory {

    public static Page<Comment> getPageWith2Comments() {
        return new PageImpl<>(CommentFactory.getAll2Comments());
    }
}
