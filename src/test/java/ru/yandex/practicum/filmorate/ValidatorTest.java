package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.Validator;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class ValidatorTest {

    Film newFilm;
    User newUser;

    @BeforeEach
    void init() {
        newFilm = new Film("Джеймс Бонд", "Фильм о шпионе", LocalDate.parse("2020-12-12"), 120);
        newUser = new User("Saltykov@yandex.ru", "kasail", "Nikita", "1993-09-29");
    }


    @Test
    void shouldTrueWhenCorrectFilmData() {
        assertTrue(Validator.validate(newFilm), "Неверное поведение при корректных данных");

    }

    @Test
    void shouldFalseWhenIncorrectFilmName() {
        newFilm.setName("");
        assertFalse(Validator.validate(newFilm), "Неверное поведение при некорректном назавании");
        newFilm.setName(null);
        assertFalse(Validator.validate(newFilm), "Неверное поведение при некорректном назавании");

    }

    @Test
    void shouldFalseWhenIncorrectFilmDescription() {
        newFilm.setDescription("Фильм о шпионе".repeat(200));
        assertFalse(Validator.validate(newFilm), "Неверное поведение при некорректном описании");

    }

    @Test
    void shouldFalseWhenIncorrectFilmReleaseDate() {

        newFilm.setReleaseDate(LocalDate.parse("1800-12-12"));
        assertFalse(Validator.validate(newFilm), "Неверное поведение при некорректной дате");

    }

    @Test
    void shouldFalseWhenIncorrectFilmDuration() {

        newFilm.setDuration(-120);
        assertFalse(Validator.validate(newFilm), "Неверное поведение при отрицательной продолжительности");

    }

    @Test
    void shouldFalseWhenFilmIsNull() {
        Film emptyFilm = null;
        assertFalse(Validator.validate(emptyFilm), "Неверное поведение при отрицательной продолжительности");

    }


    @Test
    void shouldTrueWhenCorrectUserData() {
        assertTrue(Validator.validate(newUser), "Неверное поведение при корректных данных");

    }

    @Test
    void shouldFalseWhenIncorrectUserEmail() {
        newUser.setEmail("");
        assertFalse(Validator.validate(newUser), "Неверное поведение при некорректном email");
        newUser.setEmail("saltykov&yandex.ru");
        assertFalse(Validator.validate(newUser), "Неверное поведение при некорректном email");


    }

    @Test
    void shouldFalseWhenIncorrectUserLogin() {
        newUser.setLogin("");
        assertFalse(Validator.validate(newUser), "Неверное поведение при некорректном Login");
        newUser.setLogin("ka sa il");
        assertFalse(Validator.validate(newUser), "Неверное поведение при некорректном Login");


    }

    @Test
    void shouldUseLoginWhenNameIsEmpty() {
        newUser.setName(null);
        Validator.validate(newUser);
        assertEquals(newUser.getName(), newUser.getLogin(), "Неверное поведение при отсутвующем имени пользователя");

    }

    @Test
    void shouldFalseWhenIncorrectUserBirthday() {
        newUser.setBirthday(LocalDate.parse("2023-12-12"));
        assertFalse(Validator.validate(newUser), "Неверное поведение при некорректной дате рождения");


    }

    @Test
    void shouldFalseWhenIncorrectObjectsIsNull() {
        newUser = null;
        newFilm = null;
        assertFalse(Validator.validate(newUser), "Неверное поведение при отсутвующих ссылках на обьекты");
        assertFalse(Validator.validate(newFilm), "Неверное поведение при отсутвующих ссылках на обьекты");


    }




}
