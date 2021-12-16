package com.shyrski.profit.tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyrski.profit.tracker.model.db.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByUserId(String userId);
}
