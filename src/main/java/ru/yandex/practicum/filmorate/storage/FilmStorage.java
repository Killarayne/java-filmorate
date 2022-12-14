package ru.yandex.practicum.filmorate.storage;

import org.springframework.dao.DataAccessException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface FilmStorage {

    Map<Integer, Film> getFilms();

    Film getFilmByID(Integer filmId) throws DataAccessException;

    List<Film> findAll();

    Film create(Film film) throws ValidationException;

    Film update(Film film) throws ValidationException;


}
