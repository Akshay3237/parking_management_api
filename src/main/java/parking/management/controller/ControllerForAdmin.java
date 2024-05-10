package parking.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import parking.management.dao.ParkingZoneDao;
import parking.management.entity.History;
import parking.management.entity.ParkingSpaceRemain;
import parking.management.entity.ParkingZone;
import parking.management.entity.Profit;
import parking.management.entity.Transaction;
import parking.management.entity.User;
import parking.management.service.ServiceForAdmin;

@RestController
@RequestMapping("/admin")
public class ControllerForAdmin {
	ServiceForAdmin serviceForAdmin;

	@Autowired
	public ControllerForAdmin(ServiceForAdmin serviceForAdmin) {
		super();
		this.serviceForAdmin = serviceForAdmin;
	}
	//Get Requests
	
	//request mapping for users/creation and deletion and updation
	@CrossOrigin(origins="*")
	@GetMapping("/users")
	public List<User> getAllUsers()
	{
		return serviceForAdmin.getAllUsers();
	}
	@CrossOrigin(origins="*")
	@PostMapping("/createmanager")
	public User createManager(@RequestBody User manager) {
		return serviceForAdmin.createManager(manager);
	}
	
	
	//parkingzone urls
	@CrossOrigin(origins="*")
	@GetMapping("/parkingzones")
	public List<ParkingZone> getAllParkingZones(){
		return serviceForAdmin.getAllParkingZones();
	}
	
	@CrossOrigin(origins="*")
	@PostMapping("/createparkingzone")
	public ParkingZone creaParkingZone(@RequestBody ParkingZone parkingZone) {
		return serviceForAdmin.createParkingZone(parkingZone);
	}
	
	
	@CrossOrigin(origins="*")
	@GetMapping("/vehicles")
	public ParkingSpaceRemain totalVehicles() {
		return serviceForAdmin.totalVehicle();
	}
	@CrossOrigin(origins="*")
	@GetMapping("/vehicles/{parkingZoneId}")
	public ParkingSpaceRemain totalVehicleByParkingZoneId(@PathVariable Long parkingZoneId) {
		return serviceForAdmin.totalVehicleByParkingId(parkingZoneId);
	}
	@CrossOrigin(origins="*")
	@GetMapping("/profit")
	public Profit getProfite() {
		return serviceForAdmin.getProfite();
	}
	@CrossOrigin(origins="*")
	@GetMapping("/profit/{parkingZoneId}")
	public Profit getProfiteById(@PathVariable Long parkingZoneId) {
		return serviceForAdmin.getProfiteByParkingZoneId(parkingZoneId);
	}
	@CrossOrigin(origins="*")
	@GetMapping("/histories")
	public List<History> getHistories(){
		return serviceForAdmin.getHistories();
	}
	@CrossOrigin(origins="*")
	@GetMapping("/histories/{parkingZoneId}")
	public List<History> getHistoriesByParkingZoneId(@PathVariable Long parkingZoneId){
		return serviceForAdmin.getHistoriesByParkingZoneId(parkingZoneId);
	}
	@CrossOrigin(origins="*")
	@GetMapping("/transactions")
	public List<Transaction> geTransactions(){
		return serviceForAdmin.getTransactions();
	}
	@CrossOrigin(origins="*")
	@GetMapping("/transactions/{parkingZoneId}")
	public List<Transaction> getTransactionsByParkingZoneId(@PathVariable Long parkingZoneId){
		return serviceForAdmin.getTransactionsByParkingZoneId(parkingZoneId);
	}
	@CrossOrigin(origins="*")
	@GetMapping("/parkingmanagers")
	public List<User> getParkingManagers(){
		return serviceForAdmin.getParkingManagers();
	}
	@CrossOrigin(origins="*")
	@GetMapping("/clients")
	  public List<User> getAllClients(){
		return serviceForAdmin.getAllClients();  
	  }
	@CrossOrigin(origins="*")
	@GetMapping("/clients/{parkingZoneId}")
	  public List<User> getClientByParkingZoneId(@PathVariable Long parkingZoneId){
		  return serviceForAdmin.getClientByParkingZoneId(parkingZoneId);
	  }

}
