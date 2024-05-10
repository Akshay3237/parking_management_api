package parking.management.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import parking.management.bcrypt.BcryptionPassword;
import parking.management.entity.ParkingZone;
import parking.management.entity.User;

import java.util.List;

@Repository
public class ParkingZoneDaoImplementation implements ParkingZoneDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(ParkingZone parkingZone) {
    	parkingZone.getParkingManager().setPassword(BcryptionPassword.Conversion(parkingZone.getParkingManager().getPassword()));
        entityManager.persist(parkingZone);
    }

    @Override
    @Transactional
    public void delete(Integer parkingZoneId) {
        if (parkingZoneId == null) {
            throw new NullPointerException("ParkingZone ID cannot be null");
        }
        try {
            ParkingZone parkingZone = entityManager.find(ParkingZone.class, parkingZoneId);
            if (parkingZone != null) {
                entityManager.remove(parkingZone);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex; // Rethrow the exception
        }
    }

    @Override
    @Transactional
    public void update(ParkingZone parkingZone) {
        entityManager.merge(parkingZone);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingZone> findAll() {
        TypedQuery<ParkingZone> query = entityManager.createQuery("SELECT p FROM ParkingZone p", ParkingZone.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public ParkingZone findById(Integer parkingZoneId) {
        if (parkingZoneId == null) {
            throw new NullPointerException("ParkingZone ID cannot be null");
        }
        return entityManager.find(ParkingZone.class, parkingZoneId);
    }

	@Override
	public ParkingZone findByManager(User user) {
		List<ParkingZone> parkingZones=findAll();
		
		for(ParkingZone parkingZone:parkingZones) {
			if(parkingZone.getParkingManager().toString().equals(user.toString())) {
				return parkingZone;
			}
		}
		return null;
	}
}
