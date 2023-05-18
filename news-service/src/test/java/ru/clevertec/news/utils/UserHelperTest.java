package ru.clevertec.news.utils;

import org.junit.jupiter.api.Test;
import ru.clevertec.news.clients.dto.Permission;

import static generators.factories.AuthenticatedUserFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserHelperTest {

    @Test
    void checkUserIsNotOwnerShouldReturnTrueWhenUserIsSubscriber() {
        boolean actualCheck = UserHelper.checkUserIsNotOwner(getSubscriber(), "User 2");

        assertThat(actualCheck).isTrue();
    }

    @Test
    void checkUserIsNotOwnerShouldReturnFalseWhenUserIsSubscriber() {
        boolean actualCheck = UserHelper.checkUserIsNotOwner(getSubscriber(), "User");

        assertThat(actualCheck).isFalse();
    }

    @Test
    void checkUserIsNotOwnerShouldReturnFalseWhenUserNotOwnerButAdmin() {
        boolean actualCheck = UserHelper.checkUserIsNotOwner(getAdmin(), "User 2");

        assertThat(actualCheck).isFalse();
    }

    @Test
    void checkUserHasNotPermissionShouldReturnTrueWhenUserHasNotPermission() {
        boolean actualCheck = UserHelper.checkUserHasNotPermission(getSubscriber(), Permission.NEWS_MANAGE);

        assertThat(actualCheck).isTrue();
    }

    @Test
    void checkUserHasNotPermissionShouldReturnFalseWhenUserHasPermission() {
        boolean actualCheck = UserHelper.checkUserHasNotPermission(getJournalist(), Permission.NEWS_MANAGE);

        assertThat(actualCheck).isFalse();
    }

    @Test
    void checkUserHasNotPermissionShouldReturnFalseWhenUserAdmin() {
        boolean actualCheck = UserHelper.checkUserHasNotPermission(getAdmin(), Permission.NEWS_MANAGE);

        assertThat(actualCheck).isFalse();
    }
}