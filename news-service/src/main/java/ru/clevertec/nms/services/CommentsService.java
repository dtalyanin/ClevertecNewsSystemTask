package ru.clevertec.nms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.nms.dao.CommentsRepository;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.utils.PageableHelper;

import java.util.List;

import static ru.clevertec.nms.utils.PageableHelper.*;


@Service
public class CommentsService {

    private final CommentsRepository repository;

    @Autowired
    public CommentsService(CommentsRepository repository) {
        this.repository = repository;
    }

    public List<Comment> getCommentsByNewsIdWithPagination(long newsId, Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        return repository.findAllByNewsId(newsId, pageable);
    }
}
