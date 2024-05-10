package parking.management.service;

import java.util.List;

import parking.management.entity.History;
import parking.management.entity.ParkingSpace;
import parking.management.entity.ParkingSpaceRemain;
import parking.management.entity.Profit;
import parking.management.entity.Transaction;
import parking.management.entity.User;

public interface ServiceForPM {
	public User createCustomer(User user);
	public List<User> geAlltUser();
	public ParkingSpaceRemain getAvaliblitParkingSpaces();
	public ParkingSpace AllocationOfParkingSpace(ParkingSpace parkingSpace);
	public List<ParkingSpace> getParkingSpaces();
	public Transaction disAllocatePlace(Long placeId);
	public Transaction getTransactionById(Long transactionId);
	public Transaction payTransactionById(Long transactionId);
	public List<Transaction> getAllTrancaTransactions();
	public ParkingSpaceRemain getAllVehicles();
	public Profit getProfite();
	public List<History> getHistories();
}
