package ru.clevertec.uas.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static jakarta.persistence.EnumType.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "users")
public class User implements BaseEntity <Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank(message = "Username must contain username")
    @Max(value = 50, message = "Max length of username is 200 characters")
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(STRING)
    private Role role;
}
