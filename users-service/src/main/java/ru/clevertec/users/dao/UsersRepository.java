package ru.clevertec.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.users.models.User;

import java.util.Optional;

/**
 * Repository for performing operation with users in DB
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    /**
     * Get user with specified username
     * @param username username to search
     * @return user with specified username
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if user with specified username exist in DB
     * @param username username to search
     * @return true if user with specified username exist in DB
     */
    boolean existsByUsername(String username);

    /**
     * Delete user related to specified news ID
     * @param id ID to delete
     */
    @Modifying
    @Query("delete from User u where u.id = :id")
    int deleteById(long id);

}
