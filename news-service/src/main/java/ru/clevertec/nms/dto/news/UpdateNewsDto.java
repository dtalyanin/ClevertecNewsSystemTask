package ru.clevertec.nms.dto.news;

import lombok.Data;

@Data
public class UpdateNewsDto {
    private String title;
    private String text;
    private String username;
}
