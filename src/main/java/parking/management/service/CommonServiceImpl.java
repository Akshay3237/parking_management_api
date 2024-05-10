package parking.management.service;

import java.util.List;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import parking.management.dao.HistoryDao;
import parking.management.dao.ParkingSpaceDao;
import parking.management.dao.ParkingZoneDao;
import parking.management.dao.RolesDao;
import parking.management.dao.TransactionDao;
import parking.management.dao.UserDao;
import parking.management.entity.Roles;

@Service
public class CommonServiceImpl implements CommonService {

	final private UserDao userDao;
	final private ParkingSpaceDao parkingSpaceDao;
	final private ParkingZoneDao parkingZoneDao;
	final private HistoryDao historyDao;
	final private RolesDao rolesDao;
	final private TransactionDao transactionDao;

	
	
	
	@Autowired
	public CommonServiceImpl(UserDao userDao, ParkingSpaceDao parkingSpaceDao, ParkingZoneDao parkingZoneDao,
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
	public List<Roles> getRoles() {
		// TODO Auto-generated method stub
		return rolesDao.findAll();
	}

}
