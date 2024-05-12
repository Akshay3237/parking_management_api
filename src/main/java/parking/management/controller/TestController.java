package parking.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import parking.management.entity.User;
import parking.management.service.TestService;

@RestController
public class TestController {
	TestService testService;
	
	@Autowired
	public TestController(TestService testService) {
		this.testService = testService;
	}
	@CrossOrigin(origins="*")
	@GetMapping("/users")
	public List<User> findAll(){
		return testService.findAll();
	}
	@CrossOrigin(origins="*")
	@PostMapping("/users")
	public User createCustomer(@RequestBody User user) {
		testService.save(user);
		return user;
	}
	
	
	
}
