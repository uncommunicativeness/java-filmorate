package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    @Test
    @DisplayName("Correct user addition")
    void correctUserAdditionTest() {
        User user = User.builder()
                .email("name@email.com")
                .login("login")
                .name("Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        assertDoesNotThrow(() -> UserController.validate(user),
                "Некорректная валидация валидного пользователя");
    }

    @Test
    @DisplayName("Blank email")
    void incorrectEmailTest() {
        User userWithoutEmail = User.builder()
                .email("")
                .login("login")
                .name("Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        User userWithIncorrectEmail = User.builder()
                .email("email")
                .login("login")
                .name("Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        ValidationException exceptionWithoutEmail = assertThrows(ValidationException.class, () -> UserController.validate(userWithoutEmail));
        ValidationException exceptionWithIncorrectEmail = assertThrows(ValidationException.class, () -> UserController.validate(userWithIncorrectEmail));

        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", exceptionWithoutEmail.getMessage(),
                "Некорректная валидация пользователя с пустым email");
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @", exceptionWithIncorrectEmail.getMessage(),
                "Некорректная валидация пользователя с некорректным email");
    }

    @Test
    @DisplayName("Blank email or email with space")
    void incorrectLoginTest() {
        User userWithoutLogin = User.builder()
                .email("name@email.com")
                .login("")
                .name("Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        User userWithIncorrectLogin = User.builder()
                .email("name@email.com")
                .login("my login")
                .name("Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();

        ValidationException exceptionWithoutEmail = assertThrows(ValidationException.class, () -> UserController.validate(userWithoutLogin));
        ValidationException exceptionWithIncorrectEmail = assertThrows(ValidationException.class, () -> UserController.validate(userWithIncorrectLogin));

        assertEquals("Логин не может быть пустым и содержать пробелы", exceptionWithoutEmail.getMessage(),
                "Некорректная валидация пользователя с пустым логином");
        assertEquals("Логин не может быть пустым и содержать пробелы", exceptionWithIncorrectEmail.getMessage(),
                "Некорректная валидация пользователя с логином, содержащим пробелы");
    }

    @Test
    @DisplayName("Date of birth in the future")
    void futureBirthdayTest() {
        User user = User.builder()
                .email("name@email.com")
                .login("login")
                .name("Name")
                .birthday(LocalDate.now().plusDays(1))
                .build();

        ValidationException exceptionWithoutEmail = assertThrows(ValidationException.class, () -> UserController.validate(user));

        assertEquals("Дата рождения не может быть в будущем", exceptionWithoutEmail.getMessage(),
                "Некорректная валидация пользователя с датой рождения в будущем");
    }
}