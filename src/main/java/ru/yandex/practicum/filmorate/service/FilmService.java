package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryAbstractDataStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService extends AbstractDataService<Film> {
    public FilmService(InMemoryAbstractDataStorage<Film> storage) {
        super(storage);
    }

    public void addLike(Film film, User user) {
        film.addLike(user);
    }

    public void removeLike(Film film, User user) {
        film.removeLike(user);
    }

    public List<Film> getPopularFilms(int count) {
        return new ArrayList<>(storage.findAll()).stream()
                .sorted(Comparator.comparing(Film::getLikesCount, Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
