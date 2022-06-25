package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;

@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User> {
    @Override
    public void validate(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
