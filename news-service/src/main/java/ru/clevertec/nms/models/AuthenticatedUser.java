package ru.clevertec.nms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.clevertec.nms.clients.dto.Role;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthenticatedUser {
    private String username;
    private Role role;
}
