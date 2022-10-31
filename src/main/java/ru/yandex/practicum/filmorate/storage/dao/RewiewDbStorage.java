package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.RewiewStorage;

@Component
public class RewiewDbStorage implements RewiewStorage {
    JdbcTemplate jdbcTemplate;

    public RewiewDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("INSERT INTO film_likes (film_id, user_id) values (?, ?)", filmId, userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        jdbcTemplate.update("DELETE  FROM film_likes WHERE film_id = ? AND user_id = ?", filmId, userId);
    }
}
