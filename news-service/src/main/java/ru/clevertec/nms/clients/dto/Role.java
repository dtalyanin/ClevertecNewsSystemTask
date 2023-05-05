package ru.clevertec.nms.clients.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum Role {

    ADMIN(Set.of(Permission.NEWS_MANAGE, Permission.COMMENTS_MENAGE)),
    JOURNALIST(Set.of(Permission.NEWS_MANAGE, Permission.COMMENTS_MENAGE)),
    SUBSCRIBER(Collections.singleton(Permission.COMMENTS_MENAGE));

    private static final String ROLE_PREFIX = "ROLE_";

    private final Set<Permission> permissions;

    public String getNameWithPrefix() {
        return ROLE_PREFIX + this.name();
    }
}
