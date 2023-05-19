package generators.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.news.dto.comments.CommentDto;

import java.time.LocalDateTime;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class CommentDtoTestBuilder implements TestBuilder<CommentDto> {

    private Long id = 1L;
    private String text = "Comment";
    private String username = "User";
    private LocalDateTime time = LocalDateTime.parse("2023-01-01T11:00:00");

    @Override
    public CommentDto build() {
        CommentDto dto = new CommentDto();
        dto.setId(id);
        dto.setText(text);
        dto.setUsername(username);
        dto.setTime(time);
        return dto;
    }
}
