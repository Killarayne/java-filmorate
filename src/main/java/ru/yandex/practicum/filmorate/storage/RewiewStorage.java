package ru.yandex.practicum.filmorate.storage;

public interface RewiewStorage {

    void addLike(Integer filmId, Integer userId);

    public void deleteLike(Integer filmId, Integer userId);
}
