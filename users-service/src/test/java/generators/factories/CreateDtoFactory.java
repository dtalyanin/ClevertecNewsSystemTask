package generators.factories;

import generators.builders.CreateDtoTestBuilder;
import ru.clevertec.users.dto.CreateDto;

public class CreateDtoFactory {

    public static CreateDto getCreateDto() {
        return CreateDtoTestBuilder.builder().build();
    }

    public static CreateDto getCreateDtoWithExistedInDbName() {
        return CreateDtoTestBuilder.builder()
                .withUsername("User")
                .build();
    }

    public static CreateDto getCreateDtoWithNullName() {
        return CreateDtoTestBuilder.builder()
                .withUsername(null)
                .build();
    }

    public static CreateDto getCreateDtoWithEmptyName() {
        return CreateDtoTestBuilder.builder()
                .withUsername("")
                .build();
    }

    public static CreateDto getCreateDtoWithTooBigName() {
        return CreateDtoTestBuilder.builder()
                .withUsername("a".repeat(100))
                .build();
    }

    public static CreateDto getCreateDtoWithNullPassword() {
        return CreateDtoTestBuilder.builder()
                .withPassword(null)
                .build();
    }

    public static CreateDto getCreateDtoWithEmptyPassword() {
        return CreateDtoTestBuilder.builder()
                .withPassword(" ".repeat(10))
                .build();
    }

    public static CreateDto getCreateDtoWithTooSmallPassword() {
        return CreateDtoTestBuilder.builder()
                .withPassword("123")
                .build();
    }

    public static CreateDto getCreateDtoWithNullNameAndNullPassword() {
        return CreateDtoTestBuilder.builder()
                .withUsername(null)
                .withPassword(null)
                .build();
    }
}
