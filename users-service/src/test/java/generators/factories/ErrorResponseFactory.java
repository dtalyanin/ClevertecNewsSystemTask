package generators.factories;

import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.exceptions.models.ErrorResponse;
import ru.clevertec.exceptions.models.IncorrectValueErrorResponse;

import static ru.clevertec.users.utils.constants.MessageConstants.*;

public class ErrorResponseFactory {

    public static ErrorResponse getResponseWithAuthException() {
        return new ErrorResponse(INCORRECT_AUTH_DATA, ErrorCode.INCORRECT_AUTHENTICATION_DATA.getCode());
    }

    public static ErrorResponse getResponseWithTokenNotValid() {
        return new ErrorResponse(TOKEN_NOT_VALID, ErrorCode.TOKEN_NOT_VALID.getCode());
    }

    public static ErrorResponse getResponseWithIncorrectToken() {
        return new ErrorResponse(TOKEN_NOT_VALID, ErrorCode.INCORRECT_TOKEN.getCode());
    }

    public static ErrorResponse getResponseWithTokenExceptionTokenExpired() {
        return new ErrorResponse(TOKEN_NOT_VALID, ErrorCode.TOKEN_EXPIRED.getCode());
    }

    public static IncorrectValueErrorResponse getResponseWithUserNotFoundException() {
        return new IncorrectValueErrorResponse(USER_ID_NOT_FOUND,100L,ErrorCode.USER_ID_NOT_FOUND.getCode());
    }

    public static IncorrectValueErrorResponse getResponseWithUserNotFoundExceptionForDelete() {
        return new IncorrectValueErrorResponse(USER_ID_NOT_FOUND + CANNOT_DELETE_END,
                100L,ErrorCode.USER_ID_NOT_FOUND.getCode());
    }

    public static IncorrectValueErrorResponse getResponseWithUserNotFoundExceptionForUpdate() {
        return new IncorrectValueErrorResponse(USER_ID_NOT_FOUND + CANNOT_UPDATE_END,
                100L,ErrorCode.USER_ID_NOT_FOUND.getCode());
    }

    public static ErrorResponse getResponseUsernameAlreadyExist() {
        return new ErrorResponse(USER_EXIST, ErrorCode.USER_EXIST.getCode());
    }
}
