package parking.management.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import parking.management.entity.History;

import java.util.List;

@Repository
public class HistoryDaoImplementation implements HistoryDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(History history) {
        entityManager.persist(history);
    }

    @Override
    @Transactional
    public void delete(Integer historyId) {
        if (historyId == null) {
            throw new NullPointerException("History ID cannot be null");
        }
        try {
            History history = entityManager.find(History.class, historyId);
            if (history != null) {
                entityManager.remove(history);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex; // Rethrow the exception
        }
    }

    @Override
    @Transactional
    public void update(History history) {
        entityManager.merge(history);
    }

    @Override
    @Transactional(readOnly = true)
    public List<History> findAll() {
        TypedQuery<History> query = entityManager.createQuery("SELECT h FROM History h", History.class);
        return query.getResultList();
    }
    @Override
    @Transactional(readOnly = true)
    public History findById(Integer historyId) {
        if (historyId == null) {
            throw new NullPointerException("History ID cannot be null");
        }
        return entityManager.find(History.class, historyId);
    }
    @Override
    @Transactional(readOnly = true)
    public List<History> findHistoriesByParkingZoneId(Long parkingZoneId) {
        TypedQuery<History> query = entityManager.createQuery(
            "SELECT h FROM History h WHERE h.parkingZone.id = :parkingZoneId", 
            History.class
        );
        query.setParameter("parkingZoneId", parkingZoneId);
        return query.getResultList();
    }
}
