package generators.factories.news;

import generators.builders.NewsDtoTestBuilder;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static generators.factories.comments.CommentDtoFactory.*;

public class NewsDtoFactory {

    public static NewsDto getNewsDto1() {
        return NewsDtoTestBuilder.builder().build();
    }

    public static NewsDto getNewsDto2() {
        return NewsDtoTestBuilder.builder()
                .withId(2L)
                .withTitle("News 2")
                .withText("Text 2")
                .withUsername("User 2")
                .withTime(LocalDateTime.parse("2023-02-01T10:00:00"))
                .build();
    }

    public static NewsDto getNewsDto3() {
        return NewsDtoTestBuilder.builder()
                .withId(3L)
                .withTitle("News 3")
                .withText("Text 3")
                .withUsername("User 3")
                .withTime(LocalDateTime.parse("2023-03-01T10:00:00"))
                .build();
    }

    public static NewsDto getCreatedNewsDto() {
        return NewsDtoTestBuilder.builder()
                .withId(4L)
                .withTitle("News new")
                .withText("Text new")
                .withTime(LocalDateTime.now())
                .build();
    }

    public static NewsWithCommentsDto getNewsDto1WithComments() {
        NewsWithCommentsDto dto = new NewsWithCommentsDto();
        dto.setId(1L);
        dto.setTitle("News");
        dto.setText("Text");
        dto.setUsername("User");
        dto.setTime(LocalDateTime.parse("2023-01-01T10:00:00"));
        dto.setComments(getAllCommentsForNews1());
        return dto;
    }

    public static NewsWithCommentsDto getNewsDto1With2Comments() {
        NewsWithCommentsDto dto = new NewsWithCommentsDto();
        dto.setId(1L);
        dto.setTitle("News");
        dto.setText("Text");
        dto.setUsername("User");
        dto.setTime(LocalDateTime.parse("2023-01-01T10:00:00"));
        dto.setComments(getFirst2CommentsDto());
        return dto;
    }

    public static NewsWithCommentsDto getNewsDto3WithNoComments() {
        NewsWithCommentsDto dto = new NewsWithCommentsDto();
        dto.setId(3L);
        dto.setTitle("News 3");
        dto.setText("Text 3");
        dto.setUsername("User 3");
        dto.setTime(LocalDateTime.parse("2023-03-01T10:00:00"));
        dto.setComments(getCommentsDtoEmptyList());
        return dto;
    }

    public static NewsDto getUpdatedNewsDto() {
        return NewsDtoTestBuilder.builder()
                .withTitle("News new")
                .withText("Text new")
                .build();
    }

    public static List<NewsDto> getAllNewsDtos() {
        return List.of(getNewsDto1(), getNewsDto2(), getNewsDto3());
    }

    public static List<NewsDto> getFirst2NewsDtos() {
        return List.of(getNewsDto1(), getNewsDto2());
    }

    public static List<NewsDto> getNews2And3Dtos() {
        return List.of(getNewsDto2(), getNewsDto3());
    }

    public static List<NewsDto> getNewsDto2AsList() {
        return List.of(getNewsDto2());
    }

    public static List<NewsDto> getNewsDto3AsList() {
        return List.of(getNewsDto3());
    }

    public static List<NewsDto> getEmptyListNewsDtos() {
        return Collections.emptyList();
    }

    public static NewsDto getDtoToSearchByTextIgnoreCase() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle(null)
                .withText("tExT ")
                .withUsername(null)
                .withTime(null)
                .build();
    }

    public static NewsDto getDtoToSearchByTitleIgnoreCase() {
        return NewsDtoTestBuilder.builder()
                .withId(null)
                .withTitle("nEwS ")
                .withText(null)
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
