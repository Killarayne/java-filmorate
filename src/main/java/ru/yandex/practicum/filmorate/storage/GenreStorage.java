package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

public interface GenreStorage {
    List<Genre> getAll();

    Genre getById(Integer id);

    Set<Genre> getFilmGenres(int filmId);
}


