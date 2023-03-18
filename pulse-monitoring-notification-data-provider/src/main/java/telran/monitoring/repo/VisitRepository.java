package telran.monitoring.repo;

import org.springframework.data.jpa.repository.*;
import telran.monitoring.entities.*;

public interface VisitRepository extends JpaRepository<Visit, Long>  {
	
	@Query(value = "select doctor from doctors join visits on doctors.email = visits.email"
			+ " join patients on visits.id = patients.id where patient.id = :patientId order by date desc limit 1", nativeQuery = true)
	Doctor lastVisit(long patientId);

}
