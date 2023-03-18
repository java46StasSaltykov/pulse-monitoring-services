package telran.monitoring.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {
	
	@Id
	@Column(name = "id")
	long id;
	
	@Column(name = "name")
	String name;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
	List<Visit> visits;

	public Patient(long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Patient() {}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
