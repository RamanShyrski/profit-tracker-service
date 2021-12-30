package com.shyrski.profit.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shyrski.profit.tracker.model.db.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {
    String FIND_ALL_COLLECTIONS = """
            select *
            from COLLECTION c
                     inner join NETWORK n on c.NETWORK_ID = n.NETWORK_ID
                     inner join PORTFOLIO p on c.PORTFOLIO_ID = p.PORTFOLIO_ID
            where p.PORTFOLIO_ID = :portfolioId
                AND (:name is NULL or c.NAME LIKE concat('%', :name, '%'))
                AND (:network is NULL or n.NAME LIKE concat('%', :network, '%'))""";

    @Query(value = FIND_ALL_COLLECTIONS, nativeQuery = true)
    List<Collection> findAllBySearchCriteria(@Param("portfolioId") Long portfolioId, @Param("name") String name,
                                             @Param("network") String network);
}
