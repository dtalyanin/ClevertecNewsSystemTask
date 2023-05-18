package generators.factories.news;

import generators.builders.NewsTestBuilder;
import ru.clevertec.news.models.News;

public class NewsFactory {

    public static News getNews1() {
        return NewsTestBuilder.builder().build();
    }
}
