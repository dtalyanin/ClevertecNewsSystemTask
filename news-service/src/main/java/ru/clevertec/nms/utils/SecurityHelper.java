package ru.clevertec.nms.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.clevertec.nms.models.AuthenticatedUser;

import java.util.List;

@UtilityClass
public class SecurityHelper {

    public AuthenticatedUser getAuthenticatedUserFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return new AuthenticatedUser(username, authorities);
    }
}
