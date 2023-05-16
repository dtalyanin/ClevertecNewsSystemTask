package generators.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.users.dto.UpdateDto;
import ru.clevertec.users.models.Role;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class UpdateDtoTestBuilder implements TestBuilder<UpdateDto> {
    private String password = "Password";
    private Role role = Role.SUBSCRIBER;

    @Override
    public UpdateDto build() {
        UpdateDto dto = new UpdateDto();
        dto.setPassword(password);
        dto.setRole(role);
        return dto;
    }
}
