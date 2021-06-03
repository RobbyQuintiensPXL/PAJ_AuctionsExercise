package be.pxl.auctions.service;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.dao.UserDao;
import be.pxl.auctions.model.Auction;
import be.pxl.auctions.model.Bid;
import be.pxl.auctions.model.User;
import be.pxl.auctions.rest.resource.AuctionCreateResource;
import be.pxl.auctions.rest.resource.AuctionDTO;
import be.pxl.auctions.rest.resource.BidCreateResource;
import be.pxl.auctions.util.exception.*;
import javassist.NotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionService {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu");

    @Autowired
    private AuctionDao auctionDao;
    @Autowired
    private UserDao userDao;

    public List<AuctionDTO> getAllAuctions() {
        return auctionDao.findAllAuctions().stream().map(this::mapAuction).collect(Collectors.toList());
    }

    public AuctionDTO getAuctionById(long auctionId) {
        return auctionDao.findAuctionById(auctionId).map(this::mapAuction).orElseThrow(() -> new AuctionNotfoundException("Unable to find Auction with id [" + auctionId + "]"));
    }

    public AuctionDTO createAuction(AuctionCreateResource auctionInfo) throws InvalidDateException {
        Auction auction = mapToAuction(auctionInfo);
        if (StringUtils.isBlank(auctionInfo.getDescription())) {
            throw new RequiredFieldException("Description");
        }
        if (auction.getEndDate().isBefore(LocalDate.now())) {
            throw new InvalidDateException("Datum kan niet in het verleden liggen");
        }
        return mapAuction(auctionDao.saveAuction(auction));
    }

    private Auction mapToAuction(AuctionCreateResource auctionCreateResource) throws InvalidDateException {
        Auction auction = new Auction();
        auction.setDescription(auctionCreateResource.getDescription());
        try {
            auction.setEndDate(auctionCreateResource.getEndDate());
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("[" + auction.getEndDate() + "] is not a valid date. Excepted format: dd/mm/yyyy");
        }
        return auction;
    }

    private AuctionDTO mapAuction(Auction auction) {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(auction.getId());
        auctionDTO.setFinished(auction.isFinished());
        auctionDTO.setDescription(auction.getDescription());
        auctionDTO.setEndDate(auction.getEndDate());
        return auctionDTO;
    }

    public void doBid(Long id, BidCreateResource bidCreateResource) throws NotFoundException, InvalidBidException {
        Optional<Auction> auction = auctionDao.findAuctionById(id);
        if (auction.isEmpty()) {
            throw new NotFoundException("Veiling niet gevonden");
        }

        if(auction.get().isFinished()){
            throw new InvalidBidException("Deze veiling is reeds gesloten");
        }

        Optional<User> user = userDao.findUserByEmail(bidCreateResource.getEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("Gebruiker niet gevonden");
        }

        Auction newAuction = auction.get();
        User newUser = user.get();

        Bid bid = new Bid(newUser, LocalDate.now(), bidCreateResource.getPrice());
        bid.setAuction(newAuction);
        newAuction.addBid(bid);

        auctionDao.saveAuction(newAuction);
    }


}
