package parking.management.service;

import java.util.List;

import parking.management.entity.History;
import parking.management.entity.ParkingSpaceRemain;
import parking.management.entity.ParkingZone;
import parking.management.entity.Profit;
import parking.management.entity.Transaction;
import parking.management.entity.User;

public interface ServiceForAdmin {

  public User createManager(User user);
	
  public List<User> getAllUsers();
  
  public List<User> getParkingManagers();
	
  public List<User> getAllClients();
  
  public List<User> getClientByParkingZoneId(Long parkingZoneId);
  
  public List<ParkingZone> getAllParkingZones();
	
  public ParkingZone createParkingZone(ParkingZone parkingZone);
	
  public ParkingSpaceRemain totalVehicle();
  
  public ParkingSpaceRemain totalVehicleByParkingId(Long parkingZoneId);
  
  public Profit getProfite();
  
  public Profit getProfiteByParkingZoneId(Long parkingZoneId);
  
  public List<History> getHistories();
  
  public List<History> getHistoriesByParkingZoneId(Long parkingZoneId);
  
  public List<Transaction> getTransactions();
  
  public List<Transaction> getTransactionsByParkingZoneId(Long parkingZoneId);
  
}
