package generators.factories;

import generators.builders.UserDtoTestBuilder;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.Role;


import java.util.Collections;
import java.util.List;

public class UserDtoFactory {

    public static UserDto getSubscriber() {
        return UserDtoTestBuilder.builder().build();
    }

    public static UserDto getSubscriber2() {
        return UserDtoTestBuilder.builder()
                .withUsername("User 2")
                .build();
    }

    public static UserDto getSubscriber3() {
        return UserDtoTestBuilder.builder()
                .withUsername("User 3")
                .build();
    }

    public static UserDto getAdmin() {
        return UserDtoTestBuilder.builder()
                .withUsername("User 4")
                .withRole(Role.ADMIN)
                .build();
    }

    public static UserDto getJournalist() {
        return UserDtoTestBuilder.builder()
                .withUsername("User 5")
                .withRole(Role.JOURNALIST)
                .build();
    }

    public static List<UserDto> getAllUserDtos() {
        return List.of(getSubscriber(), getSubscriber2(), getSubscriber3(), getAdmin(), getJournalist());
    }

    public static List<UserDto> getEmptyListUserDtos() {
        return Collections.emptyList();
    }

    public static List<UserDto> getFirst2UserDtos() {
        return List.of(getSubscriber(), getSubscriber2());
    }

    public static List<UserDto> getFrom2PageFirst2UserDtos() {
        return List.of(getSubscriber3(), getAdmin());
    }
}
