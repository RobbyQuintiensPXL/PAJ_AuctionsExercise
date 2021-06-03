package be.pxl.auctions.dao.impl;

import be.pxl.auctions.model.User;
import be.pxl.auctions.util.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class UserDaoImplTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserDaoImpl userDao;

	private User user;

	@BeforeEach
	public void setup(){
		user = new User();
		user.setFirstName("Mark");
		user.setLastName("Zuckerberg");
		user.setDateOfBirth(LocalDate.of(1989, 5, 3));
		user.setEmail("mark@facebook.com");
	}

	@Test
	public void userCanBeSavedAndRetrievedById() {

		long newUserId = userDao.saveUser(user).getId();
		entityManager.flush();
		entityManager.clear();

		Optional<User> retrievedUser = userDao.findUserById(newUserId);
		assertTrue(retrievedUser.isPresent());

		assertEquals(user.getFirstName(), retrievedUser.get().getFirstName());
		assertEquals(user.getLastName(), retrievedUser.get().getLastName());
		assertEquals(user.getEmail(), retrievedUser.get().getEmail());
		assertEquals(user.getDateOfBirth(), retrievedUser.get().getDateOfBirth());
	}
	@Test
	public void userCanBeSavedAndRetrievedByEmail() {
		String newUserEmail = userDao.saveUser(user).getEmail();
		entityManager.flush();
		entityManager.clear();

		Optional<User> retrievedUser = userDao.findUserByEmail(newUserEmail);
		assertTrue(retrievedUser.isPresent());

		assertEquals(user.getFirstName(), retrievedUser.get().getFirstName());
		assertEquals(user.getLastName(), retrievedUser.get().getLastName());
		assertEquals(user.getEmail(), retrievedUser.get().getEmail());
		assertEquals(user.getDateOfBirth(), retrievedUser.get().getDateOfBirth());
		assertEquals(user.getId(), retrievedUser.get().getId());
	}

	@Test
	public void returnsNullWhenNoUserFoundWithGivenEmail() {
		Optional<User> retrievedUser = userDao.findUserByEmail("test@test.be");
		assertEquals(retrievedUser, Optional.empty());
	}

	@Test
	public void allUsersCanBeRetrieved() {
		// TODO implement this test
		// create and save one user
		User newUser = new User();
		user.setFirstName("Jaak");
		user.setLastName("Trekhaak");
		user.setEmail("jaak@mail.be");
		user.setDateOfBirth(LocalDate.of(1987, 10, 5));

		long newUserId = userDao.saveUser(user).getId();
		entityManager.flush();
		entityManager.clear();

		// retrieve all users
		// make sure there is at least 1 user in the list
		// make sure the newly created user is in the list (e.g. test if a user with this email address is in the list)

		List<User> retrievedUsers = userDao.findAllUsers();
		assertTrue(retrievedUsers.size() > 0);

	}


}
