package ru.clevertec.news.utils;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import ru.clevertec.news.models.Comment;
import ru.clevertec.news.models.News;

import static generators.factories.comments.CommentFactory.getComment1;
import static generators.factories.news.NewsFactory.getNews1;
import static org.assertj.core.api.Assertions.assertThat;

class SearchHelperTest {

    @Test
    void checkGetExampleShouldReturnCommentExample() {
        Example<Comment> example = SearchHelper.getExample(getComment1());
        Class<?> actualClass = example.getProbeType();
        Class<Comment> expectedClass = Comment.class;

        assertThat(actualClass).isEqualTo(expectedClass);
    }

    @Test
    void checkGetExampleShouldReturnNewsExample() {
        Example<News> example = SearchHelper.getExample(getNews1());
        Class<?> actualClass = example.getProbeType();
        Class<News> expectedClass = News.class;

        assertThat(actualClass).isEqualTo(expectedClass);
    }
}