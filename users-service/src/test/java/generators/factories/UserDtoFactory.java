package generators.factories;

import generators.builders.UserDtoTestBuilder;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.models.Role;

import java.util.Collections;
import java.util.List;

public class UserDtoFactory {

    public static UserDto getSubscriberDto() {
        return UserDtoTestBuilder.builder().build();
    }

    public static UserDto getSubscriberDto2() {
        return UserDtoTestBuilder.builder()
                .withId(2L)
                .withUsername("User 2")
                .build();
    }

    public static UserDto getSubscriberDto3() {
        return UserDtoTestBuilder.builder()
                .withId(3L)
                .withUsername("User 3")
                .build();
    }

    public static UserDto getAdminDto() {
        return UserDtoTestBuilder.builder()
                .withId(4L)
                .withUsername("User 4")
                .withRole(Role.ADMIN)
                .build();
    }

    public static UserDto getJournalistDto() {
        return UserDtoTestBuilder.builder()
                .withId(5L)
                .withUsername("User 5")
                .withRole(Role.JOURNALIST)
                .build();
    }

    public static UserDto getCreatedUserDto() {
        return UserDtoTestBuilder.builder()
                .withId(6L)
                .withUsername("User new")
                .withRole(Role.SUBSCRIBER)
                .build();
    }

    public static UserDto getUpdatedUserDto() {
        return UserDtoTestBuilder.builder()
                .withRole(Role.ADMIN)
                .build();
    }

    public static List<UserDto> getAllUserDtos() {
        return List.of(getSubscriberDto(), getSubscriberDto2(), getSubscriberDto3(), getAdminDto(), getJournalistDto());
    }

    public static List<UserDto> getEmptyListUserDtos() {
        return Collections.emptyList();
    }

    public static List<UserDto> getFirst2UserDtos() {
        return List.of(getSubscriberDto(), getSubscriberDto2());
    }

    public static List<UserDto> getFrom2PageFirst2UserDtos() {
        return List.of(getSubscriberDto3(), getAdminDto());
    }
}
