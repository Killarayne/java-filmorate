package ru.yandex.practicum.filmorate.model;

import lombok.Data;


import java.time.LocalDate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    private Set<Integer> likes = new HashSet<>();

    public void setLike(Integer userID) {
        likes.add(userID);
    }

    public Set<Integer>  getLikes() {
        return likes;
    }

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
