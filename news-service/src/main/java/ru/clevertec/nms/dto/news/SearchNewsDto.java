package ru.clevertec.nms.dto.news;

import lombok.Data;

@Data
public class SearchNewsDto {
    private String title;
    private String text;
    private String userName;
}
