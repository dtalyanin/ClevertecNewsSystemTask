package ru.clevertec.news.utils.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.news.models.Comment;
import ru.clevertec.news.models.News;
import ru.clevertec.news.utils.CacheFactory;

import static generators.factories.comments.CommentFactory.getComment1;
import static generators.factories.comments.CommentFactory.getComment2;
import static generators.factories.news.NewsFactory.getNews1;
import static generators.factories.news.NewsFactory.getNews2;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.clevertec.news.utils.constants.CacheConstants.COMMENTS_CACHE;
import static ru.clevertec.news.utils.constants.CacheConstants.NEWS_CACHE;

class CacheFacadeTest {

    private CacheFacade facade;

    @BeforeEach
    void setUp() {
        facade = new CacheFacade(new CacheFactory("lru", 3));
        facade.put(NEWS_CACHE, getNews1().getId(), getNews1());
        facade.put(NEWS_CACHE, getNews2().getId(), getNews2());
        facade.put(COMMENTS_CACHE, getComment1().getId(), getComment1());
        facade.put(COMMENTS_CACHE, getComment2().getId(), getComment2());
    }


    @Test
    void checkGetShouldReturnNews1() {
        Object actual = facade.get(NEWS_CACHE, 1L);
        News expectedNews = getNews1();

        assertThat(actual).isEqualTo(expectedNews);
    }

    @Test
    void checkGetShouldReturnNullNews() {
        Object actual = facade.get(NEWS_CACHE, 10L);

        assertThat(actual).isNull();
    }

    @Test
    void checkGetShouldReturnComment1() {
        Object actual = facade.get(COMMENTS_CACHE, 1L);
        Comment expectedComment = getComment1();

        assertThat(actual).isEqualTo(expectedComment);
    }

    @Test
    void checkGetShouldReturnNullComment() {
        Object actual = facade.get(COMMENTS_CACHE, 10L);

        assertThat(actual).isNull();
    }

    @Test
    void checkPutShouldExistNewsAfterExecuting() {
        long idToPut = 5L;
        facade.put(NEWS_CACHE, idToPut, getNews1());
        Object actual = facade.get(NEWS_CACHE, idToPut);
        News expectedNews = getNews1();

        assertThat(actual).isEqualTo(expectedNews);
    }

    @Test
    void checkPutShouldExistCommentAfterExecuting() {
        long idToPut = 5L;
        facade.put(COMMENTS_CACHE, idToPut, getComment1());
        Object actual = facade.get(COMMENTS_CACHE, idToPut);
        Comment expectedComment = getComment1();

        assertThat(actual).isEqualTo(expectedComment);
    }

    @Test
    void checkDeleteShouldNotExistNewsAfterExecuting() {
        long idToDelete = 1L;
        facade.delete(NEWS_CACHE, idToDelete);

        assertThat(facade.get(NEWS_CACHE, idToDelete)).isNull();
    }

    @Test
    void checkPutShouldNotExistCommentAfterExecuting() {
        long idToDelete = 1L;
        facade.delete(COMMENTS_CACHE, idToDelete);

        assertThat(facade.get(COMMENTS_CACHE, idToDelete)).isNull();
    }
}