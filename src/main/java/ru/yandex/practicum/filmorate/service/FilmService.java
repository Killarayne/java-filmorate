package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validator.Validator;

import java.sql.SQLException;


@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;


    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film getFilmByID(Integer filmId) throws DataAccessException {
        Film newFilm = filmStorage.getFilmByID(filmId);
        if (newFilm != null) {
            log.debug("Получен фильм с id: " + filmId);
            return newFilm;
        } else {
            log.warn("Фильма с id: " + filmId + " не существует");
            throw new NotFoundException("Фильма с id: " + filmId + " не существует");
        }
    }

    public Film create(Film film) throws ValidationException {
        if (Validator.validateFilm(film)) {
            log.debug("Получен фильм с id: " + film.getId());
            return filmStorage.create(film);
        } else {
            log.warn("Ошибка валидации фильма");
            throw new ValidationException("Ошибка валидации фильма");
        }

    }

    public Film update(Film film) throws ValidationException {
        if (Validator.validateFilm(film)) {
            log.debug("Получен фильм с id: " + film.getId());
            return film;
        } else {
            log.warn("Ошибка валидации фильма");
            throw new ValidationException("Ошибка валидации фильма");
        }
    }


}
