package ru.clevertec.news.utils.mappers;

import generators.builders.NewsDtoTestBuilder;
import generators.factories.news.NewsDtoFactory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.clevertec.news.dto.news.NewsDto;
import ru.clevertec.news.dto.news.NewsWithCommentsDto;
import ru.clevertec.news.models.News;

import java.util.List;

import static generators.factories.comments.CommentDtoFactory.getFirst2CommentsDto;
import static generators.factories.news.ModificationNewsDtoFactory.getModificationDto;
import static generators.factories.news.NewsDtoFactory.getNewsDto1;
import static generators.factories.news.NewsDtoFactory.getNewsDto1With2Comments;
import static generators.factories.news.NewsFactory.*;
import static org.assertj.core.api.Assertions.assertThat;

class NewsMapperTest {

    private final NewsMapper mapper = Mappers.getMapper(NewsMapper.class);

    @Test
    void convertNewsToDto() {
        NewsDto actualDto = mapper.convertNewsToDto(getNews1());
        NewsDto expectedDto = getNewsDto1();

        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    void convertNewsToDtoWithComments() {
        NewsWithCommentsDto actualDto = mapper.convertNewsToDtoWithComments(getNews1(), getFirst2CommentsDto());
        NewsWithCommentsDto expectedDto = getNewsDto1With2Comments();

        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    void convertModificationDtoToNews() {
        News actualNews = mapper.convertModificationDtoToNews(getModificationDto(), "User");
        News expectedNews = getCreatedNews();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void updateNews() {
        News actualNews = mapper.updateNews(getNews1(), getModificationDto());
        News expectedNews = getUpdatedNews();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void convertDtoToNews() {
        News actualNews = mapper.convertDtoToNews(NewsDtoTestBuilder.builder()
                .withId(null)
                .withUsername(null)
                .build());
        News expectedNews = getNewsWithoutId();

        assertThat(actualNews).isEqualTo(expectedNews);
    }

    @Test
    void convertAllNewsToDto() {
        List<NewsDto> actualDtos = mapper.convertAllNewsToDto(getAllNews());
        List<NewsDto> expectedDtos = NewsDtoFactory.getFirst2NewsDtos();

        assertThat(actualDtos).isEqualTo(expectedDtos);
    }
}