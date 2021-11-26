package com.shyrski.profit.tracker.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shyrski.profit.tracker.client.MessariClient;
import com.shyrski.profit.tracker.model.TransactionStatistics;
import com.shyrski.profit.tracker.model.db.Currency;
import com.shyrski.profit.tracker.model.db.Transaction;
import com.shyrski.profit.tracker.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final MessariClient messariClient;

    public TransactionStatistics getStatistics() {
        TransactionStatistics statistics = new TransactionStatistics();
        List<Transaction> allTransactions = transactionRepository.findAll();

        Double invested = 0.0;

        List<String> currencyCodes = allTransactions.stream()
                .map(Transaction::getCurrency)
                .map(Currency::getCode)
                .distinct()
                .collect(Collectors.toList());

        HashMap<String, Double> tokenPrices = createPricesMap(currencyCodes);
        double currentPrice = 0.0;

        for (Transaction transaction : allTransactions) {
            invested += transaction.getValue();
            double currentTokenPrice = transaction.getAmount() * tokenPrices.get(transaction.getCurrency().getCode());
            currentPrice += currentTokenPrice;
        }

        statistics.setInvested(invested);
        statistics.setCurrentCost(currentPrice);
        statistics.setCostChange(currentPrice - invested);
        statistics.setPercentageChange(calculatePercentageChange(statistics));
        return statistics;
    }

    private Double calculatePercentageChange(TransactionStatistics statistics) {
        return (statistics.getCostChange() * 100) / statistics.getInvested();
    }

    private HashMap<String, Double> createPricesMap(List<String> currencyCodes) {
        HashMap<String, Double> tokenPrices = new HashMap<>();

        currencyCodes.forEach(code ->
                tokenPrices.put(code, messariClient.findTokenRate(code).getData().getMarketData().getPriceUsd())
        );

        return tokenPrices;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}
