package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.AbstractDataService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User> {
    private final UserService userService;

    @Autowired
    public UserController(AbstractDataService<User> abstractDataService, UserService userService) {
        super(abstractDataService);
        this.userService = userService;
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<List<User>> getFriends(@PathVariable int id) {
        Optional<User> optional = userService.findById(id);
        if (optional.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", id));
        }

        log.info("Пользователь с id={} успешно найден, получаем его друзей", id);
        return ResponseEntity.ok(userService.getFriends(optional.get()));
    }

    @PutMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Set<User>> addFriend(@PathVariable int id, @PathVariable int friendId) {
        Optional<User> optionalUser = userService.findById(id);
        Optional<User> optionalFriend = userService.findById(friendId);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", id));
        } else if (optionalFriend.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", friendId));
        }

        log.info("Пользователи с id={} и id={} успешно найдены, добавляем в друзья", id, friendId);
        userService.addFriend(optionalUser.get(), optionalFriend.get());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<Set<User>> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        Optional<User> optionalUser = userService.findById(id);
        Optional<User> optionalFriend = userService.findById(otherId);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", id));
        } else if (optionalFriend.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", otherId));
        }

        log.info("Пользователи с id={} и id={} успешно найдены, получаем общих друзей", id, otherId);
        return ResponseEntity.ok(userService.getCommonFriends(optionalUser.get(), optionalFriend.get()));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public ResponseEntity<Set<User>> removeFriend(@PathVariable int id, @PathVariable int friendId) {
        Optional<User> optionalUser = userService.findById(id);
        Optional<User> optionalFriend = userService.findById(friendId);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", id));
        } else if (optionalFriend.isEmpty()) {
            throw new NotFoundException(String.format("Пользователь с id=%s не найден", friendId));
        }

        userService.removeFriend(optionalUser.get(), optionalFriend.get());
        log.info("У пользователи с id={} успешно удалена связь с пользователем с id={}", id, friendId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
