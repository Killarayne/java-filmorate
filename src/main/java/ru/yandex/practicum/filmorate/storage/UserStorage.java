package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {

    Map<Integer, User> getUsers();

    User getUserByID(Integer ID);
    List<User> findAll();
    User create(User user) throws ValidationException;

    User update(User user) throws ValidationException;

}
