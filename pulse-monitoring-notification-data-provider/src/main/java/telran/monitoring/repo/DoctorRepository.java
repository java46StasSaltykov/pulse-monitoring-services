package telran.monitoring.repo;

import org.springframework.data.jpa.repository.*;
import telran.monitoring.entities.*;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

}
