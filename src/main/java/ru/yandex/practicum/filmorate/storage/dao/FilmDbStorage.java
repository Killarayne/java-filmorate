package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class FilmDbStorage implements FilmStorage {

    JdbcTemplate jdbcTemplate;
    GenreStorage genreStorage;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreStorage = genreStorage;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return null;
    }

    @Override
    public Film getFilmByID(Integer filmId) throws DataAccessException {
        String sql = "SELECT film_id, name, description, releaseDate, duration, m.mpa_id, m.mpa_name " +
                "FROM films LEFT JOIN mpa_rating AS m ON films.mpa_id = m.mpa_id " +
                "WHERE film_id = ?";
        Film newFilm = jdbcTemplate.queryForObject(sql, new FilmMapper(), filmId);
        for (Genre g : genreStorage.getFilmGenres(filmId)) {
            newFilm.setGenre(genreStorage.getFilmGenres(filmId));
        }
        return newFilm;

    }

    private Film updateGenres(Film film) {

        String deleteGenresSql = "DELETE FROM film_genres WHERE film_id = ?";
        jdbcTemplate.update(deleteGenresSql, film.getId());
        String sql = "INSERT INTO film_genres (GENRE_ID, FILM_ID) VALUES (?, ?)";
        List<Integer> genres = film.getGenres().stream().map(Genre::getId).distinct().collect(Collectors.toList());
        film.getGenres().clear();
        for (Integer genreId : genres) {
            jdbcTemplate.update(sql, genreId, film.getId());
        }
        film.setGenre(genreStorage.getFilmGenres(film.getId()));

        return film;
    }


    @Override
    public List<Film> findAll() {
        String sql = "SELECT film_id, name, description, releaseDate, duration, m.mpa_id, m.mpa_name " +
                "FROM films LEFT JOIN mpa_rating AS m ON films.mpa_id = m.mpa_id ";
        List<Film> newFilmsList = jdbcTemplate.query(sql, new FilmMapper());
        for (Film film : newFilmsList) {
            film.setGenre(genreStorage.getFilmGenres(film.getId()));
        }
        return newFilmsList;
    }

    @Override
    public Film create(Film film) throws ValidationException {
        SimpleJdbcInsert insertFilm = new SimpleJdbcInsert(jdbcTemplate);
        insertFilm.withTableName("films").usingGeneratedKeyColumns("film_id");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("releaseDate", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("mpa_id", film.getMpa().getId());
        Number newId = insertFilm.executeAndReturnKey(params);
        film.setId(newId.intValue());
        updateGenres(film);
        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException {
        String sql = "UPDATE films SET name = ?, description = ?, releaseDate = ?, Duration = ?, mpa_id = ? " +
                "where film_id = ? ";
        Integer status = jdbcTemplate.update(sql, film.getName(),
                film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId(), film.getId());
        updateGenres(film);
        if (status != 0) {
            return film;
        } else {
            throw new NotFoundException("Такого фильма не существует");
        }


    }


    public List<Film> getPopular(Integer count) {
        String sql = "SELECT f.film_id, f.name, f.description, f.releaseDate, f.duration, m.mpa_id, m.mpa_name " +
                "FROM films AS f " +
                "LEFT JOIN mpa_rating AS m ON f.mpa_id = m.mpa_id " +
                "LEFT JOIN film_likes AS ll ON f.film_id=ll.film_id " +
                "GROUP BY (f.name,ll.film_id) " +
                "ORDER BY COUNT(ll.user_id) desc " +
                "LIMIT ?";
        return jdbcTemplate.query(sql, new FilmMapper(), count);


    }


}
