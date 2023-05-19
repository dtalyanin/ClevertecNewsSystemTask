package generators.factories;

import ru.clevertec.users.dto.authentication.TokenDto;

public class TokenDtoFactory {

    public static TokenDto getTokenDto() {
        return new TokenDto("token");
    }
}
