package generators.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.news.dto.news.ModificationNewsDto;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class ModificationNewsDtoTestBuilder implements TestBuilder<ModificationNewsDto> {

    private String title = "News";
    private String text = "Text";

    @Override
    public ModificationNewsDto build() {
        ModificationNewsDto dto = new ModificationNewsDto();
        dto.setTitle(title);
        dto.setText(text);
        return dto;
    }
}
