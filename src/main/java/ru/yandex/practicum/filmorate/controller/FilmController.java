package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private static int nextId = 0;
    Map<Integer, Film> films = new HashMap<>();

    private static int getNextId() {
        return ++nextId;
    }

    public static void validate(Film film) {
        final int MAX_LENGTH_DESCRIPTION = 200;
        final LocalDate EARLY_DATE = LocalDate.of(1895, 12, 28);

        if (film.getName().isBlank()) {
            RuntimeException e = new ValidationException("Название фильма не может быть пустым");
            log.error(e.getMessage());
            throw e;
        } else if (film.getDescription().length() > MAX_LENGTH_DESCRIPTION) {
            RuntimeException e = new ValidationException("Максимальная длина описания — 200 символов");
            log.error(e.getMessage());
            throw e;
        } else if (film.getReleaseDate().isBefore(EARLY_DATE)) {
            RuntimeException e = new ValidationException("Дата релиза фильма — не раньше 28 декабря 1895 года");
            log.error(e.getMessage());
            throw e;
        } else if (film.getDuration() <= 0) {
            RuntimeException e = new ValidationException("Продолжительность фильма должна быть положительной");
            log.error(e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public Iterable<Film> getAllFilms() {
        return films.values();
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        validate(film);
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            RuntimeException e = new ValidationException("Отсутствует фильм с переданным ID");
            log.error(e.getMessage());
            throw e;
        }
        validate(film);

        films.put(film.getId(), film);
        return film;
    }

}
