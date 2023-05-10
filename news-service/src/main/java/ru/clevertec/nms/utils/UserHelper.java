package ru.clevertec.nms.utils;

import lombok.experimental.UtilityClass;
import ru.clevertec.nms.clients.dto.Role;
import ru.clevertec.nms.models.AuthenticatedUser;

@UtilityClass
public class UserHelper {

    public boolean checkUserCannotPerformOperation(AuthenticatedUser user, String owner) {
        return !checkUserIsAdmin(user) && !checkUserIsOwner(user, owner);
    }

    private boolean checkUserIsOwner(AuthenticatedUser user, String owner) {
        return user.getUsername().equals(owner);
    }

    private boolean checkUserIsAdmin(AuthenticatedUser user) {
        return user.getRole() == Role.ADMIN;
    }
}
