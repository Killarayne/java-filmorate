package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exeptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import javax.persistence.EntityNotFoundException;
import java.util.List;
@Component
public class MpaDbStorage  implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAll() {
        String sqlQuery = "SELECT * FROM mpa_rating";

        return jdbcTemplate.query(sqlQuery, new MpaMapper());
    }

    @Override
    public Mpa getById(int id) {
        String sqlQuery = "SELECT * FROM mpa_rating WHERE mpa_id = ?";

        Mpa newMpa = jdbcTemplate.query(sqlQuery, new MpaMapper(), id).stream().findAny().orElse(null);

        if (newMpa != null){
            return newMpa;
        }else {
            throw new  NotFoundException("такого рейтинга не существует");
        }
    }

}
