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
import parking.management.entity.ParkingZoneViewForClient;
import parking.management.entity.Transaction;

@Service
public class ServiceForClientImpl implements ServiceForClient {
	final private UserDao userDao;
	final private ParkingSpaceDao parkingSpaceDao;
	final private ParkingZoneDao parkingZoneDao;
	final private HistoryDao historyDao;
	final private RolesDao rolesDao;
	final private TransactionDao transactionDao;

	@Autowired
	public ServiceForClientImpl(UserDao userDao, ParkingSpaceDao parkingSpaceDao, ParkingZoneDao parkingZoneDao,
			HistoryDao historyDao, RolesDao rolesDao, TransactionDao transactionDao) {
		super();
		this.userDao = userDao;
		this.parkingSpaceDao = parkingSpaceDao;
		this.parkingZoneDao = parkingZoneDao;
		this.historyDao = historyDao;
		this.rolesDao = rolesDao;
		this.transactionDao = transactionDao;
	}

	@Override
	public Transaction getTransactionById(Long transactionId) {
		Transaction transaction = transactionDao.findById(transactionId);
		if (transaction != null) {
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
		return transaction;
	}

	@Override
	public List<ParkingZoneViewForClient> getViews() {
		// TODO Auto-generated method stub
		List<ParkingZone> parkingZones=parkingZoneDao.findAll();
		
		List<ParkingZoneViewForClient> parkingZoneViewForClients=new LinkedList<ParkingZoneViewForClient>();
		for(ParkingZone parkingZone:parkingZones) {
			parkingZoneViewForClients.add(new ParkingZoneViewForClient(parkingZone.getParkingZoneId(), parkingZone.getParkingZoneName()));
		}
		
		return parkingZoneViewForClients;
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

		ParkingZone parkingZone=parkingZoneDao.findById(parkingZoneId.intValue());
		ParkingSpaceRemain parkingSpaceRemain = new ParkingSpaceRemain();
		parkingSpaceRemain.setNumberOfAutos(parkingZone.getNumberOfAuto()-numberOfAuto);
		parkingSpaceRemain.setNumberOfBikes(parkingZone.getNumberOfBike()-numberOfBike);
		parkingSpaceRemain.setNumberOfCars(parkingZone.getNumberOfCar()-numberOfCars);
		parkingSpaceRemain.setNumberOfTrucks(parkingZone.getNumberOfTruck()-numberOfTruck);
		return parkingSpaceRemain;

	}


}
