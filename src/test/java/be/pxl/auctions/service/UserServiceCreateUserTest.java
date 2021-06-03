package be.pxl.auctions.service;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.UserCreateResource;
import be.pxl.auctions.rest.resource.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceCreateUserTest {

    @Mock
    private UserDao userDao;
    @InjectMocks
    private UserService userService;
    private User user;

    @BeforeEach
    public void init(){
        user = new User();
        user.setFirstName("Jaak");
        user.setLastName("De Tester");
        user.setEmail("jaak@mail.be");
        user.setDateOfBirth(LocalDate.of(1980, 5,5));
    }

    @Test
    public void userCanBeCreated(){
        when(userDao.saveUser(any(User.class))).thenReturn(user);
        UserCreateResource userCreateResource = new UserCreateResource();
        userCreateResource.setDateOfBirth("05/05/1980");
        userCreateResource.setEmail(user.getEmail());
        userCreateResource.setLastName(user.getLastName());
        userCreateResource.setFirstName(user.getFirstName());
        UserDTO userDTO = userService.createUser(userCreateResource);
        assertEquals(userDTO.getEmail(), userCreateResource.getEmail());
    }
}
