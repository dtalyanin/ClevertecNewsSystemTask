package ru.clevertec.nms.dto.news;


import lombok.Data;

@Data
public class CreateNewsDto {
    private String title;
    private String text;
}
