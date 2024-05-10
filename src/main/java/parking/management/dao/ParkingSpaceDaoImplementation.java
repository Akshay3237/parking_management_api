package parking.management.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import parking.management.entity.ParkingSpace;
import parking.management.entity.ParkingZone;

import java.util.List;

@Repository
public class ParkingSpaceDaoImplementation implements ParkingSpaceDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(ParkingSpace parkingSpace) {
        entityManager.persist(parkingSpace);
    }

    @Override
    @Transactional
    public void delete(Integer parkingSpaceId) {
    	System.out.println(parkingSpaceId);
        if (parkingSpaceId == null) {
            throw new NullPointerException("ParkingSpace ID cannot be null");
        }
        try {
            ParkingSpace parkingSpace = entityManager.find(ParkingSpace.class, parkingSpaceId);
            if (parkingSpace != null) {
            	System.out.println(parkingSpace);
                entityManager.remove(parkingSpace);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex; // Rethrow the exception
        }
    }

    @Override
    @Transactional
    public void update(ParkingSpace parkingSpace) {
        entityManager.merge(parkingSpace);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingSpace> findAll() {
        TypedQuery<ParkingSpace> query = entityManager.createQuery("SELECT p FROM ParkingSpace p", ParkingSpace.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParkingSpace findById(Long parkingSpaceId) {
        if (parkingSpaceId == null) {
            throw new NullPointerException("ParkingSpace ID cannot be null");
        }
        return entityManager.find(ParkingSpace.class, parkingSpaceId);
    }

    @Override
    @Transactional
    public void deleteParkingSpacesByParkingZoneId(Integer parkingZoneId) {
        entityManager.createQuery("DELETE FROM ParkingSpace ps WHERE ps.parkingZone.parkingZoneId = :parkingZoneId")
                .setParameter("parkingZoneId", parkingZoneId)
                .executeUpdate();
    }
	
}
