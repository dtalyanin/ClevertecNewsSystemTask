package ru.clevertec.nms.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.dto.news.*;
import ru.clevertec.nms.models.News;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommentsMapper.class)
public interface NewsMapper {

    NewsDto convertNewsToDto(News news);
    @Mapping(source = "comments", target = "comments")
    NewsWithCommentsDto convertNewsToDtoWithComments(News news, List<CommentDto> comments);
    News convertCreateDtoToNews(CreateNewsDto dto);
    void updateNews(@MappingTarget News news, UpdateNewsDto dto);
    News convertSearchDtoToNews(SearchNewsDto dto);
    List<NewsDto> convertAllNewsToDto(List<News> news);

}
