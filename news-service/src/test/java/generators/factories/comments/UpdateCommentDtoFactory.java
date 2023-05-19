package generators.factories.comments;

import ru.clevertec.news.dto.comments.UpdateCommentDto;

public class UpdateCommentDtoFactory {

    public static UpdateCommentDto getUpdateDto() {
        UpdateCommentDto dto = new UpdateCommentDto();
        dto.setText("Comment updated");
        return dto;
    }

    public static UpdateCommentDto getUpdateDtoWithNullText() {
        return new UpdateCommentDto();
    }

    public static UpdateCommentDto getUpdateDtoWithEmptyText() {
        UpdateCommentDto dto = new UpdateCommentDto();
        dto.setText(" ");
        return dto;
    }
}
