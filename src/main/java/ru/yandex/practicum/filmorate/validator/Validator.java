package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {
    public static Boolean validate(Object object) {
        LocalDate localDate = LocalDate.parse("1895-12-28");
        if (object instanceof Film) {
            Film film = (Film) object;
            if (film.getName() != null &&!film.getName().isBlank() && film.getDescription().length() < 200 && film.getReleaseDate().isAfter(localDate) &&
                    film.getDuration()> 0) {
                return true;
            }

        } else if (object instanceof User) {
            User user = (User) object;
            if (!user.getEmail().isBlank() && user.getEmail().contains("@") && !user.getLogin().isBlank() &&
                    !user.getLogin().contains(" ") && user.getBirthday().isBefore(LocalDate.now())) {
                if (user.getName() == null || user.getName().isBlank()) {
                    user.setName(user.getLogin());
                }
                return true;

            }
        }
        return false;
    }
}
