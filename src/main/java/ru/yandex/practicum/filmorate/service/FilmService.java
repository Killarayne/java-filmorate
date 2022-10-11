package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;


import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(Integer filmId, Integer userId) {
        if (filmStorage.getFilms().containsKey(filmId) && userStorage.getUsers().containsKey(userId)) {
            filmStorage.getFilmByID(filmId).setLike(userId);
            log.debug("Добавлен лайк фильму " + filmStorage.getFilmByID(filmId));
        } else {
            log.warn("Неверный ID");
            throw new NotFoundException("Неверный ID");
        }
    }

    public void deleteLike(Integer filmId, Integer userId) {
        if (filmStorage.getFilms().containsKey(filmId) && userStorage.getUsers().containsKey(userId)) {
            filmStorage.getFilmByID(filmId).getLikes().remove(userId);
            log.debug("Удален лайк фильму " + filmStorage.getFilmByID(filmId));
        } else {
            log.warn("Неверный ID");
            throw new NotFoundException("Не верный ID");
        }

    }

    public List<Film> getListPopularFilm(long count) {
        Collection<Film> popularFilms = filmStorage.getFilms().values();
        log.debug("Выведены полпулярные фильмы");
        return popularFilms.stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size(), Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
