package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.ArrayList;
import java.util.List;

import static ru.yandex.practicum.filmorate.validator.Validator.validate;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final List<Film> films = new ArrayList<>();
    private int genrateID = 0;

    @GetMapping
    public List<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films;
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        if (film.getId() == null) {
            film.setId(++genrateID);
        }
        if (validate(film)) {
            films.add(film);
            log.debug("Добавлен фильм: ", film.getDescription());
        } else {
            log.warn("Ошибка валидации фильма");
            throw new ValidationException("Ошибка валидации фильма");
        }
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        if (validate(film) && films.contains(film)) {
            films.set(films.indexOf(film), film);
            log.debug("Фильм " + film.getName() + "обновлен");
        } else {
            log.warn("Ошибка валидации фильма или такого фильма не существует");
            throw new ValidationException("Ошибка валидации фильма или такого фильма не существует");
        }


        return film;
    }
}
