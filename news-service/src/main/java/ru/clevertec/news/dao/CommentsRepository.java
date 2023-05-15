package ru.clevertec.news.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.clevertec.news.models.Comment;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByNewsId(long id, Pageable pageable);
    @Modifying
    @Query("delete from Comment c where c.news.id = :id")
    void deleteByNewsId(long id);
}
