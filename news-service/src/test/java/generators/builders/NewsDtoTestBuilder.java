package generators.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.news.dto.news.NewsDto;

import java.time.LocalDateTime;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class NewsDtoTestBuilder implements TestBuilder<NewsDto> {

    private Long id = 1L;
    private String title = "News";
    private String text = "Text";
    private String username = "User";
    private LocalDateTime time = LocalDateTime.parse("2023-01-01T10:00:00");

    @Override
    public NewsDto build() {
        NewsDto dto = new NewsDto();
        dto.setId(id);
        dto.setTitle(title);
        dto.setText(text);
        dto.setUsername(username);
        dto.setTime(time);
        return dto;
    }
}
