package ru.clevertec.nms.utils;

import lombok.experimental.UtilityClass;
import ru.clevertec.nms.clients.dto.Permission;
import ru.clevertec.nms.clients.dto.Role;
import ru.clevertec.nms.models.AuthenticatedUser;

@UtilityClass
public class UserHelper {

    public boolean checkUserIsNotOwner(AuthenticatedUser user, String owner) {
        return user.getRole() != Role.ADMIN && !user.getUsername().equals(owner);
    }

    public boolean checkUserHasNotPermission(AuthenticatedUser user, Permission requiredPermission) {
        return  user.getRole() != Role.ADMIN && !user.getRole().getPermissions().contains(requiredPermission);
    }
}
