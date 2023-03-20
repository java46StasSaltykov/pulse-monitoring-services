package telran.monitoring.service;

import telran.monitoring.model.*;

public interface DataProviderService {
	
	NotificationData getNotificationData(long patientId);

}
