package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.AbstractData;
import ru.yandex.practicum.filmorate.storage.AbstractDataStorage;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class DataStorage<T extends AbstractData> implements AbstractDataStorage<T> {
    protected final JdbcTemplate jdbcTemplate;

    protected String SQL_FIND_ALL;
    protected String SQL_FIND_BY_ID;
    protected String SQL_CREATE;
    protected String SQL_UPDATE;
    protected String SQL_DELETE;

    public DataStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<T> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, this::mapRowToObject);
    }

    @Override
    public Optional<T> findById(int id) {
        List<T> results = jdbcTemplate.query(SQL_FIND_BY_ID, this::mapRowToObject, id);
        return results.size() == 0 ?
                Optional.empty() :
                Optional.of(results.get(0));
    }

    @Override
    public abstract Optional<T> create(T data);

    @Override
    public abstract Optional<T> update(T data);

    protected T mapRowToObject(ResultSet resultSet, int row) {
        return null;
    }
}
