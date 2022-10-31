package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.filmorate.validator.Validator.validateFilm;
import static ru.yandex.practicum.filmorate.validator.Validator.validateUser;

import java.time.LocalDate;

public class ValidatorTest {

    Film newFilm;
    User newUser;

    @BeforeEach
    void init() {
        newFilm = new Film(1, "Джеймс Бонд", "Фильм о шпионе", LocalDate.parse("2020-12-12"), 120, new Mpa(1, "G"));
        newUser = new User(1, "Saltykov@yandex.ru", "kasail", "Nikita", "1993-09-29");
    }


    @Test
    void shouldTrueWhenCorrectFilmData() {
        assertTrue(validateFilm(newFilm), "Неверное поведение при корректных данных");

    }

    @Test
    void shouldFalseWhenIncorrectFilmName() {
        newFilm.setName("");
        assertFalse(validateFilm(newFilm), "Неверное поведение при некорректном назавании");
        newFilm.setName(null);
        assertFalse(validateFilm(newFilm), "Неверное поведение при некорректном назавании");

    }

    @Test
    void shouldFalseWhenIncorrectFilmDescription() {
        newFilm.setDescription("Фильм о шпионе".repeat(200));
        assertFalse(validateFilm(newFilm), "Неверное поведение при некорректном описании");

    }

    @Test
    void shouldFalseWhenIncorrectFilmReleaseDate() {

        newFilm.setReleaseDate(LocalDate.parse("1800-12-12"));
        assertFalse(validateFilm(newFilm), "Неверное поведение при некорректной дате");

    }

    @Test
    void shouldFalseWhenIncorrectFilmDuration() {

        newFilm.setDuration(-120);
        assertFalse(validateFilm(newFilm), "Неверное поведение при отрицательной продолжительности");

    }

    @Test
    void shouldFalseWhenFilmIsNull() {
        Film emptyFilm = null;
        assertFalse(validateFilm(emptyFilm), "Неверное поведение при отрицательной продолжительности");

    }


    @Test
    void shouldTrueWhenCorrectUserData() {
        assertTrue(validateUser(newUser), "Неверное поведение при корректных данных");

    }

    @Test
    void shouldFalseWhenIncorrectUserEmail() {
        newUser.setEmail("");
        assertFalse(validateUser(newUser), "Неверное поведение при некорректном email");
        newUser.setEmail("saltykov&yandex.ru");
        assertFalse(validateUser(newUser), "Неверное поведение при некорректном email");


    }

    @Test
    void shouldFalseWhenIncorrectUserLogin() {
        newUser.setLogin("");
        assertFalse(validateUser(newUser), "Неверное поведение при некорректном Login");
        newUser.setLogin("ka sa il");
        assertFalse(validateUser(newUser), "Неверное поведение при некорректном Login");


    }

    @Test
    void shouldUseLoginWhenNameIsEmpty() {
        User newUser2 = new User(1, "Saltykov@yandex.ru", "kasail", null, "1993-09-29");
        assertEquals(newUser2.getName(), newUser2.getLogin(), "Неверное поведение при отсутвующем имени пользователя");

    }

    @Test
    void shouldFalseWhenIncorrectUserBirthday() {
        newUser.setBirthday(LocalDate.parse("2023-12-12"));
        assertFalse(validateUser(newUser), "Неверное поведение при некорректной дате рождения");


    }

    @Test
    void shouldFalseWhenIncorrectObjectsIsNull() {
        newUser = null;
        newFilm = null;
        assertFalse(validateUser(newUser), "Неверное поведение при отсутвующих ссылках на обьекты");
        assertFalse(validateFilm(newFilm), "Неверное поведение при отсутвующих ссылках на обьекты");


    }


}
