package generators.factories;

import ru.clevertec.news.models.responses.ModificationResponse;

public class ModificationResponseFactory {

    public static ModificationResponse getCommentAddedResponse() {
        return new ModificationResponse(7L, "Comment added successfully");
    }

    public static ModificationResponse getCommentUpdatedResponse() {
        return new ModificationResponse(1L, "Comment updated successfully");
    }

    public static ModificationResponse getCommentDeletedResponse() {
        return new ModificationResponse(6L, "Comment deleted successfully");
    }

    public static ModificationResponse getNewsAddedResponse() {
        return new ModificationResponse(4L, "News added successfully");
    }

    public static ModificationResponse getNewsUpdatedResponse() {
        return new ModificationResponse(1L, "News updated successfully");
    }

    public static ModificationResponse getNewsDeletedResponse() {
        return new ModificationResponse(1L, "News deleted successfully");
    }
}
