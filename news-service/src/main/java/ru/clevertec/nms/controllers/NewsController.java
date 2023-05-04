package ru.clevertec.nms.controllers;

import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.clevertec.nms.dto.news.NewsDto;
import ru.clevertec.nms.dto.news.SearchNewsDto;
import ru.clevertec.nms.models.responses.ModificationResponse;
import ru.clevertec.nms.security.UserDetailsDecorator;
import ru.clevertec.nms.services.NewsService;
import ru.clevertec.nms.clients.services.UsersService;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<ModificationResponse> addNews(@RequestBody NewsDto dto) {
        ModificationResponse response = service.addNews(dto, getUserDetails());
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ModificationResponse> updateNews(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id,
            @RequestBody NewsDto dto) {
        return ResponseEntity.ok(service.updateNews(id, dto, getUserDetails()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ModificationResponse> deleteNewsById(
            @PathVariable @Min(value = 1, message = MIN_ID_MESSAGE) long id) {
        return ResponseEntity.ok(service.deleteNewsById(id, getUserDetails()));
    }

    private UserDetailsDecorator getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (UserDetailsDecorator) authentication.getPrincipal();
    }
}
