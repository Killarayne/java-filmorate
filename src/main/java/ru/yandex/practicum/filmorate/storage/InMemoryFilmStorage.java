package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.validator.Validator.validateFilm;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int genrateID = 0;

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Film getFilmByID(Integer filmId) {

        if (films.containsKey(filmId)) {
            log.debug("Получен фильм с ID: " + filmId);
            return films.get(filmId);
        } else {
            log.warn("Фильма с " + filmId + " не существует ");
            throw new NotFoundException("Фильма с " + filmId + " не существует ");
        }
    }

    @Override
    public List<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) throws ValidationException {
        if (validateFilm(film)) {
            film.setId(++genrateID);
            films.put(film.getId(), film);
            log.debug("Добавлен фильм: ", film.getDescription());
        } else {
            log.warn("Ошибка валидации фильма");
            throw new ValidationException("Ошибка валидации фильма");
        }
        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException {
        if (validateFilm(film) && films.containsKey(film.getId())) {
            films.remove(film.getId());
            films.put(film.getId(), film);
            log.debug("Фильм " + film.getName() + "обновлен");
        } else {
            log.warn("Ошибка валидации фильма или такого фильма не существует");
            throw new NotFoundException("Ошибка валидации фильма или такого фильма не существует");
        }


        return film;
    }
}
