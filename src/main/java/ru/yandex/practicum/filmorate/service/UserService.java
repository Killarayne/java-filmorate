package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public void addFriend(Integer userID, Integer friendID) {
        if (userStorage.getUsers().containsKey(userID) && userStorage.getUsers().containsKey(friendID)) {
            userStorage.getUserByID(userID).setFriend(friendID);
            userStorage.getUserByID(friendID).setFriend(userID);
            log.debug("Пользователь с ID " + userID + " начал дружить с пользователем с ID " + friendID);
        } else {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public Set<User> getFriendByID(Integer ID) {
        Set<User> freiendsSet = new HashSet<>();
        if (userStorage.getUsers().containsKey(ID)) {
            for (Integer i : userStorage.getUserByID(ID).getFriends()) {
                freiendsSet.add(userStorage.getUserByID(i));
            }
            log.debug("Получены друзья у пользователя с ID " + ID);
            return freiendsSet;
        } else {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }

    }


    public void deleteFriend(Integer userID, Integer friendID) {
        if (userStorage.getUsers().containsKey(userID) && userStorage.getUsers().containsKey(friendID)) {
            userStorage.getUserByID(userID).getFriends().remove(userStorage.getUserByID(friendID));
            log.debug("Пользователь с ID " + userID + " перестал дружить с пользователем с ID " + friendID);
        } else {
            log.warn("Пользователь не найден");
            throw new NotFoundException("Пользователь не найден");
        }
    }

    public Set<User> getCommonFriends(Integer ID, Integer anotherID) {

        Set<User> commonFriendsSet = new HashSet<>();
        if (userStorage.getUsers().containsKey(ID) && userStorage.getUsers().containsKey(anotherID)) {
            Set<Integer> setOfID = new HashSet<>(userStorage.getUserByID(ID).getFriends());
            setOfID.retainAll(userStorage.getUserByID(anotherID).getFriends());
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
