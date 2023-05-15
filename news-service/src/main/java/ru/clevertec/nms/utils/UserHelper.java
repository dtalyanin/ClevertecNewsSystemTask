package ru.clevertec.nms.utils;

import ru.clevertec.nms.clients.dto.Permission;
import ru.clevertec.nms.clients.dto.Role;
import ru.clevertec.nms.models.AuthenticatedUser;

public class UserHelper {

    public static boolean checkUserIsNotOwner(AuthenticatedUser user, String owner) {
        return user.getRole() != Role.ADMIN && !user.getUsername().equals(owner);
    }

    public static boolean checkUserHasNotPermission(AuthenticatedUser user, Permission requiredPermission) {
        return  user.getRole() != Role.ADMIN && !user.getRole().getPermissions().contains(requiredPermission);
    }
}
