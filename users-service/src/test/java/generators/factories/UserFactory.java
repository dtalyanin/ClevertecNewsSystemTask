package generators.factories;

import generators.builders.UserTestBuilder;
import ru.clevertec.users.models.Role;
import ru.clevertec.users.models.User;

import java.util.List;

public class UserFactory {

    public static User getSubscriber() {
        return UserTestBuilder.builder().build();
    }

    public static User getSubscriber2() {
        return UserTestBuilder.builder()
                .withId(2L)
                .withUsername("User 2")
                .build();
    }

    public static User getSubscriber3() {
        return UserTestBuilder.builder()
                .withId(3L)
                .withUsername("User 3")
                .build();
    }

    public static User getAdmin() {
        return UserTestBuilder.builder()
                .withId(4L)
                .withUsername("User 4")
                .withRole(Role.ADMIN)
                .build();
    }

    public static User getJournalist() {
        return UserTestBuilder.builder()
                .withId(5L)
                .withUsername("User 5")
                .withRole(Role.JOURNALIST)
                .build();
    }

    public static User getCreatedUser() {
        return UserTestBuilder.builder()
                .withUsername("User new")
                .withId(null)
                .build();
    }

    public static User getUpdatedUser() {
        return UserTestBuilder.builder()
                .withId(4L)
                .withUsername("User 4")
                .build();
    }

    public static List<User> getAllUsers() {
        return List.of(getSubscriber(), getSubscriber2(), getSubscriber3(), getAdmin(), getJournalist());
    }
}
