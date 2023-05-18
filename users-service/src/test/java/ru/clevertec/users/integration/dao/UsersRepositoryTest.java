package ru.clevertec.users.integration.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.users.dao.UsersRepository;
import ru.clevertec.users.integration.BaseIntegrationTest;
import ru.clevertec.users.models.User;

import java.util.Optional;

import static generators.factories.UserFactory.getSubscriber;
import static org.assertj.core.api.Assertions.assertThat;

class UsersRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private UsersRepository repository;

    @Test
    void checkFindByUsernameShouldReturnExistingUser() {
        Optional<User> actualUser = repository.findByUsername("User");
        User expectedUser = getSubscriber();

        assertThat(actualUser)
                .isPresent()
                .hasValue(expectedUser);
    }

    @Test
    void checkFindByUsernameShouldReturnEmptyOptional() {
        Optional<User> actualUser = repository.findByUsername("UserNotExist");

        assertThat(actualUser).isEmpty();
    }

    @Test
    void checkExistsByUsernameShouldReturnTrue() {
        boolean userWithUsernameExist = repository.existsByUsername("User");
        assertThat(userWithUsernameExist).isTrue();
    }

    @Test
    void checkExistsByUsernameShouldReturnFalse() {
        boolean userWithUsernameExist = repository.existsByUsername("UserNotExist");
        assertThat(userWithUsernameExist).isFalse();
    }

    @Test
    void checkDeleteByIdShouldReturn1DeletedRow() {
        int actualDeletedRows = repository.deleteById(5L);
        int expectedDeletedRows = 1;

        assertThat(actualDeletedRows).isEqualTo(expectedDeletedRows);
    }

    @Test
    void checkDeleteByIdShouldReturn0DeletedRow() {
        int actualDeletedRows = repository.deleteById(100L);
        int expectedDeletedRows = 0;

        assertThat(actualDeletedRows).isEqualTo(expectedDeletedRows);
    }
}