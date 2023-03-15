package telran.monitoring.service;

import telran.monitoring.model.*;

public interface AvgReducerService {
	
	Integer reduce(PulseProbe probe);

}
