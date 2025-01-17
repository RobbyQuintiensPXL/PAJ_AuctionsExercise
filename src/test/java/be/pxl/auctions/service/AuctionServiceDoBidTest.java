package be.pxl.auctions.service;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.Bid;
import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.AuctionDTO;
import be.pxl.auctions.rest.resource.BidCreateResource;
import be.pxl.auctions.util.exception.InvalidBidException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceDoBidTest {

    private static final long AUCTION_ID = 5L;

    @Mock
    private AuctionDao auctionDao;
    @Mock
    private UserDao userDao;
    @InjectMocks
    private AuctionService auctionService;
    private Auction auction;
    private User user01;
    private User user02;

    @BeforeEach
    public void init() {
        user01 = new User();
        user01.setFirstName("User1");
        user01.setEmail("user1@test.be");
        user02 = new User();
        user02.setFirstName("User2");
        user02.setEmail("user2@test.be");

        auction = new Auction();
        auction.setId(AUCTION_ID);
        auction.setDescription("Veiling 01");
        auction.setEndDate(LocalDate.now().plusDays(2));
        auction.addBid(new Bid(user01, LocalDate.now(), 500));
        auction.addBid(new Bid(user02, LocalDate.now(), 700));
    }

    @Test
    public void returnHighestBodAfterAdding() {
        when(auctionDao.findAuctionById(AUCTION_ID)).thenReturn(Optional.of(auction));
        AuctionDTO auctionDto = auctionService.getAuctionById(AUCTION_ID);

        assertEquals(700, auctionDto.getHighestBid());
    }

    @Test
    public void returnExceptionWhenBodIsLower() {
        when(auctionDao.findAuctionById(AUCTION_ID)).thenReturn(Optional.of(auction));
        when(userDao.findUserByEmail(any())).thenReturn(Optional.of(user01));

        BidCreateResource bidCreateResource = new BidCreateResource();
        bidCreateResource.setEmail(user01.getEmail());
        bidCreateResource.setPrice(600);


        assertThrows(InvalidBidException.class, () -> auctionService.doBid(AUCTION_ID, bidCreateResource));
    }

    @Test
    public void returnExceptionBodBySameUser() {
        when(auctionDao.findAuctionById(AUCTION_ID)).thenReturn(Optional.of(auction));
        when(userDao.findUserByEmail(any())).thenReturn(Optional.of(user02));

        BidCreateResource bidCreateResource = new BidCreateResource();
        bidCreateResource.setEmail(user02.getEmail());
        bidCreateResource.setPrice(800);

        assertThrows(InvalidBidException.class, () -> auctionService.doBid(AUCTION_ID, bidCreateResource));
    }



}
