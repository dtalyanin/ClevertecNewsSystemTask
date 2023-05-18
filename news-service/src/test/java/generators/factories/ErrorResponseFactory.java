package generators.factories;

import org.springframework.http.HttpHeaders;
import ru.clevertec.exceptions.models.ErrorCode;
import ru.clevertec.exceptions.models.ErrorResponse;
import ru.clevertec.exceptions.models.IncorrectValueErrorResponse;
import ru.clevertec.news.models.enums.Operation;

import static ru.clevertec.exceptions.utils.constants.MessageConstants.HEADER_NOT_PRESENT;
import static ru.clevertec.news.utils.constants.MessageConstants.*;


public class ErrorResponseFactory {

    public static IncorrectValueErrorResponse getResponseWithCommentNotFoundExceptionForGet() {
        return new IncorrectValueErrorResponse(COMMENT_NOT_FOUND + CANNOT_END + Operation.GET.getName(),
                100L,ErrorCode.COMMENT_NOT_FOUND.getCode());
    }

    public static IncorrectValueErrorResponse getResponseWithNewsNotFoundExceptionForGet() {
        return new IncorrectValueErrorResponse(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.GET.getName(),
                100L,ErrorCode.NEWS_NOT_FOUND.getCode());
    }

    public static IncorrectValueErrorResponse getResponseWithCommentNotFoundExceptionForDelete() {
        return new IncorrectValueErrorResponse(COMMENT_NOT_FOUND + CANNOT_END + Operation.DELETE.getName(),
                100L,ErrorCode.COMMENT_NOT_FOUND.getCode());
    }

    public static IncorrectValueErrorResponse getResponseWithNewsNotFoundExceptionForAdd() {
        return new IncorrectValueErrorResponse(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.ADD.getName(),
                100L,ErrorCode.NEWS_NOT_FOUND.getCode());
    }

    public static IncorrectValueErrorResponse getResponseWithNewsNotFoundExceptionForUpdate() {
        return new IncorrectValueErrorResponse(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.UPDATE.getName(),
                100L,ErrorCode.NEWS_NOT_FOUND.getCode());
    }

    public static IncorrectValueErrorResponse getResponseWithNewsNotFoundExceptionForDelete() {
        return new IncorrectValueErrorResponse(NEWS_WITH_ID_NOT_FOUND + CANNOT_END + Operation.DELETE.getName(),
                100L,ErrorCode.NEWS_NOT_FOUND.getCode());
    }

    public static ErrorResponse getResponseWithNotOwnerCommentForDelete() {
        return new ErrorResponse(NOT_COMMENT_OWNER + CANNOT_END + Operation.DELETE.getName(),
                ErrorCode.NOT_OWNER_FOR_COMMENT_MODIFICATION.getCode());
    }

    public static ErrorResponse getResponseWithNotOwnerNewsForDelete() {
        return new ErrorResponse(NOT_NEWS_OWNER + CANNOT_END + Operation.DELETE.getName(),
                ErrorCode.NOT_OWNER_FOR_NEWS_MODIFICATION.getCode());
    }

    public static ErrorResponse getResponseWithNotOwnerCommentForUpdate() {
        return new ErrorResponse(NOT_COMMENT_OWNER + CANNOT_END + Operation.UPDATE.getName(),
                ErrorCode.NOT_OWNER_FOR_COMMENT_MODIFICATION.getCode());
    }

    public static ErrorResponse getResponseWithNotOwnerNewsForUpdate() {
        return new ErrorResponse(NOT_NEWS_OWNER + CANNOT_END + Operation.UPDATE.getName(),
                ErrorCode.NOT_OWNER_FOR_NEWS_MODIFICATION.getCode());
    }

    public static ErrorResponse getResponseWithNoPermissionForNewsAdd() {
        return new ErrorResponse(NOT_PERMISSIONS + CANNOT_END + Operation.ADD.getName(),
                ErrorCode.NO_PERMISSIONS_FOR_NEWS_MODIFICATION.getCode());
    }

    public static ErrorResponse getResponseWithNoPermissionForNewsUpdate() {
        return new ErrorResponse(NOT_PERMISSIONS + CANNOT_END + Operation.UPDATE.getName(),
                ErrorCode.NO_PERMISSIONS_FOR_NEWS_MODIFICATION.getCode());
    }

    public static ErrorResponse getResponseWithNoPermissionForNewsDelete() {
        return new ErrorResponse(NOT_PERMISSIONS + CANNOT_END + Operation.DELETE.getName(),
                ErrorCode.NO_PERMISSIONS_FOR_NEWS_MODIFICATION.getCode());
    }

    public static ErrorResponse getResponseWithNoAuthHeader() {
        return new ErrorResponse(HttpHeaders.AUTHORIZATION + HEADER_NOT_PRESENT,
                ErrorCode.NO_AUTH_HEADER.getCode());
    }
}
