package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;


import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper implements RowMapper<Film> {
    private final String film_id = "film_id";
    private final String name = "name";
    private final String description = "description";
    private final String releaseDate = "releaseDate";
    private final String duration = "duration";
    private final String mpa_id = "mpa_id";
    private final String mpa_name = "mpa_name";

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Film(rs.getInt(film_id),
                rs.getString(name),
                rs.getString(description),
                rs.getDate(releaseDate).toLocalDate(),
                rs.getInt(duration),
                new Mpa(rs.getInt(mpa_id), rs.getString(mpa_name)));


    }
}
