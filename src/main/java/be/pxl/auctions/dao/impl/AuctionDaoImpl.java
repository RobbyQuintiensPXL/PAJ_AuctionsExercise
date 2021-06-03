package be.pxl.auctions.dao.impl;

import be.pxl.auctions.dao.AuctionDao;
import be.pxl.auctions.model.Auction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class AuctionDaoImpl implements AuctionDao {

    private static final Logger LOGGER = LogManager.getLogger(AuctionDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Auction saveAuction(Auction auction) {
        LOGGER.info("Saving auction [" + auction + "]");
        entityManager.persist(auction);
        return auction;
    }

    public Optional<Auction> findAuctionById(long auction) {
        return Optional.ofNullable(entityManager.find(Auction.class, auction));
    }

    public List<Auction> findAllAuctions() {
        TypedQuery<Auction> findAllQuery = entityManager.createNamedQuery("findAllAuctions", Auction.class);
        return findAllQuery.getResultList();
    }
}
