package telran.monitoring.repo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import telran.monitoring.entities.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
	
	List<Visit> findByPatientId(long id);

	List<Visit> findByDateBetweenAndPatientId(LocalDate from, LocalDate to, long patientId);

}