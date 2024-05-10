package parking.management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import parking.management.entity.History;
import parking.management.entity.ParkingSpace;
import parking.management.entity.ParkingSpaceRemain;
import parking.management.entity.Profit;
import parking.management.entity.Transaction;
import parking.management.entity.User;
import parking.management.service.ServiceForPM;

@RestController
@RequestMapping("/parkingmanager")
public class ControllerForPM {
	
   final private ServiceForPM serviceForPM;
	
	public ControllerForPM(ServiceForPM serviceForPM) {
	super();
	this.serviceForPM = serviceForPM;
}
	@CrossOrigin(origins="*")
	@GetMapping("/users")
	public List<User> getAllUser(){
		return serviceForPM.geAlltUser();
	}
	@CrossOrigin(origins="*")
	@PostMapping("/users")
	public User createCustomer(@RequestBody User user) {
		return serviceForPM.createCustomer(user);
	}
	@CrossOrigin(origins="*")
	@GetMapping("/availablespaces")
	public ParkingSpaceRemain getAvalableSpaces() {
		return serviceForPM.getAvaliblitParkingSpaces();
	}
	@CrossOrigin(origins="*")
	@GetMapping("/parkingspaces")
	public List<ParkingSpace> getParkingSpaces(){
		return serviceForPM.getParkingSpaces();
	}
	@CrossOrigin(origins="*")
	@PostMapping("/parkingspaces")
	public ParkingSpace allocation(@RequestBody ParkingSpace parkingSpace) {
			return serviceForPM.AllocationOfParkingSpace(parkingSpace);
	}
	@CrossOrigin(origins="*")
	@DeleteMapping("/parkingspaces/{parkingspaceid}")
	public Transaction disallocation(@PathVariable Long parkingspaceid) {
//		return null;
		return serviceForPM.disAllocatePlace(parkingspaceid);
	}
	
	@CrossOrigin(origins="*")
	@GetMapping("/transactions/{transactionId}")
	Transaction getTransactionById(@PathVariable Long transactionId) {
		return serviceForPM.getTransactionById(transactionId);
	}
	@CrossOrigin(origins="*")
	@PutMapping("/transactions/{transactionId}")
	Transaction paiTransactionById(@PathVariable Long transactionId) {
		return serviceForPM.payTransactionById(transactionId);
	}
	@CrossOrigin(origins="*")
	@GetMapping("/transactions")
	public List<Transaction> getAllTrancaTransactions(){
		return serviceForPM.getAllTrancaTransactions();
	}
	@CrossOrigin(origins="*")
	@GetMapping("/vehicles")
	public ParkingSpaceRemain getAllVehicles() {
		return serviceForPM.getAllVehicles();
	}
	@CrossOrigin(origins="*")
	@GetMapping("/profit")
	public Profit getProfite() {
		
		return serviceForPM.getProfite();
	}
	@CrossOrigin(origins="*")
	@GetMapping("/histories")
	public List<History> getHistories(){
		return serviceForPM.getHistories();
	}
}
