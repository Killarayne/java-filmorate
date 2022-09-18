package ru.yandex.practicum.filmorate.model;


import lombok.Data;


import java.time.LocalDate;
import java.util.Objects;

@Data
public class User {
    private Integer id;

    private String email;


    private String login;
    private String name;

    private LocalDate birthday;


    public User(String email, String login, String name, String birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = LocalDate.parse(birthday);

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
