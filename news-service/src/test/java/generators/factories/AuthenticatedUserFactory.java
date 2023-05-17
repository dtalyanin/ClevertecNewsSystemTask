package generators.factories;

import generators.builders.AuthenticatedUserTestBuilder;
import ru.clevertec.news.clients.dto.AuthenticatedUser;
import ru.clevertec.news.clients.dto.Role;

public class AuthenticatedUserFactory {

    public static AuthenticatedUser getAdmin() {
        return AuthenticatedUserTestBuilder.builder().build();
    }

    public static AuthenticatedUser getSubscriber() {
        return AuthenticatedUserTestBuilder.builder()
                .withRole(Role.SUBSCRIBER)
                .build();
    }

    public static AuthenticatedUser getJournalist() {
        return AuthenticatedUserTestBuilder.builder()
                .withRole(Role.JOURNALIST)
                .build();
    }
}
