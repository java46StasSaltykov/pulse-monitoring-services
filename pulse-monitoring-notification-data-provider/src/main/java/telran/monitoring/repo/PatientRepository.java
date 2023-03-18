package telran.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import telran.monitoring.entities.*;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	@Query(value = "select patient from patients where patients.id = :patientId", nativeQuery = true)
	Patient findPatient(long patientId);

}
