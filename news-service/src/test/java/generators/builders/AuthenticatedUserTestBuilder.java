package generators.builders;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.news.clients.dto.AuthenticatedUser;
import ru.clevertec.news.clients.dto.Role;

@NoArgsConstructor(staticName = "builder")
@With
@AllArgsConstructor
public class AuthenticatedUserTestBuilder implements TestBuilder<AuthenticatedUser> {

    private String username = "User";
    private Role role = Role.ADMIN;

    @Override
    public AuthenticatedUser build() {
        return new AuthenticatedUser(username, role);
    }
}
