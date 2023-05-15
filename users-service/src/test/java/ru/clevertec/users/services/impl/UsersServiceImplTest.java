package ru.clevertec.users.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import ru.clevertec.users.dao.UsersRepository;
import ru.clevertec.users.dto.UserDto;
import ru.clevertec.users.security.services.JwtService;

import java.util.List;

import static generators.factories.PageFactory.getUsersPage;
import static generators.factories.PageableFactory.getDefaultPageable;
import static generators.factories.UserDtoFactory.getAllUserDtos;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class UsersServiceImplTest {

    @Autowired
    private UsersServiceImpl service;
    @MockBean
    private UsersRepository repository;
    @MockBean
    private JwtService jwtService;

    @Test
    void getAllUsersWithPaginationShouldReturn5Users() {
        doReturn(getUsersPage())
                .when(repository).findAll(any(Pageable.class));

        List<UserDto> actualUsers = service.getAllUsersWithPagination(getDefaultPageable());
        List<UserDto> expectedUsers = getAllUserDtos();

        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    void getUserById() {
    }

    @Test
    void getUserByToken() {
    }

    @Test
    void addUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserById() {
    }
}