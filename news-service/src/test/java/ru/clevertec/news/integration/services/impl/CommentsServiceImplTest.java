package ru.clevertec.news.integration.services.impl;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.exceptions.exceptions.AccessException;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.news.dao.CommentsRepository;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.integration.BaseIntegrationTest;
import ru.clevertec.news.models.Operation;
import ru.clevertec.news.services.impl.CommentsServiceImpl;

import java.util.List;

import static generators.factories.comments.CommentDtoFactory.*;
import static generators.factories.comments.CreateCommentDtoFactory.*;
import static generators.factories.PageableFactory.*;
import static generators.factories.comments.UpdateCommentDtoFactory.*;
import static generators.factories.AuthenticatedUserFactory.getAdmin;
import static generators.factories.AuthenticatedUserFactory.getSubscriber;
import static org.assertj.core.api.Assertions.*;
import static ru.clevertec.news.utils.constants.MessageConstants.*;

class CommentsServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private CommentsServiceImpl service;
    @Autowired
    private CommentsRepository repository;

    @Test
    void checkGetCommentsWithPaginationShouldReturnAll6Comments() {
        List<CommentDto> actualComments = service.getCommentsWithPagination(getDefaultPageable());
        List<CommentDto> expectedComments = getAllComments();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetCommentsWithPaginationShouldReturn2CommentsWithPageSize2() {
        List<CommentDto> actualComments = service.getCommentsWithPagination(getPageableWithSize2());
        List<CommentDto> expectedComments = getFirst2CommentsDto();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetCommentsWithPaginationShouldReturn2CommentsFrom2PageAndSize2() {
        List<CommentDto> actualComments = service.getCommentsWithPagination(getPageableWithPage2AndSize2());
        List<CommentDto> expectedComments = getFrom2PageFirst2Comments();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetCommentsWithPaginationShouldReturnEmptyListWhenOutOfRange() {
        List<CommentDto> actualComments = service.getCommentsWithPagination(getPageableOutOfRange());

        assertThat(actualComments).isEmpty();
    }

    @Test
    void checkGetCommentsWithPaginationShouldReturnAll6CommentsWithoutSort() {
        List<CommentDto> actualComments = service.getCommentsWithPagination(getDefaultPageableWithSortingByUsernameDesc());
        List<CommentDto> expectedComments = getAllComments();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationByTextIgnoreCaseShouldReturn2Comments() {
        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchByTextIgnoreCase(), getDefaultPageable());
        List<CommentDto> expectedComments = getSearchedByText();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationByTextIgnoreCaseShouldReturn1CommentsWithSize1() {
        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchByTextIgnoreCase(), getPageableWithSize1());
        List<CommentDto> expectedComments = getSearchedByTextWithSize1();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationByUsernameIgnoreCaseShouldReturn2Comments() {
        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchByUsernameIgnoreCase(), getDefaultPageable());
        List<CommentDto> expectedComments = getSearchedByUsername();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationByTextAndUsernameIgnoreCaseShouldReturn1Comment() {
        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchByUsernameAndText(), getDefaultPageable());
        List<CommentDto> expectedComments = getSearchedByUsernameAndText();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationByDateShouldReturn1Comment() {
        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchByDate(), getDefaultPageable());
        List<CommentDto> expectedComments = getSearchedByDate();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationShouldReturnEmptyListUsernameNotExist() {
        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchWithNotExistingUsername(),
                        getDefaultPageable());

        assertThat(actualComments).isEmpty();
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationShouldReturnEmptyListTextNotExist() {
        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchWithNotExistingText(),
                        getDefaultPageable());

        assertThat(actualComments).isEmpty();
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationShouldReturnEmptyListDateNotExist() {
        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchWithNotExistingDate(),
                        getDefaultPageable());

        assertThat(actualComments).isEmpty();
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationShouldIgnoreId() {
        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchIgnoreId(), getDefaultPageable());
        List<CommentDto> expectedComments = getAllComments();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetCommentByIdShouldReturnComment1() {
        CommentDto actualComment = service.getCommentById(1L);
        CommentDto expectedComment = getCommentDto1FromUser();

        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    void checkGetCommentByIdShouldReturnNotFoundException() {
        assertThatThrownBy(() -> service.getCommentById(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(COMMENT_NOT_FOUND + CANNOT_END + Operation.GET.getName());
    }

    @Test
    void checkGetCommentsByNewsIdShouldReturn4Comments() {
        List<CommentDto> actualComments = service.getCommentsByNewsId(1L, getDefaultPageable());
        List<CommentDto> expectedComments = getAllCommentsForNews1();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetCommentsByNewsIdShouldReturn2CommentsWithSize2() {
        List<CommentDto> actualComments = service.getCommentsByNewsId(1L, getPageableWithSize2());
        List<CommentDto> expectedComments = getAllCommentsForNews1WithSize2();

        assertThat(actualComments).isEqualTo(expectedComments);
    }

    @Test
    void checkGetCommentsByNewsIdShouldReturnEmptyListNoCommentForNews() {
        List<CommentDto> actualComments = service.getCommentsByNewsId(3L, getDefaultPageable());

        assertThat(actualComments).isEmpty();
    }

    @Test
    void checkAddCommentShouldReturnCreatedDto() {
        CommentDto actualComment = service.addComment(getCreateDto(), getAdmin());
        CommentDto expectedComment = getCreatedCommentDto();

        assertThat(actualComment.getId()).isEqualTo(expectedComment.getId());
        assertThat(actualComment.getText()).isEqualTo(expectedComment.getText());
        assertThat(actualComment.getUsername()).isEqualTo(expectedComment.getUsername());
    }

    @Test
    void checkAddCommentShouldExistInDbAfterExecution() {
        long createdId = service.addComment(getCreateDto(), getAdmin()).getId();

        assertThat(repository.findById(createdId)).isPresent();
    }

    @Test
    void checkAddCommentShouldThrowExceptionEmptyText() {
        assertThatThrownBy(() -> service.addComment(getCreateDtoWithEmptyText(), getAdmin()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddCommentShouldThrowExceptionNullText() {
        assertThatThrownBy(() -> service.addComment(getCreateDtoWithNullText(), getAdmin()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddCommentShouldThrowExceptionNewsNotFound() {
        assertThatThrownBy(() -> service.addComment(getCreateDtoWithNotExistingNewsId(), getAdmin()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.ADD.getName());
    }

    @Test
    void checkUpdateCommentShouldReturnDtoWithUpdatedText() {
        CommentDto actualComment = service.updateComment(1L, getUpdateDto(), getAdmin());
        CommentDto expectedComment = getCommentDto1WithUpdatedText();

        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    void checkUpdateCommentShouldReturnExistInDbWithUpdatedText() {
        long idForUpdate = 1L;
        String textBeforeUpdate = repository.findById(idForUpdate).get().getText();
        service.updateComment(idForUpdate, getUpdateDto(), getAdmin());
        String textAfterUpdate = repository.findById(idForUpdate).get().getText();

        assertThat(textBeforeUpdate).isNotEqualTo(textAfterUpdate);
    }

    @Test
    void checkUpdateCommentShouldThrowExceptionEmptyText() {
        assertThatThrownBy(() -> service.updateComment(1L, getUpdateDtoWithEmptyText(), getAdmin()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkUpdateCommentShouldNotUpdateValueIfNullText() {
        assertThatCode(() -> service.updateComment(1L, getUpdateDtoWithNullText(), getAdmin()))
                .doesNotThrowAnyException();
    }

    @Test
    void checkUpdateCommentShouldThrowExceptionNotOwner() {
        assertThatThrownBy(() -> service.updateComment(3L, getUpdateDto(), getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_COMMENT_OWNER + CANNOT_END + Operation.UPDATE.getName());
    }

    @Test
    void checkDeleteCommentByIdShouldNotExistInDbAfterExecution() {
        long idForDelete = 5L;
        service.deleteCommentById(idForDelete, getAdmin());

        assertThat(repository.findById(idForDelete)).isEmpty();
    }

    @Test
    void checkDeleteCommentByIdShouldThrowNotFoundException() {
        assertThatThrownBy(() -> service.deleteCommentById(100L, getAdmin()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(COMMENT_NOT_FOUND + CANNOT_END + Operation.DELETE.getName());
    }

    @Test
    void checkDeleteCommentByIdShouldThrowNotOwnerException() {
        assertThatThrownBy(() -> service.deleteCommentById(3L, getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_COMMENT_OWNER + CANNOT_END + Operation.DELETE.getName());
    }

    @Test
    void checkDeleteCommentsByNewsIdShouldNotExistInDbAfterExecution() {
        service.deleteCommentsByNewsId(2L);

        assertThat(repository.findAllById(List.of(5L, 6L))).isEmpty();
    }

    @Test
    void checkDeleteCommentsByNewsIdShouldNoChangeNoCommentsWithNewsId() {
        long entityBeforeExecution = repository.count();
        service.deleteCommentsByNewsId(100L);
        long entityAfterExecution = repository.count();

        assertThat(entityBeforeExecution).isEqualTo(entityAfterExecution);
    }

    @Test
    void triggerCacheEvict() {
        assertThatCode(() -> service.triggerCacheEvict(1L)).doesNotThrowAnyException();
    }
}