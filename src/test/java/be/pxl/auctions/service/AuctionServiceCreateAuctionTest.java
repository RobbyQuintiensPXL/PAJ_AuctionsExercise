package be.pxl.auctions.service;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.AuctionCreateResource;
import be.pxl.auctions.rest.resource.AuctionDTO;
import be.pxl.auctions.rest.resource.UserCreateResource;
import be.pxl.auctions.rest.resource.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceCreateAuctionTest {

    @Mock
    private AuctionDao auctionDao;
    @InjectMocks
    private AuctionService auctionService;
    private Auction auction;

    @BeforeEach
    public void init(){
        auction = new Auction();
        auction.setDescription("veiling 01");
    }

    @Test
    public void auctionCanBeCreated(){
        auction.setEndDate(LocalDate.now().plusDays(2));

        when(auctionDao.saveAuction(any(Auction.class))).thenReturn(auction);
        AuctionCreateResource auctionCreateResource = new AuctionCreateResource();
        auctionCreateResource.setDescription(auction.getDescription());
        auctionCreateResource.setEndDate(auction.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/uuuu")).toString());

        AuctionDTO auctionDTO = auctionService.createAuction(auctionCreateResource);
        assertEquals(auctionDTO.getDescription(), auctionCreateResource.getDescription());
    }
}
