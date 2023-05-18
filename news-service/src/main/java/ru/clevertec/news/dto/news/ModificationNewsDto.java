package ru.clevertec.news.dto.news;


import lombok.Data;

/**
 * DTO for performing operation with creating or updating news
 */
@Data
public class ModificationNewsDto {
    private String title;
    private String text;
}
