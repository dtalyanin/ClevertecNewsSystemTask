package ru.clevertec.news.utils;

import ru.clevertec.news.clients.dto.Permission;
import ru.clevertec.news.clients.dto.Role;
import ru.clevertec.news.clients.dto.AuthenticatedUser;

public class UserHelper {

    public static boolean checkUserIsNotOwner(AuthenticatedUser user, String owner) {
        return user.getRole() != Role.ADMIN && !user.getUsername().equals(owner);
    }

    public static boolean checkUserHasNotPermission(AuthenticatedUser user, Permission requiredPermission) {
        return  user.getRole() != Role.ADMIN && !user.getRole().getPermissions().contains(requiredPermission);
    }
}
