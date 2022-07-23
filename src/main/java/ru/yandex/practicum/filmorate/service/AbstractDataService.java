package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.AbstractDataStorage;
import ru.yandex.practicum.filmorate.model.AbstractData;

import java.util.Collection;
import java.util.Optional;

@Service
public abstract class AbstractDataService<T extends AbstractData> {
    protected final AbstractDataStorage<T> storage;

    @Autowired
    public AbstractDataService(AbstractDataStorage<T> storage) {
        this.storage = storage;
    }

    public Collection<T> findAll() {
        return storage.findAll();
    }

    public Optional<T> findById(int id) {
        return storage.findById(id);
    }

    public Optional<T> create(T data) {
        return storage.create(data);
    }

    public Optional<T> update(T data) {
        return storage.update(data);
    }
}
