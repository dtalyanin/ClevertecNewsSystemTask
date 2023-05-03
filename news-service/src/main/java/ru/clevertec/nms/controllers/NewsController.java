package ru.clevertec.nms.controllers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.dto.news.SearchNewsDto;
import ru.clevertec.nms.security.UserDetailsDecorator;
import ru.clevertec.nms.services.NewsService;
import ru.clevertec.nms.clients.services.UsersService;

import java.util.List;

import static ru.clevertec.nms.utils.constants.MessageConstants.MIN_ID_MESSAGE;

@RestController
@RequestMapping("/news")
@Validated
public class NewsController {

    private final NewsService service;
    private final UsersService usersService;

    @Autowired
    public NewsController(NewsService service, UsersService usersService) {
        this.service = service;
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNewsWithPagination(Pageable pageable) {
        System.out.println(usersService.getUserByUsername("gloria"));
        System.out.println(pageable);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsDecorator personDetails = (UserDetailsDecorator) authentication.getPrincipal();
        System.out.println(personDetails);
        return ResponseEntity.ok(service.getAllNewsWithPagination(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<NewsDto>> getAllSearchedNewsWithPagination(SearchNewsDto dto, Pageable pageable) {
        return ResponseEntity.ok(service.getAllSearchedNewsWithPagination(dto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsByIdWithPagination(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id, Pageable pageable) {
        return ResponseEntity.ok(service.getNewsWithCommentsPagination(id, pageable));
    }
}