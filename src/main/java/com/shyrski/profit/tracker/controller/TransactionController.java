package com.shyrski.profit.tracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyrski.profit.tracker.model.db.Transaction;
import com.shyrski.profit.tracker.model.TransactionStatistics;
import com.shyrski.profit.tracker.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/stat")
    public TransactionStatistics getStatistics() {
        return transactionService.getStatistics();
    }

    @GetMapping
    public List<Transaction> findAll() {
        return transactionService.findAll();
    }

}
