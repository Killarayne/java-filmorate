package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.List;

import static ru.yandex.practicum.filmorate.validator.Validator.validateUser;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int genrateID = 0;

    @Override
    public User getUserByID(Integer id) {
        if (users.containsKey(id)) {
            log.debug("Получен пользователь с ID: " + id);
            return users.get(id);
        } else {
            log.warn("Пользователя с ID: " + id + " не существует");
            throw new NotFoundException("Пользователя с ID: " + id + " не существует");
        }
    }

    @Override
    public Map<Integer, User> getUsers() {
        return users;
    }

    @Override
    public List<User> findAll() {
        log.debug("Количество пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) throws ValidationException {

        if (users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь с такой почтой уже существует");
        }
        if (validateUser(user)) {
            user.setId(++genrateID);
            users.put(user.getId(), user);
            log.debug("Добавлен пользователь: {}", user.getEmail());
        } else {
            log.warn("Ошибка валидации пользователя");
            throw new ValidationException("Ошибка валидации пользователя");
        }
        return user;
    }

    @Override
    public User update(User user) throws ValidationException {
        if (validateUser(user) && users.containsKey(user.getId())) {
            users.remove(user);
            users.put(user.getId(), user);
            log.debug("Пользователь " + user.getName() + " обновлен");
        } else {
            log.warn("Ошибка валидации пользователя или такого пользователя не существует");
            throw new NotFoundException("Ошибка валидации пользователя или такого пользователя не существует");
        }
        return user;
    }
}
