package ru.clevertec.news.utils.mappers;

import jakarta.validation.Valid;
import org.mapstruct.*;
import org.springframework.validation.annotation.Validated;
import ru.clevertec.news.dto.comments.CommentDto;
import ru.clevertec.news.dto.news.ModificationNewsDto;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.models.News;

import java.util.List;

/**
 * Mapper for converting news
 */
@Mapper(componentModel = "spring", uses = CommentsMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
@Validated
public interface NewsMapper {

    NewsDto convertNewsToDto(News news);
    @Mapping(source = "comments", target = "comments")
    NewsWithCommentsDto convertNewsToDtoWithComments(News news, List<CommentDto> comments);
    @Valid News convertModificationDtoToNews(ModificationNewsDto dto, String username);
    @Valid News updateNews(@MappingTarget News news, ModificationNewsDto dto);
    News convertDtoToNews(NewsDto dto);
    List<NewsDto> convertAllNewsToDto(List<News> news);

}
