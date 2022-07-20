package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.AbstractData;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Slf4j
@Component
public abstract class InMemoryAbstractDataStorage<T extends AbstractData> implements AbstractDataStorage<T> {
    protected int nextId = 0;
    protected Map<Integer, T> storage = new TreeMap<>();

    protected int getNextId() {
        return ++nextId;
    }

    @Override
    public Collection<T> findAll() {
        return storage.values();
    }

    @Override
    public Optional<T> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<T> update(T data) {
        if (!storage.containsKey(data.getId())) {
            return Optional.empty();
        }
        storage.put(data.getId(), data);

        return Optional.of(data);
    }

    @Override
    public Optional<T> create(T data) {
        data.setId(getNextId());
        storage.put(data.getId(), data);

        return Optional.of(data);
    }
}
