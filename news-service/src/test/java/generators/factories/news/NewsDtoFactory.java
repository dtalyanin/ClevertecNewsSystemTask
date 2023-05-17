package generators.factories.news;

import generators.builders.NewsDtoTestBuilder;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;

import java.time.LocalDateTime;
import java.util.List;

import static generators.factories.comments.CommentDtoFactory.*;

public class NewsDtoFactory {

    public static NewsDto getNews1() {
        return NewsDtoTestBuilder.builder().build();
    }

    public static NewsDto getNews2() {
        return NewsDtoTestBuilder.builder()
                .withId(2L)
                .withTitle("News 2")
                .withText("Text 2")
                .withUsername("User 2")
                .withTime(LocalDateTime.parse("2023-02-01T10:00:00"))
                .build();
    }

    public static NewsDto getNews3() {
        return NewsDtoTestBuilder.builder()
                .withId(3L)
                .withTitle("News 3")
                .withText("Text 3")
                .withUsername("User 3")
                .withTime(LocalDateTime.parse("2023-03-01T10:00:00"))
                .build();
    }

    public static NewsDto getCreatedNews() {
        return NewsDtoTestBuilder.builder()
                .withId(4L)
                .withTitle("News new")
                .withText("Text new")
                .withTime(LocalDateTime.now())
                .build();
    }

    public static NewsWithCommentsDto getNews1WithComments() {
        NewsWithCommentsDto dto = new NewsWithCommentsDto();
        dto.setId(1L);
        dto.setTitle("News");
        dto.setText("Text");
        dto.setTime(LocalDateTime.parse("2023-01-01T10:00:00"));
        dto.setComments(getAllCommentsForNews1());
        return dto;
    }

    public static NewsWithCommentsDto getNews1With2Comments() {
        NewsWithCommentsDto dto = new NewsWithCommentsDto();
        dto.setId(1L);
        dto.setTitle("News");
        dto.setText("Text");
        dto.setTime(LocalDateTime.parse("2023-01-01T10:00:00"));
        dto.setComments(getFirst2Comments());
        return dto;
    }

    public static NewsWithCommentsDto getNews3WithNoComments() {
        NewsWithCommentsDto dto = new NewsWithCommentsDto();
        dto.setId(3L);
        dto.setTitle("News 3");
        dto.setText("Text 3");
        dto.setTime(LocalDateTime.parse("2023-03-01T10:00:00"));
        dto.setComments(getEmptyListComments());
        return dto;
    }

    public static NewsDto getUpdatedNews() {
        return NewsDtoTestBuilder.builder()
                .withTitle("News new")
                .withText("Text new")
                .build();
    }

    public static List<NewsDto> getAllNews() {
        return List.of(getNews1(), getNews2(), getNews3());
    }

    public static List<NewsDto> getFirst2News() {
        return List.of(getNews1(), getNews2());
    }

    public static List<NewsDto> getNews2And3() {
        return List.of(getNews2(), getNews3());
    }

    public static List<NewsDto> getNews2AsList() {
        return List.of(getNews2());
    }

    public static List<NewsDto> getNews3AsList() {
        return List.of(getNews3());
    }

    public static NewsDto getDtoToSearchByTextIgnoreCase() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle("nEwS ")
                .withText(null)
                .withUsername(null)
                .withTime(null)
                .build();
    }

    public static NewsDto getDtoToSearchByTitleIgnoreCase() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle(null)
                .withText("tExT ")
                .withUsername(null)
                .withTime(null)
                .build();
    }

    public static NewsDto  getDtoToSearchByUsernameIgnoreCase() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle(null)
                .withText(null)
                .withUsername("uSeR ")
                .withTime(null)
                .build();
    }

    public static NewsDto  getDtoToSearchByUsernameAndText() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle(null)
                .withText("tExT 2")
                .withUsername("uSeR 2")
                .withTime(null)
                .build();
    }

    public static NewsDto getDtoToSearchByDate() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle(null)
                .withText(null)
                .withUsername(null)
                .withTime(LocalDateTime.parse("2023-03-01T10:00:00"))
                .build();
    }

    public static NewsDto getDtoToSearchWithNotExistingUsername() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle(null)
                .withText(null)
                .withUsername("User not exist")
                .withTime(null)
                .build();
    }

    public static NewsDto getDtoToSearchWithNotExistingText() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle(null)
                .withText("Text not exist")
                .withUsername(null)
                .withTime(null)
                .build();
    }

    public static NewsDto getDtoToSearchWithNotExistingDate() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle(null)
                .withText(null)
                .withUsername(null)
                .withTime(LocalDateTime.parse("2022-03-01T10:00:00"))
                .build();
    }

    public static NewsDto getDtoToSearchWithNotExistingTitle() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle("Title not exist")
                .withText(null)
                .withUsername(null)
                .withTime(null)
                .build();
    }

    public static NewsDto getDtoToSearchIgnoreId() {
        return NewsDtoTestBuilder.builder()
                .withId(1L)
                .withTitle(null)
                .withText(null)
                .withUsername(null)
                .withTime(null)
                .build();
    }
}
