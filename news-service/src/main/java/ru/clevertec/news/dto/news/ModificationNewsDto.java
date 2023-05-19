package ru.clevertec.news.dto.news;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for performing operation with creating or updating news
 */
@Data
@Schema(description = "DTO for performing operation with creating or updating news")
public class ModificationNewsDto {
    private String title;
    private String text;
}
