package generators.factories;

import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.exceptions.models.SingleFieldValidationResponse;
import ru.clevertec.exceptions.models.ValidationResponse;

import static ru.clevertec.users.utils.constants.MessageConstants.*;

public class ValidationResponseFactory {

    public static ValidationResponse getResponseNotCorrectId() {
        return new SingleFieldValidationResponse(-1, MIN_ID_MESSAGE,
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    public static ValidationResponse getResponseEmptyUsername() {
        return new SingleFieldValidationResponse("", EMPTY_USERNAME,
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    public static ValidationResponse getResponseEmptyPassword() {
        return new SingleFieldValidationResponse(" ".repeat(10), EMPTY_PASSWORD,
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    public static ValidationResponse getResponseTooSmallPassword() {
        return new SingleFieldValidationResponse("123", TOO_SMALL_PASSWORD,
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    public static ValidationResponse getResponseTooBigUsername() {
        return new SingleFieldValidationResponse("a".repeat(100), TOO_BIG_USERNAME,
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }
}
