package ru.yandex.practicum.filmorate.mapper;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper implements RowMapper<Review> {

    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Review(
                rs.getInt("film_id"),
                rs.getInt("user_id")
        );
    }
}
