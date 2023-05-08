package ru.clevertec.nms.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.nms.models.Comment;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByNewsId(long id, Pageable pageable);
    Optional<Comment> findByIdAndNewsId(long commentId, long newsId);
}
