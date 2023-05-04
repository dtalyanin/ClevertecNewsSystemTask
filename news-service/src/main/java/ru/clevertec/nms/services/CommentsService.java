package ru.clevertec.nms.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.nms.dao.CommentsRepository;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.utils.PageableHelper;
import ru.clevertec.nms.utils.mappers.CommentsMapper;

import java.util.List;

import static ru.clevertec.nms.utils.PageableHelper.*;


@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository repository;
    private final CommentsMapper mapper;

    public List<CommentDto> getCommentsByNewsIdWithPagination(long newsId, Pageable pageable) {
        pageable = setPageableUnsorted(pageable);
        return mapper.convertAllCommentsToDtos(repository.findAllByNewsId(newsId, pageable));
    }

    public CommentDto getCommentByIdAndNewsId(long newsId, long userId) {

    }
}
