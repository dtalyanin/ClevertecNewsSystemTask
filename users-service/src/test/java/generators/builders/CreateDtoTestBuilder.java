package generators.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.users.dto.CreateDto;
import ru.clevertec.users.models.Role;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class CreateDtoTestBuilder implements TestBuilder<CreateDto> {

    private String username = "User new";
    private String password = "password";
    private Role role = Role.SUBSCRIBER;

    @Override
    public CreateDto build() {
        CreateDto dto = new CreateDto();
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setRole(role);
        return dto;
    }
}
