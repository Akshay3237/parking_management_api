package parking.management.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import parking.management.entity.Transaction;

import java.util.List;

@Repository
public class TransactionDaoImplementation implements TransactionDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(Transaction transaction) {
        entityManager.persist(transaction);
    }

    @Override
    @Transactional
    public void delete(Integer transactionId) {
        if (transactionId == null) {
            throw new NullPointerException("Transaction ID cannot be null");
        }
        try {
            Transaction transaction = entityManager.find(Transaction.class, transactionId);
            if (transaction != null) {
                entityManager.remove(transaction);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex; // Rethrow the exception
        }
    }

    @Override
    @Transactional
    public void update(Transaction transaction) {
        entityManager.merge(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findAll() {
        TypedQuery<Transaction> query = entityManager.createQuery("SELECT t FROM Transaction t", Transaction.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Transaction findById(Long transactionId) {
        if (transactionId == null) {
            throw new NullPointerException("Transaction ID cannot be null");
        }
        return entityManager.find(Transaction.class, transactionId);
    }
    
    @Override
    @Transactional
    public void deleteTransactionByHistoryId(Integer historyId) {
        entityManager.createQuery("DELETE FROM Transaction t WHERE t.history.historyId = :historyId")
                .setParameter("historyId", historyId)
                .executeUpdate();
    }
}
