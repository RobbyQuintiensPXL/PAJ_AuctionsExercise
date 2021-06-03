package be.pxl.auctions.rest;

import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.Bid;
import be.pxl.auctions.rest.resource.AuctionCreateResource;
import be.pxl.auctions.rest.resource.AuctionDTO;
import be.pxl.auctions.rest.resource.BidCreateResource;
import be.pxl.auctions.service.AuctionService;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("rest/auctions")
public class AuctionRest {
    private static final Logger LOGGER = LogManager.getLogger(AuctionRest.class);

    @Autowired
    private AuctionService auctionService;

    @GetMapping
    public List<AuctionDTO> getAllAuctions() {
        return new ArrayList<>(auctionService.getAllAuctions());
    }

    @GetMapping("{auctionId}")
    public AuctionDTO getAuctionById(@PathVariable("auctionId") long auctionId) {
        return auctionService.getAuctionById(auctionId);
    }

    @PostMapping
    public AuctionDTO createAuction(@RequestBody AuctionCreateResource auctionCreateResource) {
        return auctionService.createAuction(auctionCreateResource);
    }

    @PostMapping("{auctionId}/bids")
    public AuctionDTO addBid(@PathVariable("auctionId") long auctionId, BidCreateResource bidCreateResource) throws NotFoundException {
        auctionService.doBid(auctionId, bidCreateResource);

        return auctionService.getAuctionById(auctionId);
    }

}
