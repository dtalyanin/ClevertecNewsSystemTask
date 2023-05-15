package generators.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.Role;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class UserDtoTestBuilder implements Builder<UserDto> {
    private String username = "User";
    private Role role = Role.SUBSCRIBER;

    @Override
    public UserDto build() {
        UserDto dto = new UserDto();
        dto.setUsername(username);
        dto.setRole(role);
        return dto;
    }
}
