package be.pxl.auctions.model;


import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class FindHighestBidTest {

    private Auction auction;
    private LocalDate date;

    @BeforeEach
    public void setup() {
        auction = new Auction();
        date = LocalDate.now();
    }

    @Test
    public void findHighestBid() {
        Bid bid01 = new Bid();
        bid01.setAmount(500);

        Bid bid02 = new Bid();
        bid02.setAmount(800);

        Bid bid03 = new Bid();
        bid01.setAmount(300);

        List<Bid> bids = new ArrayList<>();
        bids.add(bid01);
        bids.add(bid02);
        bids.add(bid03);

        auction.setBids(bids);


        assertEquals(auction.findHighestBid().getAmount(), 800);
    }

    @Test
    public void isFinishedReturnsTrue() {
        auction.setEndDate(date.minusDays(1));

        assertTrue(auction.isFinished());
    }

    @Test
    public void isFinishedReturnsFalse() {
        auction.setEndDate(date.plusDays(1));

        assertFalse(auction.isFinished());
    }
}
