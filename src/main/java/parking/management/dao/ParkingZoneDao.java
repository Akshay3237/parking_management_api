package parking.management.dao;

import parking.management.entity.ParkingZone;
import parking.management.entity.User;

import java.util.List;

public interface ParkingZoneDao {
    void insert(ParkingZone parkingZone);
    void delete(Integer parkingZoneId);
    void update(ParkingZone parkingZone);
    List<ParkingZone> findAll();
    ParkingZone findById(Integer parkingZoneId);
    ParkingZone findByManager(User user);
}
