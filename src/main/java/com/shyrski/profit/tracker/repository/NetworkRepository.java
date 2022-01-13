package com.shyrski.profit.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyrski.profit.tracker.model.db.Network;

public interface NetworkRepository extends JpaRepository<Network, Long> {
}
