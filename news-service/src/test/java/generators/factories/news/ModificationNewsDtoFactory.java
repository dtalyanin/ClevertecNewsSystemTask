package generators.factories.news;

import generators.builders.ModificationNewsDtoTestBuilder;
import ru.clevertec.news.dto.news.ModificationNewsDto;

public class ModificationNewsDtoFactory {

    public static ModificationNewsDto getModificationDto() {
        return ModificationNewsDtoTestBuilder.builder().build();
    }

    public static ModificationNewsDto getModificationDtoWithNullTitle() {
        return ModificationNewsDtoTestBuilder.builder()
                .withTitle(null)
                .build();
    }

    public static ModificationNewsDto getModificationDtoWithEmptyTitle() {
        return ModificationNewsDtoTestBuilder.builder()
                .withTitle(" ")
                .build();
    }

    public static ModificationNewsDto getModificationDtoWithNullText() {
        return ModificationNewsDtoTestBuilder.builder()
                .withText(null)
                .build();
    }

    public static ModificationNewsDto getModificationDtoWithEmptyText() {
        return ModificationNewsDtoTestBuilder.builder()
                .withText(" ")
                .build();
    }
}
