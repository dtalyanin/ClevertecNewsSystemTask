package ru.clevertec.news.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.news.models.Comment;

import java.util.List;

/**
 * Repository for performing operation with comments in DB
 */
@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {

    /**
     * Get all comments that associated with specified news ID
     * @param id news ID to search
     * @param pageable page and maximum size of returning collections
     * @return list of comments that associated with specified news ID
     */
    List<Comment> findAllByNewsId(long id, Pageable pageable);

    /**
     * Delete comment related to specified news ID
     * @param id news ID to delete
     */
    @Modifying
    @Query("delete from Comment c where c.news.id = :id")
    void deleteByNewsId(long id);
}
