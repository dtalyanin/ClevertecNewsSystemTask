package ru.clevertec.nms.utils.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.nms.dto.CommentDto;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.dto.news.NewsWithCommentsDto;
import ru.clevertec.nms.dto.news.SearchNewsDto;
import ru.clevertec.nms.models.Comment;
import ru.clevertec.nms.models.News;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommentsMapper.class)
public interface NewsMapper {

    NewsDto convertNewsToDto(News news);
    @Mapping(source = "comments", target = "comments")
    NewsWithCommentsDto convertNewsToDtoWithComments(News news, List<CommentDto> comments);
    News convertSearchDtoToNews(SearchNewsDto dto);
    @IterableMapping(elementTargetType = NewsDto.class)
    List<NewsDto> convertAllNewsToDto(List<News> news);

    News convertDtoToNews(NewsDto dto);

    void updateNews(@MappingTarget News news, NewsDto dto);
}
