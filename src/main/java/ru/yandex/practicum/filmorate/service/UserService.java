package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserDbStorage;
import ru.yandex.practicum.filmorate.validator.Validator;


@Service
@Slf4j
public class UserService {
    private UserStorage userStorage;
    private UserDbStorage userDbStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, UserDbStorage userDbStorage) {
        this.userStorage = userStorage;
        this.userDbStorage = userDbStorage;
    }

    public User create(User user) throws ValidationException {
        if (Validator.validateUser(user)) {
            log.debug("Создал фильм с id: " + user.getId());
            return userStorage.create(user);
        } else {
            log.warn("Ошибка валидации пользователя");
            throw new ValidationException("Ошибка валидации пользователя");
        }
    }


    public Boolean addFriend(Integer userId, Integer friendId) {
        User user = userStorage.getUserByID(userId);
        User friend = userStorage.getUserByID(friendId);
        if (user != null && friend != null){
            return userDbStorage.addFriend(userId,friendId);
        }else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public User getUserByID(Integer id) {
        User newUser = userStorage.getUserByID(id);
        if (newUser != null) {
            log.debug("Получен пользователь с id " + id);
            return newUser;
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }

    public User update(User user) throws ValidationException {
        if (Validator.validateUser(user)) {
                userStorage.update(user);
                log.debug("Обновлен пользователь с id " + user.getId());
                return user;
            } else {
                log.warn("Такого пользователя не существует");
                throw new NotFoundException("Такого пользователя не существует");
            }

        }
    }



