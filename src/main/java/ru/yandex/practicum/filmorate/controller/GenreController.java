package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.AbstractDataService;
import ru.yandex.practicum.filmorate.service.GenreService;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController extends AbstractController<Genre> {
    public GenreController(AbstractDataService<Genre> dataService, GenreService genreService) {
        super(dataService);
    }
}
