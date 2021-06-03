package be.pxl.auctions.util;

import be.pxl.auctions.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailValidatorIsValidTest {

	private User user;

	@BeforeEach
	public void setup(){
		user = new User();
	}

	@Test
	public void returnsTrueWhenValidEmail() {
		user.setEmail("user@mail.be");
		assertTrue(hasEmailAtSign(user.getEmail()));
	}

	@Test
	public void returnsFalseWhenAtSignMissing() {
		user.setEmail("usermail.be");
		assertFalse(hasEmailAtSign(user.getEmail()));
	}

	public boolean hasEmailAtSign(String s){
		boolean check = false;
		for (int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if (c == '@') {
				check = true;
				break;
			}
		}
		return check;
	}

}
