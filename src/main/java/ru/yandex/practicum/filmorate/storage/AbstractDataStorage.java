package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.AbstractData;

import java.util.Optional;

public interface AbstractDataStorage<T extends AbstractData> {
    Iterable<T> findAll();

    Optional<T> find(int id);

    Optional<T> create(T data);

    Optional<T> update(T data);

    Optional<T> delete(T data);
}
