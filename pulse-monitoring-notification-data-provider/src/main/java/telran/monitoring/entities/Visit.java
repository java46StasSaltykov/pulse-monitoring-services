package telran.monitoring.entities;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "visits")
public class Visit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@ManyToOne
	@JoinColumn
	Doctor doctor;
	
	@ManyToOne
	@JoinColumn
	Patient patient;

	LocalDate date;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public Patient getPatient() {
		return patient;
	}

}
