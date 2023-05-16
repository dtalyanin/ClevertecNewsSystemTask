package generators.factories;

import ru.clevertec.users.models.responses.ModificationResponse;

public class ModificationResponseFactory {

    public static ModificationResponse getUserAddedResponse() {
        return new ModificationResponse(6L, "User added successfully");
    }

    public static ModificationResponse getUserUpdatedResponse() {
        return new ModificationResponse(1L, "User updated successfully");
    }

    public static ModificationResponse getUserDeletedResponse() {
        return new ModificationResponse(5L, "User deleted successfully");
    }
}
