package parking.management.dao;

import parking.management.entity.ParkingSpace;
import parking.management.entity.ParkingZone;

import java.util.List;

public interface ParkingSpaceDao {
    void insert(ParkingSpace parkingSpace);
    void delete(Integer parkingSpaceId);
    void update(ParkingSpace parkingSpace);
    List<ParkingSpace> findAll();
    ParkingSpace findById(Long placeId);
    void deleteParkingSpacesByParkingZoneId(Integer parkingZoneId);
}
