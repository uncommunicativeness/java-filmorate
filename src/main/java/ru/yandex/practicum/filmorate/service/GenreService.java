package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.AbstractDataStorage;
import ru.yandex.practicum.filmorate.model.Genre;

@Service
public class GenreService extends AbstractDataService<Genre> {
    public GenreService(AbstractDataStorage<Genre> storage) {
        super(storage);
    }
}
