package ru.clevertec.news.utils.mappers;

import generators.builders.CommentDtoTestBuilder;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.models.Comment;

import java.time.LocalDateTime;
import java.util.List;

import static generators.factories.comments.CommentDtoFactory.*;
import static generators.factories.comments.CommentFactory.*;
import static generators.factories.comments.CreateCommentDtoFactory.*;
import static generators.factories.comments.UpdateCommentDtoFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class CommentsMapperTest {

    private final CommentsMapper mapper = Mappers.getMapper(CommentsMapper.class);

    @Test
    void checkConvertCommentToDtoShouldReturnCorrectDto() {
        CommentDto actualDto = mapper.convertCommentToDto(getComment1());
        CommentDto expectedDto = getCommentDto1FromUser();

        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    void checkConvertDtoToCommentShouldReturnCommentWithoutNews() {
        Comment actualComment = mapper.convertDtoToComment(CommentDtoTestBuilder.builder().withId(null).build());
        Comment expectedComment = getCommentWithoutIdAndNews();

        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    void checkConvertAllCommentsToDtosShouldReturl2Dtos() {
        List<CommentDto> actualDtos = mapper.convertAllCommentsToDtos(getAll2Comments());
        CommentDto expectedSecondDto = CommentDtoTestBuilder.builder()
                .withId(2L)
                .withText("Comment 2")
                .withUsername("User 2")
                .withTime(LocalDateTime.parse("2023-01-01T12:00:00"))
                .build();
        List<CommentDto> expectedDtos = List.of(getCommentDto1FromUser(), expectedSecondDto);

        assertThat(actualDtos).isEqualTo(expectedDtos);
    }

    @Test
    void convertCreateDtoToCommentShouldReturnCommentWithoutNewsTimeAndId() {
        Comment actualComment = mapper.convertCreateDtoToComment(getCreateDto(), "User");
        Comment expectedComment = getCreatedComment();

        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    void updateCommentShouldUpdateText() {
        Comment actualComment = mapper.updateComment(getComment1(), getUpdateDto());
        Comment expectedComment = getUpdateComment();

        assertThat(actualComment).isEqualTo(expectedComment);
    }
}