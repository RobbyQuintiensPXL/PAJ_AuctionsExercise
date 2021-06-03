package be.pxl.auctions.dao.impl;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.Bid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class AuctionDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AuctionDaoImpl auctionDao;

    private Auction auction;
    private Bid bid;
    private List<Bid> bidList;

    private static Random random;

    @BeforeEach
    public void setup(){
        bidList= new ArrayList<>();
        random = new Random();
        for (int i = 0; i < 3; i++){
            bid = new Bid();
            bid.setAmount(random.nextInt(500));
            bidList.add(bid);
        }

        auction = new Auction();
        auction.setBids(bidList);
        auction.setEndDate(LocalDate.now().plusDays(2));
        auction.setDescription("Auction 1");
    }

    @Test
    public void auctionCanBeSavedAndRetrievedById(){
        long newAuctionId = auctionDao.saveAuction(auction).getId();
        entityManager.flush();
        entityManager.clear();

        Optional<Auction> retrievedAuction = auctionDao.findAuctionById(newAuctionId);

        assertTrue(retrievedAuction.isPresent());

        assertEquals(auction.getId(), retrievedAuction.get().getId());
        assertEquals(auction.getDescription(), retrievedAuction.get().getDescription());
        assertEquals(auction.getBids().size(), retrievedAuction.get().getBids().size());
    }

    @Test
    public void allAuctionsCanBeRetrieved(){
        int loop = 4;
        for(int i = 0; i < loop; i++){
            Bid newBid = new Bid();
            newBid.setAmount(random.nextInt(200));
            List<Bid> newBidList = new ArrayList<>();
            newBidList.add(newBid);

            Auction newAuction = new Auction();
            auction.setDescription("Auction " + i);
            auction.setEndDate(LocalDate.now().plusDays(5));
            auction.setBids(newBidList);

            auctionDao.saveAuction(newAuction);
            entityManager.flush();
            entityManager.clear();
        }

        List<Auction> retrievedAuctions = auctionDao.findAllAuctions();
        assertEquals(retrievedAuctions.size(), loop);
    }

}
