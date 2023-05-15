package ru.clevertec.news.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.news.ModificationNewsDto;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.models.News;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommentsMapper.class)
public interface NewsMapper {

    NewsDto convertNewsToDto(News news);
    @Mapping(source = "comments", target = "comments")
    NewsWithCommentsDto convertNewsToDtoWithComments(News news, List<CommentDto> comments);
    News convertModificationDtoToNews(ModificationNewsDto dto, String username);
    void updateNews(@MappingTarget News news, ModificationNewsDto dto);
    News convertDtoToNews(NewsDto dto);
    List<NewsDto> convertAllNewsToDto(List<News> news);

}
