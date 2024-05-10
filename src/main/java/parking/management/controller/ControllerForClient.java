package parking.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import parking.management.entity.ParkingSpaceRemain;
import parking.management.entity.ParkingZoneViewForClient;
import parking.management.entity.Transaction;
import parking.management.service.ServiceForClient;

@RestController
@RequestMapping("/client")
public class ControllerForClient {
	
	ServiceForClient serviceForClient;
	
	@Autowired
	public ControllerForClient(ServiceForClient serviceForClient) {
		super();
		this.serviceForClient = serviceForClient;
	}

	@CrossOrigin(origins="*")
	@GetMapping("/transactions/{transactionId}")
	public Transaction getTransactionById(@PathVariable Long transactionId) {
		return serviceForClient.getTransactionById(transactionId);
	}
	@CrossOrigin(origins="*")
	@GetMapping("/parkingzones")
	public List<ParkingZoneViewForClient> getViews(){
		return serviceForClient.getViews();
	}
	
	@CrossOrigin(origins="*")	
	@GetMapping("/parkingzones/{parkingZoneId}")
    public ParkingSpaceRemain totalVehicleByParkingId(@PathVariable Long parkingZoneId) {
		
    	return serviceForClient.totalVehicleByParkingId(parkingZoneId);
    }
}
