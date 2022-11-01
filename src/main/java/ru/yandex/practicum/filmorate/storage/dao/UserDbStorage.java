package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.SQLException;
import java.util.List;


@Component
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User getUserByID(Integer id) throws DataAccessException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
    }


    public boolean addFriend(Integer id, Integer friendId) {
        String sql = "INSERT INTO user_friends (user_id, friend_id, status) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, id, friendId, true);
        return true;

    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User create(User user) throws ValidationException {
        SimpleJdbcInsert insertFilm = new SimpleJdbcInsert(jdbcTemplate);
        insertFilm.withTableName("users").usingGeneratedKeyColumns("user_id");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName())
                .addValue("login", user.getLogin())
                .addValue("email", user.getEmail())
                .addValue("birthday", user.getBirthday());
        Number newId = insertFilm.executeAndReturnKey(params);
        user.setId(newId.intValue());
        return user;
    }

    @Override
    public User update(User user) throws ValidationException {
        String sql = "UPDATE users SET name = ?, login = ?, email = ?, birthday = ? " +
                "where user_id = ? ";
        Integer status = jdbcTemplate.update(sql, user.getName(),
                user.getLogin(), user.getEmail(), user.getBirthday(), user.getId());
        if (status == 1) {
            return user;
        } else {
            throw new NotFoundException("Такого пользователя не существует");
        }
    }



    public List<User> getFriendsById(int id) {
        String sql = "SELECT * FROM USERS WHERE USER_ID IN (SELECT FRIEND_ID FROM USER_FRIENDS WHERE USER_ID = ?)";
        return jdbcTemplate.query(sql, new UserMapper(), id);
    }


    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        String sql = "SELECT  u.* " +
                "FROM user_friends AS fs " +
                "JOIN users AS u ON fs.friend_id = u.user_id " +
                "WHERE fs.user_id = ? AND fs.friend_id IN (" +
                "SELECT friend_id FROM user_friends WHERE user_id = ?)";

        return jdbcTemplate.query(sql, new UserMapper(), userId, friendId);
    }

    public boolean deleteFriend(Integer userId, Integer friendId) {
        String sqlQuery = "delete from user_friends " +
                "where (user_id = ? and friend_id = ?)" +
                "or (user_id = ? and friend_id = ?)";

        return Boolean.parseBoolean(String.valueOf(jdbcTemplate.update(sqlQuery, userId, friendId, friendId, userId)));
    }
}
