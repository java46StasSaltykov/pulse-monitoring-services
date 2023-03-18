package telra.monitoring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.*;
import telran.monitoring.model.*;
import telran.monitoring.repo.*;

@Service
@Transactional(readOnly = true)
public class DataProviderServiceImpl implements DataProviderService {
	
	VisitRepository visitRepository;
	PatientRepository patientRepository;
	DoctorRepository doctorRepository;
	EntityManager em;
	
	public DataProviderServiceImpl(VisitRepository visitRepository, EntityManager em) {
		this.visitRepository = visitRepository;
		this.em = em;
	}
	
	@Override
	public NotificationData getNotificationData(long patientId) {
		return new NotificationData
				(visitRepository.lastVisit(patientId).getEmail(), 
						visitRepository.lastVisit(patientId).getName(), 
						patientRepository.findPatient(patientId).getName());
	}

}
