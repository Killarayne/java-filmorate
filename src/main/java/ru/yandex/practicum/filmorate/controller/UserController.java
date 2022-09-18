package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import java.util.HashSet;

import static ru.yandex.practicum.filmorate.validator.Validator.validate;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final HashSet<User> users = new HashSet<>();
    private int genrateID = 0;

    @GetMapping
    public HashSet<User> findAll() {
        log.debug("Количество пользователей: {}", users.size());
        return users;
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {

        if (user.getId() == null) {
            user.setId(++genrateID);
        }

        if (validate(user)) {
            users.add(user);
            log.debug("Добавлен пользователь: {}", user.getEmail());
        } else {
            log.warn("Ошибка валидации пользователя");
            throw new ValidationException("Ошибка валидации пользователя");
        }
        return user;
    }


    @PutMapping
    public User update(@RequestBody User user) throws ValidationException {
        if (validate(user) && users.contains(user)) {
            users.remove(user);
            users.add(user);
            log.debug("Пользователь " + user.getName() + " обновлен");
        } else {
            log.warn("Ошибка валидации пользователя или такого пользователя не существует");
            throw new ValidationException("Ошибка валидации пользователя или такого пользователя не существует");
        }
        return user;

    }
}

