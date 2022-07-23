package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Repository
public class GenreStorage extends DataStorage<Genre> {
    public GenreStorage(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<Genre> create(Genre data) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement statement = connection.prepareStatement(getSqlCreate(),
                            new String[]{"id"});
                    statement.setString(1, data.getName());
                    return statement;
                }, keyHolder);

        return findById(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public Optional<Genre> update(Genre data) {
        return jdbcTemplate.update(getSqlUpdate(), data.getName(), data.getId()) == 0 ?
                Optional.empty() :
                Optional.of(data);
    }

    @Override
    protected Genre mapRowToObject(ResultSet resultSet, int row) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    @Override
    public String getSqlFindAll() {
        return "select id, name from genres";
    }

    @Override
    public String getSqlFindById() {
        return "select id, name from genres where id = ?";
    }

    @Override
    public String getSqlCreate() {
        return "insert into genres (name) values (?)";
    }

    @Override
    public String getSqlUpdate() {
        return "update genres set name = ? where id = ?";
    }
}
