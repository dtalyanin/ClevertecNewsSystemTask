package ru.clevertec.nms.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthenticatedUser {
    private String username;
    private List<String> authorities;
}
