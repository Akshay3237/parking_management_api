package parking.management.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import parking.management.dao.RolesDao;
import parking.management.dao.UserDao;
import parking.management.entity.Roles;
import parking.management.entity.RolesId;
import parking.management.entity.User;

@Service
public class TestService {
	UserDao userDao;
	RolesDao rolesDao;
	@Autowired
	public TestService(UserDao userDao,RolesDao rolesDao) {
		super();
		this.userDao = userDao;
		this.rolesDao=rolesDao;
	}

	public List<User> findAll(){
		return userDao.findAll();
	}
	
	@Transactional
	public User save(User user) {
		userDao.save(user);
		Roles role=new Roles(user, "ROLE_PARKINGMANAGER");
		rolesDao.insert(role);
		return user;
	}
}
