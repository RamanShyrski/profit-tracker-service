package com.shyrski.profit.tracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyrski.profit.tracker.model.db.CollectionMarketplace;

public interface CollectionMarketplaceRepository extends JpaRepository<CollectionMarketplace, Long> {
    Optional<CollectionMarketplace> findByName(String name);
}
