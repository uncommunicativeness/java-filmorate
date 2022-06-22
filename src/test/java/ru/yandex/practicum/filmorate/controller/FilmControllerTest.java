package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class FilmControllerTest {

    @Test
    @DisplayName("Correct film addition")
    void correctFilmAdditionTest() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.now())
                .duration(100)
                .build();
        assertDoesNotThrow(() -> FilmController.validate(film),
                "Некорректная валидация валидного фильма");
    }

    @Test
    @DisplayName("Blank film name")
    void blankFilmNameTest() {
        Film film = Film.builder()
                .name(" ")
                .description("Description")
                .releaseDate(LocalDate.now())
                .duration(100)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () -> FilmController.validate(film));
        assertEquals("Название фильма не может быть пустым", exception.getMessage(),
                "Некорректная валидация фильма с пустым названием");
    }

    @Test
    @DisplayName("Description length is more than 200 characters")
    void longDescriptionTest() {
        Film film = Film.builder()
                .name("Name")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                        "Fusce commodo odio ac tellus dapibus laoreet. Praesent dapibus non purus quis rhoncus." +
                        "Morbi eu lacinia augue, a rhoncus libero. Sed et blandit mi." +
                        "Aliquam tortor nisl, euismod a scelerisque nec, mattis nec nulla." +
                        "Proin ac tellus sit amet leo porta auctor. Aenean malesuada auctor tincidunt.")
                .releaseDate(LocalDate.now())
                .duration(100)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> FilmController.validate(film));
        assertEquals("Максимальная длина описания — 200 символов", exception.getMessage(),
                "Некорректная валидация фильма с описанием длиной более 200 символов");

    }

    @Test
    @DisplayName("Early release of the film")
    void earlyReleaseTest() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.of(1895, 12, 27))
                .duration(100)
                .build();

        ValidationException exception = assertThrows(ValidationException.class, () -> FilmController.validate(film));
        assertEquals("Дата релиза фильма — не раньше 28 декабря 1895 года", exception.getMessage(),
                "Некорректная валидация фильма с датой релиза более ранней, чем 28 декабря 1895 года");
    }
    @Test
    @DisplayName("Negative Duration")
    void negativeDurationTest() {
        Film film = Film.builder()
                .name("Name")
                .description("Description")
                .releaseDate(LocalDate.now())
                .duration(-100)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () -> FilmController.validate(film));
        assertEquals("Продолжительность фильма должна быть положительной", exception.getMessage(),
                "Некорректная валидация фильма с отрицательной длительностью");
    }
}