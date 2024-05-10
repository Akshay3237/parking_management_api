package parking.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import parking.management.entity.Roles;
import parking.management.service.CommonService;

@RestController
public class CommonController {
	
	final CommonService commonService;

	@Autowired
	public CommonController(CommonService commonService) {
		super();
		this.commonService = commonService;
	}
	
	@CrossOrigin(origins="*")
	@GetMapping("/roles")
	List<Roles> getRoles(){
		return commonService.getRoles();
	}
	
	
	
}
