package com.shyrski.profit.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shyrski.profit.tracker.model.db.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
