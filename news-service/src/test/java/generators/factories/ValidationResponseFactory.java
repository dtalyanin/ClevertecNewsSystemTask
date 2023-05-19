package generators.factories;

import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.exceptions.models.SingleFieldValidationResponse;
import ru.clevertec.exceptions.models.ValidationResponse;

import static ru.clevertec.news.utils.constants.MessageConstants.*;

public class ValidationResponseFactory {

    public static ValidationResponse getResponseNotCorrectId() {
        return new SingleFieldValidationResponse(-1, MIN_ID_MESSAGE,
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    public static ValidationResponse getResponseEmptyCommentText() {
        return new SingleFieldValidationResponse(" ", EMPTY_COMMENT_TEXT,
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    public static ValidationResponse getResponseEmptyNewsText() {
        return new SingleFieldValidationResponse(" ", EMPTY_NEWS_TEXT,
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }

    public static ValidationResponse getResponseEmptyNewsTitle() {
        return new SingleFieldValidationResponse(" ", EMPTY_NEWS_TITLE,
                ErrorCode.INCORRECT_FIELD_VALUE.getCode());
    }
}
