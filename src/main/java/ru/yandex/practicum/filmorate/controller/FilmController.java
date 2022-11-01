package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.RewiewStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.dao.FilmDbStorage;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private FilmStorage filmStorage;
    private FilmDbStorage filmDbStorage;
    private FilmService filmService;
    private RewiewStorage rewiewStorage;
    private UserService userService;

    @Autowired
    public FilmController(@Qualifier("filmDbStorage") FilmStorage filmStorage, FilmDbStorage filmDbStorage,
                          RewiewStorage rewiewStorage,UserService userService,FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmDbStorage = filmDbStorage;
        this.rewiewStorage = rewiewStorage;
        this.userService = userService;
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        return filmService.create(film);
    }

    @PutMapping
    public Film update(@RequestBody Film film) throws ValidationException {
        return filmStorage.update(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Integer id)throws DataAccessException {
        return filmService.getFilmByID(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return filmDbStorage.getPopular(count);
    }

    @PutMapping("/{id}/like/{user_id}")
    public void addLike(@PathVariable Integer id, @PathVariable Integer user_id) {
        rewiewStorage.addLike(id,user_id);
    }

    @DeleteMapping("/{id}/like/{user_id}")
    public void deleteLike(@PathVariable Integer id, @PathVariable Integer user_id) {
        userService.getUserByID(user_id);
        rewiewStorage.deleteLike(id,user_id);
    }
}
