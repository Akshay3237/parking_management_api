package parking.management.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class ServiceForAdminImpl implements ServiceForAdmin {

	final private UserDao userDao;
	final private ParkingSpaceDao parkingSpaceDao;
	final private ParkingZoneDao parkingZoneDao;
	final private HistoryDao historyDao;
	final private RolesDao rolesDao;
	final private TransactionDao transactionDao;

	@Autowired
	public ServiceForAdminImpl(UserDao userDao, ParkingSpaceDao parkingSpaceDao, ParkingZoneDao parkingZoneDao,
			HistoryDao historyDao, RolesDao rolesDao, TransactionDao transactionDao) {
		super();
		this.userDao = userDao;
		this.parkingSpaceDao = parkingSpaceDao;
		this.parkingZoneDao = parkingZoneDao;
		this.historyDao = historyDao;
		this.rolesDao = rolesDao;
		this.transactionDao = transactionDao;
	}

	// link for create manager

	@Override
	public User createManager(User user) {
		userDao.save(user);
		Roles role = new Roles(user, "ROLE_PARKINGMANAGER");
		rolesDao.insert(role);
		return user;
	}

	@Override
	public List<User> getAllUsers() {

		return userDao.findAll();
	}

	@Override
	public List<ParkingZone> getAllParkingZones() {
		// TODO Auto-generated method stub
		return parkingZoneDao.findAll();
	}

	@Override
	public ParkingZone createParkingZone(ParkingZone parkingZone) {
		// TODO Auto-generated method stub
		parkingZoneDao.insert(parkingZone);
		Roles roles = new Roles();
		roles.setUser(parkingZone.getParkingManager());
		roles.setRole("ROLE_PARKINGMANAGER");
		rolesDao.insert(roles);
		return parkingZone;
	}

	@Override
	public ParkingSpaceRemain totalVehicle() {
		// TODO Auto-generated method stub
		int numberOfCars = 0;
		int numberOfAuto = 0;
		int numberOfTruck = 0;
		int numberOfBike = 0;
		List<ParkingSpace> parkingSpaces = parkingSpaceDao.findAll();

		for (ParkingSpace parkingSpace : parkingSpaces) {
			if (parkingSpace.getVehicleType().equals("C"))
				numberOfCars++;
			else if (parkingSpace.getVehicleType().equals("B"))
				numberOfBike++;
			else if (parkingSpace.getVehicleType().equals("T"))
				numberOfTruck++;
			else if (parkingSpace.getVehicleType().equals("A"))
				numberOfAuto++;
		}
		ParkingSpaceRemain parkingSpaceRemain = new ParkingSpaceRemain();
		parkingSpaceRemain.setNumberOfAutos(numberOfAuto);
		parkingSpaceRemain.setNumberOfBikes(numberOfBike);
		parkingSpaceRemain.setNumberOfCars(numberOfCars);
		parkingSpaceRemain.setNumberOfTrucks(numberOfTruck);
		return parkingSpaceRemain;
	}

	@Override
	public ParkingSpaceRemain totalVehicleByParkingId(Long parkingZoneId) {
		// TODO Auto-generated method stub
		int numberOfCars = 0;
		int numberOfAuto = 0;
		int numberOfTruck = 0;
		int numberOfBike = 0;
		List<ParkingSpace> parkingSpaces = parkingSpaceDao.findAll();

		for (ParkingSpace parkingSpace : parkingSpaces) {
			if (parkingSpace.getParkingZone().getParkingZoneId().longValue() == parkingZoneId) {
				if (parkingSpace.getVehicleType().equals("C"))
					numberOfCars++;
				else if (parkingSpace.getVehicleType().equals("B"))
					numberOfBike++;
				else if (parkingSpace.getVehicleType().equals("T"))
					numberOfTruck++;
				else if (parkingSpace.getVehicleType().equals("A"))
					numberOfAuto++;
			}

		}

		ParkingSpaceRemain parkingSpaceRemain = new ParkingSpaceRemain();
		parkingSpaceRemain.setNumberOfAutos(numberOfAuto);
		parkingSpaceRemain.setNumberOfBikes(numberOfBike);
		parkingSpaceRemain.setNumberOfCars(numberOfCars);
		parkingSpaceRemain.setNumberOfTrucks(numberOfTruck);
		return parkingSpaceRemain;

	}


	@Override
	public Profit getProfite() {
		// TODO Auto-generated method stub
		BigDecimal profit = new BigDecimal("0");
		BigDecimal delayProfit = new BigDecimal("0");
		BigDecimal profitPending = new BigDecimal("0");
		BigDecimal delayProfitPending = new BigDecimal("0");

		List<Transaction> transactions = transactionDao.findAll();

		for (Transaction transaction : transactions) {
			if (transaction.isStatus()) {
				profit = profit.add(transaction.getTotalCost());
				delayProfit = delayProfit.add(transaction.getDelayCost());
			} else {
				profitPending = profitPending.add(transaction.getTotalCost());
				delayProfitPending = delayProfitPending.add(transaction.getDelayCost());
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
	public Profit getProfiteByParkingZoneId(Long parkingZoneId) {
		// TODO Auto-generated method stub
		BigDecimal profit = new BigDecimal("0");
		BigDecimal delayProfit = new BigDecimal("0");
		BigDecimal profitPending = new BigDecimal("0");
		BigDecimal delayProfitPending = new BigDecimal("0");

		List<Transaction> transactions = transactionDao.findAll();

		for (Transaction transaction : transactions) {
			if(transaction.getHistory().getParkingZone().getParkingZoneId().longValue()==parkingZoneId) {
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
		return historyDao.findAll();
	}

	@Override
	public List<History> getHistoriesByParkingZoneId(Long parkingZoneId) {
		// TODO Auto-generated method stub
		List<History> requiredHistories=new LinkedList<History>();
		List<History> histories=historyDao.findAll();
		for(History history:histories) {
			if(history.getParkingZone().getParkingZoneId().longValue()==parkingZoneId) {
				
				requiredHistories.add(history);
			}
		}
		return requiredHistories;
	}

	@Override
	public List<Transaction> getTransactions() {
		// TODO Auto-generated method stub
		return transactionDao.findAll();
	}

	@Override
	public List<Transaction> getTransactionsByParkingZoneId(Long parkingZoneId) {
		// TODO Auto-generated method stub
		List<Transaction> requTransactions=new LinkedList<Transaction>();
		List<Transaction> transactions=transactionDao.findAll();
		for(Transaction transaction:transactions) {
			if(transaction.getHistory().getParkingZone().getParkingZoneId().longValue()==parkingZoneId) {
				requTransactions.add(transaction);
			}
		}
		return requTransactions;
	}

	@Override
	public List<User> getParkingManagers() {
		// TODO Auto-generated method stub
		List<User> parkingManagers=new LinkedList<User>();
		List<ParkingZone> parkingZones=parkingZoneDao.findAll();
		for(ParkingZone parkingZone:parkingZones) {
			parkingManagers.add(parkingZone.getParkingManager());
		}
		return parkingManagers;
	}

	@Override
	public List<User> getAllClients() {
		// TODO Auto-generated method stub
		List<ParkingSpace> parkingSpaces=parkingSpaceDao.findAll();
		Set<User> clientSet = new HashSet<User>();
		for(ParkingSpace parkingSpace:parkingSpaces) {
			clientSet.add(parkingSpace.getUser());
		}
		return new LinkedList<User>(clientSet);
	}

	@Override
	public List<User> getClientByParkingZoneId(Long parkingZoneId) {
		List<ParkingSpace> parkingSpaces=parkingSpaceDao.findAll();
		Set<User> clientSet = new HashSet<User>();
		for(ParkingSpace parkingSpace:parkingSpaces) {
			if(parkingSpace.getParkingZone().getParkingZoneId().longValue()==parkingZoneId)
			clientSet.add(parkingSpace.getUser());
		}
		return new LinkedList<User>(clientSet);
	}



}
