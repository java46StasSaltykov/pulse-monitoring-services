package telran.monitoring.entities;

import java.util.*;
import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {
	
	@Id
	@Column(name = "email")
	String email;
	
	@Column(name = "name")
	String name;
	
	@OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE)
	List<Visit> visits;
	
	public Doctor(String email, String name) {
		this.email = email;
		this.name = name;
	}
	
	public Doctor() {}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}
	
}
