package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.AbstractData;
import ru.yandex.practicum.filmorate.storage.InMemoryAbstractDataStorage;

import java.util.Optional;

@Service
public abstract class AbstractDataService<T extends AbstractData> {
    protected final InMemoryAbstractDataStorage<T> storage;

    @Autowired
    public AbstractDataService(InMemoryAbstractDataStorage<T> storage) {
        this.storage = storage;
    }

    public Iterable<T> findAll() {
        return storage.findAll();
    }

    public Optional<T> findById(int id) {
        return storage.find(id);
    }

    public Optional<T> create(T data) {
        return storage.create(data);
    }

    public Optional<T> update(T data) {
        return storage.update(data);
    }

    public Optional<T> delete(T data) {
        return storage.delete(data);
    }
}
