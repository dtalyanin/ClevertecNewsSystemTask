package generators.factories.comments;

import ru.clevertec.news.dto.comments.CreateCommentDto;

public class CreateCommentDtoFactory {

    public static CreateCommentDto getCreateDto() {
        CreateCommentDto dto = new CreateCommentDto();
        dto.setNewsId(1L);
        dto.setText("Comment created");
        return dto;
    }

    public static CreateCommentDto getCreateDtoWithEmptyText() {
        CreateCommentDto dto = new CreateCommentDto();
        dto.setNewsId(1L);
        dto.setText(" ");
        return dto;
    }

    public static CreateCommentDto getCreateDtoWithNullText() {
        CreateCommentDto dto = new CreateCommentDto();
        dto.setNewsId(1L);
        return dto;
    }

    public static CreateCommentDto getCreateDtoWithNotExistingNewsId() {
        CreateCommentDto dto = new CreateCommentDto();
        dto.setNewsId(100L);
        dto.setText("Comment created");
        return dto;
    }
}
