package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;


import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<User> {
    private final String user_id = "user_id";
    private final String email = "email";
    private final String login = "login";
    private final String name = "name";
    private final String birthday = "birthday";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getInt(user_id),rs.getString(email),
                rs.getString(login),
                rs.getString(name),
                rs.getString(birthday));
    }
}
