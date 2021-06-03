package be.pxl.auctions.service;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.rest.resource.AuctionDTO;
import be.pxl.auctions.util.exception.AuctionNotfoundException;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceGetAuctionByIdTest {

    private static final long AUCTION_ID = 5L;

    @Mock
    private AuctionDao auctionDao;
    @InjectMocks
    private AuctionService auctionService;
    private Auction auction;

    @BeforeEach
    public void init(){
        auction = new Auction();
        auction.setId(AUCTION_ID);
        auction.setDescription("Veiling 01");
        auction.setEndDate(LocalDate.now().plusDays(2));
    }

    @Test
    public void returnsNullWhenNoAuctionWithGivenIdFound(){
        when(auctionDao.findAuctionById(AUCTION_ID)).thenReturn(Optional.empty());

        assertThrows(AuctionNotfoundException.class, () -> auctionService.getAuctionById(AUCTION_ID));
    }

    @Test
    public void returnsAuctionWhenAuctionWithGivenIdFound(){
        when(auctionDao.findAuctionById(AUCTION_ID)).thenReturn(Optional.of(auction));
        AuctionDTO auctionDto = auctionService.getAuctionById(AUCTION_ID);

        assertEquals(AUCTION_ID, auctionDto.getId());
    }

}
