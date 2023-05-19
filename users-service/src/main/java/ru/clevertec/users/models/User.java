package ru.clevertec.users.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import static jakarta.persistence.EnumType.STRING;
import static ru.clevertec.users.utils.constants.MessageConstants.*;

/**
 * User entity for DB
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users")
public class User implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = EMPTY_USERNAME)
    @Size(max = 50, message = TOO_BIG_USERNAME)
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    @NotBlank(message = EMPTY_PASSWORD)
    @Size(min = 8, message = TOO_SMALL_PASSWORD)
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(STRING)
    @NotNull(message = NULL_ROLE)
    private Role role;
}
