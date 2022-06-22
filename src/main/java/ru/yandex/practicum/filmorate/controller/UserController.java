package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private static int nextId = 0;
    Map<Integer, User> users = new HashMap<>();

    private static int getNextId() {
        return ++nextId;
    }

    static void validate(User user) {
        final Pattern pattern =
                Pattern.compile("^[A-Z\\d._%+-]+@[A-Z\\d.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        if (!pattern.matcher(user.getEmail()).find()) {
            RuntimeException e = new ValidationException("Электронная почта не может быть пустой и должна содержать символ @");
            log.error(e.getMessage());
            throw e;
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            RuntimeException e = new ValidationException("Логин не может быть пустым и содержать пробелы");
            log.error(e.getMessage());
            throw e;
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            RuntimeException e = new ValidationException("Дата рождения не может быть в будущем");
            log.error(e.getMessage());
            throw e;
        }

        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    @GetMapping
    public Iterable<User> getAllUser() {
        return users.values();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        validate(user);
        user.setId(getNextId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            RuntimeException e = new ValidationException("Отсутствует пользователь с переданным ID");
            log.error(e.getMessage());
            throw e;
        }
        validate(user);
        users.put(user.getId(), user);
        return user;
    }
}
