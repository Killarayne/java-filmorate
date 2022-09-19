package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

public class Validator {

    static LocalDate startReleaseDate = LocalDate.parse("1895-12-28");

    public static Boolean validateFilm(Film film) {
        return film != null && validateString(film.getName()) && validateDescription(film.getDescription()) &&
                validateDate(film.getReleaseDate(), startReleaseDate) && validateDuration(film.getDuration());

    }

    public static Boolean validateUser(User user) {
        return user != null && validateString(user.getEmail()) && validateEmail(user.getEmail()) && validateString(user.getLogin()) &&
                !user.getLogin().contains(" ") && !validateDate(user.getBirthday(), LocalDate.now());

    }



    public static boolean validateString(String name) {
        return name != null &&!name.isBlank();
    }

    public static boolean validateDescription(String description) {
        return description.length() < 200;
    }

    public static boolean validateDate(LocalDate date, LocalDate dateToCompare) {

        return date.isAfter(dateToCompare);
    }

    public static boolean validateDuration(Integer duration) {
        return duration > 0;
    }
    public static boolean validateEmail(String email) {
        return email.contains("@");
    }

}
