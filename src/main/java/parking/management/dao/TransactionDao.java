package parking.management.dao;

import parking.management.entity.Transaction;

import java.util.List;

public interface TransactionDao {
    void insert(Transaction transaction);
    void delete(Integer transactionId);
    void update(Transaction transaction);
    List<Transaction> findAll();
    Transaction findById(Long transactionId);
    void deleteTransactionByHistoryId(Integer historyId);
}
