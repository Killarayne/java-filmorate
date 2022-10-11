package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public void addFriend(Integer userId, Integer friendId) {
        if (userStorage.getUsers().containsKey(userId) && userStorage.getUsers().containsKey(friendId)) {
            userStorage.getUserByID(userId).setFriend(friendId);
            userStorage.getUserByID(friendId).setFriend(userId);
            log.debug("Пользователь с ID " + userId + " начал дружить с пользователем с ID " + friendId);
        } else {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public Set<User> getFriendsByID(Integer id) {
        Set<User> freiendsSet = new HashSet<>();
        if (userStorage.getUsers().containsKey(id)) {
            for (Integer i : userStorage.getUserByID(id).getFriends()) {
                freiendsSet.add(userStorage.getUserByID(i));
            }
            log.debug("Получены друзья у пользователя с ID " + id);
            return freiendsSet;
        } else {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }

    }


    public void deleteFriend(Integer userId, Integer friendId) {
        if (userStorage.getUsers().containsKey(userId) && userStorage.getUsers().containsKey(friendId)) {
            userStorage.getUserByID(userId).getFriends().remove(userStorage.getUserByID(friendId));
            log.debug("Пользователь с ID " + userId + " перестал дружить с пользователем с ID " + friendId);
        } else {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public Set<User> getCommonFriends(Integer id, Integer anotherId) {
        Set<User> commonFriendsSet = new HashSet<>();
        if (userStorage.getUsers().containsKey(id) && userStorage.getUsers().containsKey(anotherId)) {
            Set<Integer> setOfID = new HashSet<>(userStorage.getUserByID(id).getFriends());
            setOfID.retainAll(userStorage.getUserByID(anotherId).getFriends());
            for (Integer i : setOfID) {
                commonFriendsSet.add(userStorage.getUserByID(i));
            }
            log.debug("Предоставлены общие друзья");
            return commonFriendsSet;
        } else {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }


    }
}
