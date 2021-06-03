package be.pxl.auctions.rest.resource;

import be.pxl.auctions.model.Bid;
import be.pxl.auctions.model.User;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AuctionDTO {
    private long id;
    private String description;
    private LocalDate endDate;
    private int numberOfBids;
    private double highestBid;
    private String highestBidBy;
    private boolean finished;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public void setHighestBid(double highestBid) {
        this.highestBid = highestBid;
    }

    public int getNumberOfBids() {
        return numberOfBids;
    }

    public void setNumberOfBids(int numberOfBids) {
        this.numberOfBids = numberOfBids;
    }

    public String getHighestBidBy() {
        return highestBidBy;
    }

    public void setHighestBidBy(String highestBidBy) {
        this.highestBidBy = highestBidBy;
    }
}
