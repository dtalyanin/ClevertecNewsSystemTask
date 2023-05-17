package ru.clevertec.news.integration.services.impl;

import generators.factories.news.NewsDtoFactory;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.exceptions.exceptions.AccessException;
import ru.clevertec.exceptions.exceptions.NotFoundException;
import ru.clevertec.news.dao.CommentsRepository;
import ru.clevertec.news.dao.NewsRepository;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.integration.BaseIntegrationTest;
import ru.clevertec.news.models.Operation;
import ru.clevertec.news.services.impl.NewsServiceImpl;

import java.util.List;

import static generators.factories.AuthenticatedUserFactory.*;
import static generators.factories.PageableFactory.*;
import static generators.factories.PageableFactory.getDefaultPageable;
import static generators.factories.news.ModificationNewsDtoFactory.*;
import static generators.factories.news.NewsDtoFactory.*;
import static generators.factories.news.NewsDtoFactory.getCreatedNews;
import static generators.factories.news.NewsDtoFactory.getUpdatedNews;
import static org.assertj.core.api.Assertions.*;
import static ru.clevertec.news.utils.constants.MessageConstants.*;


class NewsServiceImplTest extends BaseIntegrationTest {

    @Autowired
    private NewsServiceImpl service;
    @Autowired
    private NewsRepository repository;
    @Autowired
    private CommentsRepository commentsRepository;

    @Test
    void checkGetAllNewsWithPaginationShouldReturnAll3News() {
        List<NewsDto> actualNews = service.getAllNewsWithPagination(getDefaultPageable());
        List<NewsDto> expectedNews = NewsDtoFactory.getAllNews();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetNewsWithPaginationShouldReturn2NewsWithPageSize2() {
        List<NewsDto> actualNews = service.getAllNewsWithPagination(getPageableWithSize2());
        List<NewsDto> expectedNews = getFirst2News();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetNeDwsWithPaginationShouldReturn2NewsFrom2PageAndSize2() {
        List<NewsDto> actualNews = service.getAllNewsWithPagination(getPageableWithPage2AndSize2());
        List<NewsDto> expectedNews = getNews3AsList();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetNewsWithPaginationShouldReturnEmptyListWhenOutOfRange() {
        List<NewsDto> actualNews = service.getAllNewsWithPagination(getPageableOutOfRange());

        assertThat(actualNews).isEmpty();
    }

    @Test
    void checkGetNewsWithPaginationShouldReturnAll3NewsWithoutSort() {
        List<NewsDto> actualNews = service.getAllNewsWithPagination(getDefaultPageableWithSortingByUsernameDesc());
        List<NewsDto> expectedNews = getAllNews();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationByTitleIgnoreCaseShouldReturn2News() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchByTitleIgnoreCase(), getDefaultPageable());
        List<NewsDto> expectedNews = getNews2And3();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationByTextIgnoreCaseShouldReturn2News() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchByTextIgnoreCase(), getDefaultPageable());
        List<NewsDto> expectedNews = getNews2And3();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationByTextIgnoreCaseShouldReturn1NewsWithSize1() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchByTextIgnoreCase(), getPageableWithSize1());
        List<NewsDto> expectedNews = getNews2AsList();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationByUsernameIgnoreCaseShouldReturn2News() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchByUsernameIgnoreCase(), getDefaultPageable());
        List<NewsDto> expectedNews = getNews2And3();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationByTextAndUsernameIgnoreCaseShouldReturn1News() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchByUsernameAndText(), getDefaultPageable());
        List<NewsDto> expectedNews = getNews2AsList();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationByDateShouldReturn1News() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchByDate(), getDefaultPageable());
        List<NewsDto> expectedNews = getNews3AsList();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationShouldReturnEmptyListUsernameNotExist() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchWithNotExistingUsername(),
                        getDefaultPageable());

        assertThat(actualNews).isEmpty();
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationShouldReturnEmptyListTextNotExist() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchWithNotExistingText(),
                        getDefaultPageable());

        assertThat(actualNews).isEmpty();
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationShouldReturnEmptyListDateNotExist() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchWithNotExistingDate(),
                        getDefaultPageable());

        assertThat(actualNews).isEmpty();
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationShouldReturnEmptyListTitleNotExist() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchWithNotExistingTitle(),
                        getDefaultPageable());

        assertThat(actualNews).isEmpty();
    }

    @Test
    void checkGetAllSearchedNewsWithPaginationShouldIgnoreId() {
        List<NewsDto> actualNews =
                service.getAllSearchedNewsWithPagination(getDtoToSearchIgnoreId(), getDefaultPageable());
        List<NewsDto> expectedNews = getAllNews();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetNewsByIdShouldReturnNews1() {
        NewsDto actualNews = service.getNewsById(1L);
        NewsDto expectedNews = getNews1();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetNewsByIdShouldReturnNotFoundException() {
        assertThatThrownBy(() -> service.getNewsById(100L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.GET.getName());
    }

    @Test
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNewsWith4Comments() {
        NewsWithCommentsDto actualNews = service.getNewsByIdWithCommentsPagination(1L, getDefaultPageable());
        NewsDto expectedNews = getNews1WithComments();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNewsWith2Comments() {
        NewsWithCommentsDto actualNews = service.getNewsByIdWithCommentsPagination(1L, getPageableWithSize2());
        NewsDto expectedNews = getNews1With2Comments();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNewsWithNoComments() {
        NewsWithCommentsDto actualNews = service.getNewsByIdWithCommentsPagination(3L, getDefaultPageable());
        NewsDto expectedNews = getNews3WithNoComments();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkGetNewsByIdWithCommentsPaginationShouldReturnNotFoundException() {
        assertThatThrownBy(() -> service.getNewsByIdWithCommentsPagination(100L, getDefaultPageable()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.GET.getName());
    }

    @Test
    void checkAddNewsShouldReturnCreatedDto() {
        NewsDto actualNews = service.addNews(getModificationDto(), getAdmin());
        NewsDto expectedNews = getCreatedNews();

        assertThat(actualNews.getId()).isEqualTo(expectedNews.getId());
        assertThat(actualNews.getTitle()).isEqualTo(expectedNews.getTitle());
        assertThat(actualNews.getText()).isEqualTo(expectedNews.getText());
        assertThat(actualNews.getUsername()).isEqualTo(expectedNews.getUsername());
    }

    @Test
    void checkAddNewsShouldExistInDbAfterExecution() {
        long createdId = service.addNews(getModificationDto(), getAdmin()).getId();

        assertThat(repository.findById(createdId)).isPresent();
    }

    @Test
    void checkAddNewsShouldThrowExceptionEmptyTitle() {
        assertThatThrownBy(() -> service.addNews(getModificationDtoWithEmptyTitle(), getAdmin()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddNewsShouldThrowExceptionNullTitle() {
        assertThatThrownBy(() -> service.addNews(getModificationDtoWithNullTitle(), getAdmin()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddNewsShouldThrowExceptionEmptyText() {
        assertThatThrownBy(() -> service.addNews(getModificationDtoWithEmptyText(), getAdmin()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddNewsShouldThrowExceptionNullText() {
        assertThatThrownBy(() -> service.addNews(getModificationDtoWithNullText(), getAdmin()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkAddNewsByShouldThrowNoPermissionException() {
        assertThatThrownBy(() -> service.addNews(getModificationDto(), getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_PERMISSIONS + CANNOT_END + Operation.ADD.getName());
    }

    @Test
    void checkUpdateNewsShouldReturnDtoWithUpdatedFields() {
        NewsDto actualNews = service.updateNews(1L, getModificationDto(), getAdmin());
        NewsDto expectedNews = getUpdatedNews();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void checkUpdateNewsShouldReturnExistInDbWithUpdatedText() {
        long idForUpdate = 1L;
        String textBeforeUpdate = repository.findById(idForUpdate).get().getText();
        service.updateNews(idForUpdate, getModificationDto(), getAdmin());
        String textAfterUpdate = repository.findById(idForUpdate).get().getText();

        assertThat(textBeforeUpdate).isNotEqualTo(textAfterUpdate);
    }

    @Test
    void checkUpdateNewsShouldThrowExceptionEmptyText() {
        assertThatThrownBy(() -> service.updateNews(1L, getModificationDtoWithEmptyText(), getAdmin()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkUpdateNewsShouldNotUpdateValueIfNullText() {
        service.updateNews(1L, getModificationDtoWithNullText(), getAdmin());
        assertThatCode(() -> repository.flush()).doesNotThrowAnyException();
    }

    @Test
    void checkUpdateNewsShouldThrowExceptionEmptyTitle() {
        assertThatThrownBy(() -> service.updateNews(1L, getModificationDtoWithEmptyTitle(), getAdmin()))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void checkUpdateNewsShouldNotUpdateValueIfNullTitle() {
        service.updateNews(1L, getModificationDtoWithNullTitle(), getAdmin());
        assertThatCode(() -> repository.flush()).doesNotThrowAnyException();
    }

    @Test
    void checkUpdateNewsShouldThrowExceptionNotOwner() {
        assertThatThrownBy(() -> service.updateNews(3L, getModificationDto(), getJournalist()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_NEWS_OWNER + CANNOT_END + Operation.UPDATE.getName());
    }

    @Test
    void checkUpdateNewsShouldThrowNoPermissionException() {
        assertThatThrownBy(() -> service.updateNews(3L, getModificationDto(), getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_PERMISSIONS + CANNOT_END + Operation.UPDATE.getName());
    }

    @Test
    void checkDeleteNewsByIdShouldNotExistInDbAfterExecution() {
        long idForDelete = 1L;
        service.deleteNewsById(idForDelete, getAdmin());

        assertThat(repository.findById(idForDelete)).isEmpty();
    }

    @Test
    void checkDeleteNewsByIdShouldNotExistNewsCommentsInDbAfterExecution() {
        long idForDelete = 1L;
        service.deleteNewsById(idForDelete, getAdmin());

        assertThat(commentsRepository.findAllByNewsId(idForDelete, getDefaultPageable())).isEmpty();
    }

    @Test
    void checkDeleteNewsByIdShouldThrowNotFoundException() {
        assertThatThrownBy(() -> service.deleteNewsById(100L, getAdmin()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.DELETE.getName());
    }

    @Test
    void checkDeleteNewsByIdShouldThrowNotOwnerException() {
        assertThatThrownBy(() -> service.deleteNewsById(3L, getJournalist()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_NEWS_OWNER + CANNOT_END + Operation.DELETE.getName());
    }

    @Test
    void checkDeleteNewsByIdShouldThrowNoPermissionException() {
        assertThatThrownBy(() -> service.deleteNewsById(3L, getSubscriber()))
                .isInstanceOf(AccessException.class)
                .hasMessage(NOT_PERMISSIONS + CANNOT_END + Operation.DELETE.getName());
    }
}