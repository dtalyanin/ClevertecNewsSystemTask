package generators.factories;

import generators.builders.UpdateDtoTestBuilder;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.models.Role;

public class UpdateDtoFactory {

    public static UpdateDto getUpdateDto() {
        return UpdateDtoTestBuilder.builder().build();
    }

    public static UpdateDto getUpdateDtoWithOnlyUpdatedPassword() {
        return UpdateDtoTestBuilder.builder()
                .withPassword("123456789")
                .build();
    }

    public static UpdateDto getUpdateDtoWithOnlyUpdatedRole() {
        return UpdateDtoTestBuilder.builder()
                .withRole(Role.ADMIN)
                .build();
    }

    public static UpdateDto getUpdateDtoWithTooSmallPassword() {
        return UpdateDtoTestBuilder.builder()
                .withPassword("123")
                .build();
    }
}
