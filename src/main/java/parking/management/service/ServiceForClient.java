package parking.management.service;

import java.util.List;

import parking.management.entity.ParkingSpaceRemain;
import parking.management.entity.ParkingZoneViewForClient;
import parking.management.entity.Transaction;

public interface ServiceForClient {
	public Transaction getTransactionById(Long transactionId);
	
	public List<ParkingZoneViewForClient> getViews();
	
    public ParkingSpaceRemain totalVehicleByParkingId(Long parkingZoneId);
}
