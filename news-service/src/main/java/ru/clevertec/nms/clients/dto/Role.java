package ru.clevertec.nms.clients.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN(Set.of(Permission.NEWS_MANAGE, Permission.COMMENTS_MENAGE)),
    JOURNALIST(Collections.singleton(Permission.NEWS_MANAGE)),
    SUBSCRIBER(Collections.singleton(Permission.COMMENTS_MENAGE));

    private final Set<Permission> permissions;

    public String getNameWithRolePrefix() {
        return "ROLE_" + this.name();
    }
}
