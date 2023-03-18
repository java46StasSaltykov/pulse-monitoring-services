package telra.monitoring.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import telra.monitoring.service.*;

@RestController
@RequestMapping("visits")
public class VisitController {
	
	@Autowired
	DataProviderService service;
	
	@GetMapping("last/visit")
	String getLastVisit(@RequestParam(name = "id") long patientId) {
		return service.getNotificationData(patientId).toString();
	}

}
