package ru.clevertec.news.utils;

import ru.clevertec.news.clients.dto.Permission;
import ru.clevertec.news.clients.dto.Role;
import ru.clevertec.news.clients.dto.AuthenticatedUser;

/**
 * Class for check user permissions
 */
public class UserHelper {

    /**
     * Check that user not owner or not admin
     * @param user authenticated user who performs operation
     * @param owner item owner
     * @return true if user not owner and not admin
     */
    public static boolean checkUserIsNotOwner(AuthenticatedUser user, String owner) {
        return user.getRole() != Role.ADMIN && !user.getUsername().equals(owner);
    }

    /**
     *
     * @param user authenticated user who performs operation
     * @param requiredPermission permission that user must have
     * @return true if user hasn't permissions and not admin
     */
    public static boolean checkUserHasNotPermission(AuthenticatedUser user, Permission requiredPermission) {
        return  user.getRole() != Role.ADMIN && !user.getRole().getPermissions().contains(requiredPermission);
    }
}
