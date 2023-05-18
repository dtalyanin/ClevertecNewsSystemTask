package ru.clevertec.news.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import ru.clevertec.exceptions.exceptions.AccessException;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.news.dao.CommentsRepository;
import ru.clevertec.news.dao.NewsRepository;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.comments.CreateCommentDto;
import ru.clevertec.news.dto.comments.UpdateCommentDto;
import ru.clevertec.news.models.Comment;
import ru.clevertec.news.models.Operation;
import ru.clevertec.news.utils.mappers.CommentsMapper;

import java.util.List;
import java.util.Optional;

import static generators.factories.AuthenticatedUserFactory.getAdmin;
import static generators.factories.AuthenticatedUserFactory.getSubscriber;
import static generators.factories.PageFactory.getPageWith2Comments;
import static generators.factories.PageableFactory.getDefaultPageable;
import static generators.factories.comments.CommentDtoFactory.*;
import static generators.factories.comments.CommentFactory.*;
import static generators.factories.comments.CreateCommentDtoFactory.getCreateDto;
import static generators.factories.comments.CreateCommentDtoFactory.getCreateDtoWithNotExistingNewsId;
import static generators.factories.comments.UpdateCommentDtoFactory.getUpdateDto;
import static generators.factories.news.NewsFactory.getNews1;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.clevertec.news.utils.constants.MessageConstants.*;

@ExtendWith(MockitoExtension.class)
class CommentsServiceImplTest {

    @InjectMocks
    private CommentsServiceImpl service;
    @Mock
    private CommentsRepository repository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private CommentsMapper mapper;


    @Test
    void checkGetCommentsWithPaginationShouldReturnAll2Comments() {
        doReturn(getPageWith2Comments()).when(repository).findAll(any(Pageable.class));
        doReturn(getFirst2CommentsDto()).when(mapper).convertAllCommentsToDtos(anyList());

        List<CommentDto> actualComments = service.getCommentsWithPagination(getDefaultPageable());
        List<CommentDto> expectedComments = getFirst2CommentsDto();

        assertThat(actualComments).isEqualTo(expectedComments);

        verify(repository).findAll(any(Pageable.class));
        verify(mapper).convertAllCommentsToDtos(anyList());
    }

    @Test
    void checkGetAllSearchedCommentsWithPaginationByTextIgnoreCaseShouldReturn2Comments() {
        doReturn(getComment1()).when(mapper).convertDtoToComment(any(CommentDto.class));
        doReturn(getPageWith2Comments()).when(repository).findAll(any(Example.class), any(Pageable.class));
        doReturn(getFirst2CommentsDto()).when(mapper).convertAllCommentsToDtos(anyList());

        List<CommentDto> actualComments =
                service.getAllSearchedCommentsWithPagination(getDtoToSearchByTextIgnoreCase(), getDefaultPageable());
        List<CommentDto> expectedComments = getFirst2CommentsDto();

        assertThat(actualComments).isEqualTo(expectedComments);

        verify(mapper).convertDtoToComment(any(CommentDto.class));
        verify(repository).findAll(any(Example.class), any(Pageable.class));
        verify(mapper).convertAllCommentsToDtos(anyList());
    }

    @Test
    void checkGetCommentByIdShouldReturnComment1() {
        doReturn(Optional.of(getComment1())).when(repository).findById(anyLong());
        doReturn(getCommentDto1FromUser()).when(mapper).convertCommentToDto(any(Comment.class));

        CommentDto actualComment = service.getCommentById(1L);
        CommentDto expectedComment = getCommentDto1FromUser();

        assertThat(actualComment).isEqualTo(expectedComment);

        verify(repository).findById(anyLong());
        verify(mapper).convertCommentToDto(any(Comment.class));
    }

    @Test
    void checkGetCommentByIdShouldReturnNotFoundException() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.getCommentById(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(COMMENT_NOT_FOUND + CANNOT_END + Operation.GET.getName());

        verify(repository).findById(anyLong());
        verify(mapper, never()).convertCommentToDto(any(Comment.class));
    }

    @Test
    void checkGetCommentsByNewsIdShouldReturn2Comments() {
        doReturn(getAll2Comments()).when(repository).findAllByNewsId(anyLong(), any(Pageable.class));
        doReturn(getFirst2CommentsDto()).when(mapper).convertAllCommentsToDtos(anyList());

        List<CommentDto> actualComments = service.getCommentsByNewsId(3L, getDefaultPageable());
        List<CommentDto> expectedComments = getFirst2CommentsDto();

        assertThat(actualComments).isEqualTo(expectedComments);

        verify(repository).findAllByNewsId(anyLong(), any(Pageable.class));
        verify(mapper).convertAllCommentsToDtos(anyList());
    }

    @Test
    void checkGetCommentsByNewsIdShouldReturnEmptyListNoCommentForNews() {
        doReturn(getCommentsEmptyList()).when(repository).findAllByNewsId(anyLong(), any(Pageable.class));
        doReturn(getCommentsDtoEmptyList()).when(mapper).convertAllCommentsToDtos(anyList());

        List<CommentDto> actualComments = service.getCommentsByNewsId(3L, getDefaultPageable());

        assertThat(actualComments).isEmpty();

        verify(repository).findAllByNewsId(anyLong(), any(Pageable.class));
        verify(mapper).convertAllCommentsToDtos(anyList());
    }

    @Test
    void checkAddCommentShouldReturnCreatedDto() {
        doReturn(getComment1()).when(mapper).convertCreateDtoToComment(any(CreateCommentDto.class), anyString());
        doReturn(Optional.of(getNews1())).when(newsRepository).findById(anyLong());
        doReturn(getComment1()).when(repository).save(any(Comment.class));
        doReturn(getCommentDto1FromUser()).when(mapper).convertCommentToDto(any(Comment.class));

        CommentDto actualComment = service.addComment(getCreateDto(), getAdmin());
        CommentDto expectedComment = getCommentDto1FromUser();

        assertThat(actualComment).isEqualTo(expectedComment);

        verify(mapper).convertCreateDtoToComment(any(CreateCommentDto.class), anyString());
        verify(newsRepository).findById(anyLong());
        verify(repository).save(any(Comment.class));
        verify(mapper).convertCommentToDto(any(Comment.class));
    }

    @Test
    void checkAddCommentShouldThrowExceptionNewsNotFound() {
        doReturn(getComment1()).when(mapper).convertCreateDtoToComment(any(CreateCommentDto.class), anyString());
        doReturn(Optional.empty()).when(newsRepository).findById(anyLong());

        assertThatThrownBy(() -> service.addComment(getCreateDtoWithNotExistingNewsId(), getAdmin()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.ADD.getName());

        verify(mapper).convertCreateDtoToComment(any(CreateCommentDto.class), anyString());
        verify(newsRepository).findById(anyLong());
        verify(repository, never()).save(any(Comment.class));
        verify(mapper, never()).convertCommentToDto(any(Comment.class));
    }

    @Test
    void checkUpdateCommentShouldReturnUpdatedDto() {
        doReturn(Optional.of(getComment1())).when(repository).findById(anyLong());
        doReturn(getComment1()).when(mapper).updateComment(any(Comment.class), any(UpdateCommentDto.class));
        doReturn(getCommentDto1WithUpdatedText()).when(mapper).convertCommentToDto(any(Comment.class));

        CommentDto actualComment = service.updateComment(1L, getUpdateDto(), getSubscriber());
        CommentDto expectedComment = getCommentDto1WithUpdatedText();

        assertThat(actualComment).isEqualTo(expectedComment);

        verify(repository).findById(anyLong());
        verify(mapper).updateComment(any(Comment.class), any(UpdateCommentDto.class));
        verify(mapper).convertCommentToDto(any(Comment.class));
    }

    @Test
    void checkUpdateCommentShouldThrowExceptionNotOwner() {
        doReturn(Optional.of(getComment2())).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.updateComment(3L, getUpdateDto(), getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_COMMENT_OWNER + CANNOT_END + Operation.UPDATE.getName());

        verify(repository).findById(anyLong());
        verify(mapper, never()).updateComment(any(Comment.class), any(UpdateCommentDto.class));
        verify(mapper, never()).convertCommentToDto(any(Comment.class));
    }

    @Test
    void checkUpdateCommentShouldUpdateIfNotOwnerButAdmin() {
        doReturn(Optional.of(getComment2())).when(repository).findById(anyLong());
        doReturn(getComment2()).when(mapper).updateComment(any(Comment.class), any(UpdateCommentDto.class));
        doReturn(getCommentDto1WithUpdatedText()).when(mapper).convertCommentToDto(any(Comment.class));

        CommentDto actualComment = service.updateComment(1L, getUpdateDto(), getAdmin());
        CommentDto expectedComment = getCommentDto1WithUpdatedText();

        assertThat(actualComment).isEqualTo(expectedComment);

        verify(repository).findById(anyLong());
        verify(mapper).updateComment(any(Comment.class), any(UpdateCommentDto.class));
        verify(mapper).convertCommentToDto(any(Comment.class));
    }

    @Test
    void checkDeleteCommentByIdShouldBeCalledWithId() {
        Comment expectedDeletedComment = getComment1();

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        doReturn(Optional.of(getComment1())).when(repository).findById(anyLong());
        service.deleteCommentById(1L, getAdmin());
        verify(repository).delete(captor.capture());

        Comment actualDeletedComment = captor.getValue();

        assertThat(expectedDeletedComment).isEqualTo(actualDeletedComment);

        verify(repository).findById(anyLong());
    }

    @Test
    void checkDeleteCommentByIdShouldThrowNotFoundException() {
        doReturn(Optional.empty()).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.deleteCommentById(1L, getAdmin()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(COMMENT_NOT_FOUND + CANNOT_END + Operation.DELETE.getName());

        verify(repository).findById(anyLong());
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void checkDeleteCommentByIdShouldThrowNotOwnerException() {
        doReturn(Optional.of(getComment2())).when(repository).findById(anyLong());

        assertThatThrownBy(() -> service.deleteCommentById(1L, getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_COMMENT_OWNER + CANNOT_END + Operation.DELETE.getName());

        verify(repository).findById(anyLong());
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void checkDeleteCommentByIdShouldDeleteIfNotOwnerButAdmin() {
        Comment expectedDeletedComment = getComment2();

        ArgumentCaptor<Comment> captor = ArgumentCaptor.forClass(Comment.class);
        doReturn(Optional.of(getComment2())).when(repository).findById(anyLong());
        service.deleteCommentById(2L, getAdmin());
        verify(repository).delete(captor.capture());

        Comment actualDeletedComment = captor.getValue();

        assertThat(expectedDeletedComment).isEqualTo(actualDeletedComment);

        verify(repository).findById(anyLong());
    }

    @Test
    void checkDeleteCommentsByNewsIdShouldBeCalledWithNewsId() {
        long expectedDeletedNewsId = 1L;

        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        service.deleteCommentsByNewsId(expectedDeletedNewsId);
        verify(repository).deleteByNewsId(captor.capture());

        long actualDeletedNewsId = captor.getValue();

        assertThat(actualDeletedNewsId).isEqualTo(expectedDeletedNewsId);
    }

    @Test
    void triggerCacheEvict() {
        assertThatCode(() -> service.triggerCacheEvict(1L)).doesNotThrowAnyException();
    }
}