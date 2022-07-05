package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.AbstractDataService;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends AbstractController<Film> {

    private final FilmService filmService;
    private final UserService userService;

    @Autowired
    public FilmController(AbstractDataService<Film> dataService, FilmService filmService, UserService userService) {
        super(dataService);
        this.filmService = filmService;
        this.userService = userService;
    }

    @PutMapping("/{id}/like/{userId}")
    public ResponseEntity<Set<User>> addLike(@PathVariable int id, @PathVariable int userId) {
        Optional<Film> optionalFilm = filmService.findById(id);
        Optional<User> optionalUser = userService.findById(userId);

        if (optionalFilm.isEmpty()) {
            throw new NotFoundException(String.format("Фильм с id=%s не найден", id));
        } else if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", userId));
        }

        log.info("Фильм с id={} и пользователь id={} успешно найдены, добавляем лайк", id, userId);
        filmService.addLike(optionalFilm.get(), optionalUser.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}/like/{userId}")
    public ResponseEntity<Set<User>> removeLike(@PathVariable int id, @PathVariable int userId) {
        Optional<Film> optionalFilm = filmService.findById(id);
        Optional<User> optionalUser = userService.findById(userId);

        if (optionalFilm.isEmpty()) {
            throw new NotFoundException(String.format("Фильм с id=%s не найден", id));
        } else if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", userId));
        }

        log.info("Фильм с id={} и пользователь id={} успешно найдены, удаляем лайк", id, userId);
        filmService.removeLike(optionalFilm.get(), optionalUser.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Film>> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return new ResponseEntity<>(filmService.getPopularFilms(count), HttpStatus.OK);
    }
}
