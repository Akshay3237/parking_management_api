package parking.management.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import parking.management.dao.HistoryDao;
import parking.management.dao.ParkingSpaceDao;
import parking.management.dao.ParkingZoneDao;
import parking.management.dao.RolesDao;
import parking.management.dao.TransactionDao;
import parking.management.dao.UserDao;
import parking.management.entity.History;
import parking.management.entity.ParkingSpace;
import parking.management.entity.ParkingSpaceRemain;
import parking.management.entity.ParkingZone;
import parking.management.entity.Profit;
import parking.management.entity.Roles;
import parking.management.entity.Transaction;
import parking.management.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ServiceForPMImpl implements ServiceForPM {

	final private UserDao userDao;
	final private ParkingSpaceDao parkingSpaceDao;
	final private ParkingZoneDao parkingZoneDao;
	final private HistoryDao historyDao;
	final private RolesDao rolesDao;
	final private TransactionDao transactionDao;
	Authentication authentication;

	private User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Retrieve username from UserDetails
		String username = userDetails.getUsername();

		// Retrieve the user from the database using the username if needed
		return userDao.findById(Long.parseLong(username));
	}
	
	@Autowired
	public ServiceForPMImpl(UserDao userDao, ParkingSpaceDao parkingSpaceDao, ParkingZoneDao parkingZoneDao,
			HistoryDao historyDao, RolesDao rolesDao, TransactionDao transactionDao) {
		super();
		this.userDao = userDao;
		this.parkingSpaceDao = parkingSpaceDao;
		this.parkingZoneDao = parkingZoneDao;
		this.historyDao = historyDao;
		this.rolesDao = rolesDao;
		this.transactionDao = transactionDao;
		this.authentication = SecurityContextHolder.getContext().getAuthentication();

	}

	// service for creating user by parking maanger

	public User createCustomer(User user) {
		userDao.save(user);
		Roles role = new Roles(user, "ROLE_CLIENT");
		rolesDao.insert(role);
		return user;
	}

	// service for look user by parking manager

	public List<User> geAlltUser() {
		List<User> users = userDao.findAll();
		Iterator<User> iterator = users.iterator();

		while (iterator.hasNext()) {
			User user = iterator.next();
			if (rolesDao.findById(user, "ROLE_ADMIN") != null
					|| rolesDao.findById(user, "ROLE_PARKINGMANAGER") != null) {
				iterator.remove(); // Remove the current user using the iterator
			}
		}

		return users;
	}

	@Override
	public ParkingSpaceRemain getAvaliblitParkingSpaces() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Retrieve username from UserDetails
		String username = userDetails.getUsername();

		// Retrieve the user from the database using the username if needed
		User user = userDao.findById(Long.parseLong(username));

		ParkingZone parkingzone = parkingZoneDao.findByManager(user);

		ParkingSpaceRemain parkingSpaceRemain = new ParkingSpaceRemain();
//		
		int numberOfAuto = 0;
		int numberOfCar = 0;
		int numberOfBike = 0;
		int numberOfTruck = 0;
//		
		List<ParkingSpace> parkingSpaces = parkingSpaceDao.findAll();
//		
		for (ParkingSpace parkingSpace : parkingSpaces) {
			if (parkingSpace.getParkingZone() == parkingzone) {
				if (parkingSpace.getVehicleType().equals("C"))
					numberOfCar++;
				else if (parkingSpace.getVehicleType().equals("B"))
					numberOfBike++;
				else if (parkingSpace.getVehicleType().equals("T"))
					numberOfTruck++;
				else if (parkingSpace.getVehicleType().equals("A"))
					numberOfAuto++;
			}
		}
//		
		parkingSpaceRemain.setNumberOfAutos(parkingzone.getNumberOfAuto() - numberOfAuto);
		parkingSpaceRemain.setNumberOfBikes(parkingzone.getNumberOfBike() - numberOfBike);
		parkingSpaceRemain.setNumberOfCars(parkingzone.getNumberOfCar() - numberOfCar);
		parkingSpaceRemain.setNumberOfTrucks(parkingzone.getNumberOfTruck() - numberOfTruck);
		return parkingSpaceRemain;
	}

	@Override
	public ParkingSpace AllocationOfParkingSpace(ParkingSpace parkingSpace) {
		// logic for finding parking zone
		ParkingSpaceRemain parkingSpaceRemain = getAvaliblitParkingSpaces();
		String vihicleTypeString = parkingSpace.getVehicleType();

		if (vihicleTypeString.equals("C")) {
			if (parkingSpaceRemain.getNumberOfCars() <= 0)
				return null;
		} else if (vihicleTypeString.equals("B")) {
			if (parkingSpaceRemain.getNumberOfBikes() <= 0)
				return null;
		} else if (vihicleTypeString.equals("A")) {
			if (parkingSpaceRemain.getNumberOfAutos() <= 0)
				return null;
		} else if (vihicleTypeString.equals("T")) {
			if (parkingSpaceRemain.getNumberOfTrucks() <= 0)
				return null;
		} else {
			return null;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userDao.findById(Long.parseLong(username));
		ParkingZone parkingzone = parkingZoneDao.findByManager(user);
		// logic for finding user
		Long userIdInteger = parkingSpace.getUser().getUserId();
		user = userDao.findById(userIdInteger);
		parkingSpace.setParkingZone(parkingzone);
		parkingSpace.setUser(null);
		parkingSpaceDao.insert(parkingSpace);
		parkingSpace.setUser(user);
		ZoneId zoneId = ZoneId.of("Asia/Kolkata");
		parkingSpace.setStartTime(LocalDateTime.now(zoneId));
		parkingSpaceDao.update(parkingSpace);
		return parkingSpace;
	}

	@Override
	public List<ParkingSpace> getParkingSpaces() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = userDao.findById(Long.parseLong(username));
		ParkingZone parkingzone = parkingZoneDao.findByManager(user);

		List<ParkingSpace> parkingSpaces = parkingSpaceDao.findAll();

		List<ParkingSpace> returnParkingSpaces = new LinkedList<ParkingSpace>();

		for (ParkingSpace parkingSpace : parkingSpaces) {
			if (parkingSpace.getParkingZone() == parkingzone)
				returnParkingSpaces.add(parkingSpace);
		}
		return returnParkingSpaces;
	}

	@Override
	public Transaction disAllocatePlace(Long placeId) {
		// TODO Auto-generated method stud
		ZoneId zoneId = ZoneId.of("Asia/Kolkata");
		ParkingSpace parkingSpace = parkingSpaceDao.findById(placeId);
		LocalDateTime startDateTime = parkingSpace.getStartTime();
		LocalDateTime endDateTime = LocalDateTime.now(zoneId);
		Duration duration = Duration.between(startDateTime, endDateTime);
		long seconds = duration.getSeconds();
		BigDecimal secondsBigDecimal = BigDecimal.valueOf(seconds);
		// Convert seconds to hours as BigDecimal
		BigDecimal hours = secondsBigDecimal.divide(BigDecimal.valueOf(3600), 2, BigDecimal.ROUND_HALF_UP);
		Transaction transaction = new Transaction();
		transaction.setHistory(null);
		transaction.setUser(null);
		transaction.setStatus(false);
		transaction.setStartTransactionTime(endDateTime);
		transaction.setTotalCost(parkingSpace.getParkingZone().getParkingCost().multiply(hours));
		transactionDao.insert(transaction);
		transaction.setUser(parkingSpace.getUser());
		History history=new History();
		history.setPaidCompleteTime(endDateTime);
		history.setParkingZone(parkingSpace.getParkingZone());
		history.setStartTime(startDateTime);
		history.setUser(parkingSpace.getUser());
		history.setVehicleNumber(parkingSpace.getVehicleNumber());
		history.setVehicleType(parkingSpace.getVehicleType());
		transaction.setHistory(history);
		transaction.setDelayCost(new BigDecimal(0));
		transactionDao.update(transaction);
		parkingSpaceDao.delete(parkingSpace.getParkingSpaceId());
		return transaction;
	}
	
	@Override
	public Transaction getTransactionById(Long transactionId) {
		Transaction transaction = transactionDao.findById(transactionId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Retrieve username from UserDetails
		String username = userDetails.getUsername();

		// Retrieve the user from the database using the username if needed
		User user = userDao.findById(Long.parseLong(username));

		if (transaction != null && transaction.getHistory().getParkingZone().getParkingManager()==user) {
			
			System.out.println("ok");
			
			
			History history = transaction.getHistory();
			LocalDateTime startDateTime = transaction.getStartTransactionTime();
			ZoneId zoneId = ZoneId.of("Asia/Kolkata");
			LocalDateTime endDateTime = LocalDateTime.now(zoneId);
			Duration duration = Duration.between(startDateTime, endDateTime);
			long seconds = duration.getSeconds();
			BigDecimal secondsBigDecimal = BigDecimal.valueOf(seconds);
			// Convert seconds to hours as BigDecimal
			BigDecimal hours = secondsBigDecimal.divide(BigDecimal.valueOf(3600), 2, BigDecimal.ROUND_HALF_UP);
			
			transaction.setDelayCost(history.getParkingZone().getDelayCost().multiply(hours));
			transactionDao.update(transaction);
			
			return transaction;
			
		}
		return null;
	}

	@Override
	public Transaction payTransactionById(Long transactionId) {
		// TODO Auto-generated method stub
		Transaction transaction=getTransactionById(transactionId);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Retrieve username from UserDetails
		String username = userDetails.getUsername();

		// Retrieve the user from the database using the username if needed
		User user = userDao.findById(Long.parseLong(username));

		if (transaction != null && transaction.getHistory().getParkingZone().getParkingManager()==user) {
			
			if(transaction.isStatus()) return null; //if transaction status already true then simply return null
			transaction.setStatus(true);
			
			transaction.getHistory().setPaidCompleteTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
			transactionDao.update(transaction);
			return transaction;
		}
		
		return null;
	}

	@Override
	public List<Transaction> getAllTrancaTransactions() {
		// TODO Auto-generated method stub
		User user=getUser();
		
		List<Transaction> transactions=transactionDao.findAll();
		List<Transaction> requiredTransactions=new LinkedList<Transaction>();
		
		for(Transaction transaction:transactions) {
			if(transaction.getHistory().getParkingZone().getParkingManager()==user) {
				requiredTransactions.add(transaction);
			}
		}
		return requiredTransactions;
	}

	@Override
	public ParkingSpaceRemain getAllVehicles() {
		// TODO Auto-generated method stub
		User user=getUser();
		
		ParkingZone parkingzone = parkingZoneDao.findByManager(user);

		ParkingSpaceRemain parkingSpaceRemain = new ParkingSpaceRemain();
//		
		int numberOfAuto = 0;
		int numberOfCar = 0;
		int numberOfBike = 0;
		int numberOfTruck = 0;
//		
		List<ParkingSpace> parkingSpaces = parkingSpaceDao.findAll();
//		
		for (ParkingSpace parkingSpace : parkingSpaces) {
			if (parkingSpace.getParkingZone() == parkingzone) {
				if (parkingSpace.getVehicleType().equals("C"))
					numberOfCar++;
				else if (parkingSpace.getVehicleType().equals("B"))
					numberOfBike++;
				else if (parkingSpace.getVehicleType().equals("T"))
					numberOfTruck++;
				else if (parkingSpace.getVehicleType().equals("A"))
					numberOfAuto++;
			}
		}
//		
		parkingSpaceRemain.setNumberOfAutos( numberOfAuto);
		parkingSpaceRemain.setNumberOfBikes( numberOfBike);
		parkingSpaceRemain.setNumberOfCars(numberOfCar);
		parkingSpaceRemain.setNumberOfTrucks(numberOfTruck);
		return parkingSpaceRemain;
	}

	@Override
	public Profit getProfite() {
		// TODO Auto-generated method stub
		User user=getUser();
		BigDecimal profit = new BigDecimal("0");
		BigDecimal delayProfit = new BigDecimal("0");
		BigDecimal profitPending = new BigDecimal("0");
		BigDecimal delayProfitPending = new BigDecimal("0");

		List<Transaction> transactions = transactionDao.findAll();

		for (Transaction transaction : transactions) {
			if(transaction.getHistory().getParkingZone().getParkingManager()==user) {
				if (transaction.isStatus()) {
					profit = profit.add(transaction.getTotalCost());
					delayProfit = delayProfit.add(transaction.getDelayCost());
				} else {
					profitPending = profitPending.add(transaction.getTotalCost());
					delayProfitPending = delayProfitPending.add(transaction.getDelayCost());
				}
			}
				
		}
		Profit profitObj = new Profit();
		profitObj.setProfite(profit);
		profitObj.setProfiteForDelay(delayProfit);
		profitObj.setTotalProfite(profit.add(delayProfit));
		profitObj.setProfitePending(profitPending);
		profitObj.setProfiteForDelayPending(delayProfitPending);
		profitObj.setTotalProfitePending(profitPending.add(delayProfitPending));
		return profitObj;
	}

	@Override
	public List<History> getHistories() {
		// TODO Auto-generated method stub
		User user=getUser();
		List<History> requiredHistories=new LinkedList<History>();
		List<History> histories=historyDao.findAll();
		for(History history:histories) {
			if(history.getParkingZone().getParkingManager()==user) {
				
				requiredHistories.add(history);
			}
		}
		return requiredHistories;
	}
}
