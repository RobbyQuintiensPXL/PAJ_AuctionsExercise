package be.pxl.auctions.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserGetAgeTest {

	private User user;
	private int month;
	private int day;
	private int year;
	private LocalDate birthday;

	@BeforeEach
	public void setup(){
		user = new User();
		month = LocalDate.now().getMonth().getValue();
		day = LocalDate.now().getDayOfMonth();
		year = 2021;
		birthday = LocalDate.of(1988,month, day);
	}

	@Test
	public void returnsCorrectAgeWhenHavingBirthdayToday() {
		user.setDateOfBirth(birthday);
		int years = Period.between(user.getDateOfBirth(), LocalDate.of(year, month, day)).getYears();

		assertEquals(years, 33);
	}

	@Test
	public void returnsCorrectAgeWhenHavingBirthdayTomorrow() {
		user.setDateOfBirth(birthday.plusDays(1));
		int years = Period.between(user.getDateOfBirth(), LocalDate.of(year, month, day)).getYears();

		assertEquals(years, 32);
	}

	@Test
	public void returnsCorrectAgeWhenBirthdayWasYesterday() {
		user.setDateOfBirth(birthday.minusDays(1));
		int years = Period.between(user.getDateOfBirth(), LocalDate.of(year, month, day)).getYears();

		assertEquals(years, 33);
	}


}
