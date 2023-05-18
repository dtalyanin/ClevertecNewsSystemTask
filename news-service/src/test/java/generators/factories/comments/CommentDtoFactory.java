package generators.factories.comments;

import generators.builders.CommentDtoTestBuilder;
import ru.clevertec.news.dto.comments.CommentDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class CommentDtoFactory {

    public static CommentDto getCommentDto1FromUser() {
        return CommentDtoTestBuilder.builder().build();
    }

    public static CommentDto getCommentDto2FromUser() {
        return CommentDtoTestBuilder.builder()
                .withId(2L)
                .withText("Comment 2")
                .withTime(LocalDateTime.parse("2023-01-01T12:00:00"))
                .build();
    }

    public static CommentDto getCommentDto3FromUser2() {
        return CommentDtoTestBuilder.builder()
                .withId(3L)
                .withText("Comment 3")
                .withUsername("User 2")
                .withTime(LocalDateTime.parse("2023-01-01T13:00:00"))
                .build();
    }

    public static CommentDto getCommentDto4FromUser3() {
        return CommentDtoTestBuilder.builder()
                .withId(4L)
                .withText("Comment 4")
                .withUsername("User 3")
                .withTime(LocalDateTime.parse("2023-01-01T14:00:00"))
                .build();
    }

    public static CommentDto getCommentDto5FromUser() {
        return CommentDtoTestBuilder.builder()
                .withId(5L)
                .withTime(LocalDateTime.parse("2023-02-01T11:00:00"))
                .build();
    }

    public static CommentDto getCommentDto6FromUser2() {
        return CommentDtoTestBuilder.builder()
                .withId(6L)
                .withText("Comment 2")
                .withUsername("User 2")
                .withTime(LocalDateTime.parse("2023-02-01T12:00:00"))
                .build();
    }

    public static CommentDto getDtoToSearchByTextIgnoreCase() {
        return  CommentDtoTestBuilder.builder()
                .withId(null)
                .withText("coMMent 2")
                .withUsername(null)
                .withTime(null)
                .build();
    }

    public static CommentDto getDtoToSearchByUsernameIgnoreCase() {
        return  CommentDtoTestBuilder.builder()
                .withId(null)
                .withText(null)
                .withUsername("uSeR 2")
                .withTime(null)
                .build();
    }

    public static CommentDto getDtoToSearchByUsernameAndText() {
        return  CommentDtoTestBuilder.builder()
                .withId(null)
                .withText("Comment")
                .withUsername("User 3")
                .withTime(null)
                .build();
    }

    public static CommentDto getDtoToSearchByDate() {
        return  CommentDtoTestBuilder.builder()
                .withId(null)
                .withText(null)
                .withUsername(null)
                .withTime(LocalDateTime.parse("2023-02-01T12:00:00"))
                .build();
    }

    public static CommentDto getDtoToSearchWithNotExistingUsername () {
        return  CommentDtoTestBuilder.builder()
                .withId(null)
                .withText(null)
                .withUsername("User not exist")
                .withTime(null)
                .build();
    }

    public static CommentDto getDtoToSearchWithNotExistingText () {
        return  CommentDtoTestBuilder.builder()
                .withId(null)
                .withText("Comment not exist")
                .withUsername(null)
                .withTime(null)
                .build();
    }

    public static CommentDto getDtoToSearchWithNotExistingDate() {
        return  CommentDtoTestBuilder.builder()
                .withId(null)
                .withText(null)
                .withUsername(null)
                .withTime(LocalDateTime.parse("2022-02-01T12:00:00"))
                .build();
    }

    public static CommentDto getDtoToSearchIgnoreId() {
        return  CommentDtoTestBuilder.builder()
                .withId(1L)
                .withText(null)
                .withUsername(null)
                .withTime(null)
                .build();
    }

    public static CommentDto getCreatedCommentDto() {
        return CommentDtoTestBuilder.builder()
                .withId(7L)
                .withText("Comment created")
                .build();
    }

    public static CommentDto getCommentDto1WithUpdatedText() {
        return CommentDtoTestBuilder.builder()
                .withText("Comment updated")
                .build();
    }

    public static List<CommentDto> getAllComments() {
        return List.of(getCommentDto1FromUser(), getCommentDto2FromUser(), getCommentDto3FromUser2(),
                getCommentDto4FromUser3(), getCommentDto5FromUser(), getCommentDto6FromUser2());
    }

    public static List<CommentDto> getAllCommentsForNews1() {
        return List.of(getCommentDto1FromUser(), getCommentDto2FromUser(), getCommentDto3FromUser2(),
                getCommentDto4FromUser3());
    }

    public static List<CommentDto> getAllCommentsForNews1WithSize2() {
        return List.of(getCommentDto1FromUser(), getCommentDto2FromUser());
    }

    public static List<CommentDto> getFirst2CommentsDto() {
        return List.of(getCommentDto1FromUser(), getCommentDto2FromUser());
    }

    public static List<CommentDto> getFrom2PageFirst2Comments() {
        return List.of(getCommentDto3FromUser2(), getCommentDto4FromUser3());
    }

    public static List<CommentDto> getCommentsDtoEmptyList() {
        return Collections.emptyList();
    }

    public static List<CommentDto> getSearchedByText() {
        return List.of(getCommentDto2FromUser(), getCommentDto6FromUser2());
    }

    public static List<CommentDto> getSearchedByTextWithSize1() {
        return List.of(getCommentDto2FromUser());
    }

    public static List<CommentDto> getSearchedByUsername() {
        return List.of(getCommentDto3FromUser2(), getCommentDto6FromUser2());
    }
    public static List<CommentDto> getSearchedByUsernameAndText() {
        return List.of(getCommentDto4FromUser3());
    }


    public static List<CommentDto> getSearchedByDate() {
        return List.of(getCommentDto6FromUser2());
    }
}
