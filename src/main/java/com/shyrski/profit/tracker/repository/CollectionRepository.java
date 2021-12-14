package com.shyrski.profit.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shyrski.profit.tracker.model.db.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query(value = "select * from COLLECTION c where c.portfolio_id=:portfolioId", nativeQuery = true)
    List<Collection> findAllByPortfolioId(@Param("portfolioId") Long portfolioId);
}
