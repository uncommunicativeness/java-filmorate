package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

@RestController
@RequestMapping("/films")
public class FilmController extends AbstractController<Film> {
}
