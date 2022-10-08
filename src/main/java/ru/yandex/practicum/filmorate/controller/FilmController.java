package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;


import java.util.ArrayList;
import java.util.List;


import static ru.yandex.practicum.filmorate.validator.Validator.validateFilm;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    FilmStorage filmStorage;
    FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        return filmStorage.update(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Integer id) {
        return filmStorage.getFilmByID(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer userId){
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer userId){
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(value = "count", defaultValue = "10", required = false)Integer count){
       return filmService.getListPopularFilm(count);
    }
}
