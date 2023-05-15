package generators.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.users.models.Role;
import ru.clevertec.users.models.User;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class UserTestBuilder implements Builder<User> {

    private Long id = 1L;
    private String username = "User";
    private String password = "Password";
    private Role role = Role.SUBSCRIBER;

    @Override
    public User build() {
        return new User(id, username, password, role);
    }
}
