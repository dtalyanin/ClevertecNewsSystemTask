package ru.clevertec.news.integration.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.news.dao.CommentsRepository;
import ru.clevertec.news.integration.BaseIntegrationTest;
import ru.clevertec.news.models.Comment;

import java.util.List;

import static generators.factories.PageableFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class CommentsRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private CommentsRepository repository;

    @Test
    void checkFindAllByNewsIdShouldReturn4Comments() {
        List<Comment> actualComments = repository.findAllByNewsId(1L, getDefaultPageable());
        assertThat(actualComments).hasSize(4);
    }

    @Test
    void checkFindAllByNewsIdShouldReturnEmptyList() {
        List<Comment> actualComments = repository.findAllByNewsId(5L, getDefaultPageable());
        assertThat(actualComments).isEmpty();
    }

    @Test
    void checkFindAllByNewsIdShouldReturnEmptyListWhenOutOfRange() {
        List<Comment> actualComments = repository.findAllByNewsId(1L, getPageableOutOfRange());
        assertThat(actualComments).isEmpty();
    }

    @Test
    void checkDeleteByNewsIdShouldDelete2Comments() {
        repository.deleteByNewsId(2L);
        List<Comment> commentsThatHasNewsId2 = repository.findAllById(List.of(5L, 6L));
        assertThat(commentsThatHasNewsId2).isEmpty();
    }

    @Test
    void checkDeleteByNewsIdShouldNotDelete() {
        long commentsBeforeExecuting = repository.count();
        repository.deleteByNewsId(5L);
        long commentsAfterExecuting = repository.count();
        assertThat(commentsBeforeExecuting).isEqualTo(commentsAfterExecuting);
    }
}