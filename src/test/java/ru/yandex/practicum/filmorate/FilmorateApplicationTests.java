package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exeptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserDbStorage;
import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.util.Optional;


@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

class FilmorateApplicationTests {

	private final FilmService filmService;




	@Test
	public void testFindFilmById() throws ValidationException {

		Film testFilm = new Film(1,"name", "description", LocalDate.of(1993, 9, 28), 120, new Mpa(1,"G"));

		int filmId = filmService.create(testFilm).getId();

		Optional<Film> filmOptional = Optional.ofNullable(filmService.getFilmByID(filmId));
		assertThat(filmOptional)
				.isPresent()
				.hasValueSatisfying(film ->
						assertThat(film).hasFieldOrPropertyWithValue("id", filmId)
				);
	}
}


