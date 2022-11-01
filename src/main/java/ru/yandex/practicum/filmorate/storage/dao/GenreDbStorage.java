package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;


    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "SELECT genre_id, name " +
                "FROM genres " +
                "ORDER BY genre_id ASC ";

        return jdbcTemplate.query(sqlQuery, new GenreMapper());
    }


    public Genre getById(Integer id) throws DataAccessException {
        String sqlQuery = "SELECT g.genre_id, " +
                "g.name " +
                "FROM genres AS g " +
                "WHERE g.genre_id = ?;";

        return jdbcTemplate.queryForObject(sqlQuery, new GenreMapper(), id);
    }

    public Set<Genre> getFilmGenres(int filmId) {
        String sql = "Select g.genre_id, g.name from genres as g " +
                "join film_genres as fg on g.genre_id = fg.genre_id " +
                "where film_id = ?" +
                "ORDER BY g.genre_id ASC";
        Set<Genre> targetSet = new HashSet<Genre>(jdbcTemplate.query(sql, new GenreMapper(), filmId));
        return targetSet;
    }


}
