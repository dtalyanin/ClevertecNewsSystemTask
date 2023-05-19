package generators.factories;

import ru.clevertec.users.dto.authentication.AuthenticationDto;

public class AuthenticationDtoFactory {

    public static AuthenticationDto getWithCorrectUsernameAndPassword() {
        return new AuthenticationDto("User", "Password");
    }

    public static AuthenticationDto getWithEmptyUsername() {
        return new AuthenticationDto("", "Password");
    }

    public static AuthenticationDto getWithEmptyPassword() {
        return new AuthenticationDto("User", " ".repeat(10));
    }

    public static AuthenticationDto getWithTooSmallPassword() {
        return new AuthenticationDto("User", "123");
    }

    public static AuthenticationDto getWithIncorrectPassword() {
        return new AuthenticationDto("User", "Password2");
    }

    public static AuthenticationDto getWithIncorrectUsername() {
        return new AuthenticationDto("UserNotCorrect", "Password");
    }
}
