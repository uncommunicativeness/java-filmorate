package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerValidateTest {
    private static UserController controller;

    @BeforeAll
    static void beforeAll() {
        controller = new UserController();
    }


    @Test
    @DisplayName("Correct user addition")
    void correctUserAdditionTest() {
        User user = User.builder()
                .email("name@email.com")
                .login("login")
                .name("Name")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
        controller.validate(user);

        assertEquals("Name", user.getName(), "Некоренное поведение при заполненном имени пользователя");
    }

    @Test
    @DisplayName("User without name")
    void userWithoutNameTest() {
        User user = User.builder()
                .email("name@email.com")
                .login("login")
                .name("")
                .birthday(LocalDate.of(1990, 1, 1))
                .build();
        controller.validate(user);

        assertEquals("login", user.getName(), "Некоренное поведение при незаполненном имени пользователя");
    }
}