package ru.clevertec.uas.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.uas.models.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    @Modifying
    @Query("delete from User u where u.id = :id")
    int deleteById(long id);

}
