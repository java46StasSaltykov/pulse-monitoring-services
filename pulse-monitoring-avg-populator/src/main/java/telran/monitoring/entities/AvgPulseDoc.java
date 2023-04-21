package telran.monitoring.entities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Document;
import telran.monitoring.model.PulseProbe;

@Document(collection = "documents")
public class AvgPulseDoc {
	
	long patientId;
	LocalDateTime dateTime;
	int value;
	
	private AvgPulseDoc(long patientId, LocalDateTime dateTime, int value) {
		this.patientId = patientId;
		this.dateTime = dateTime;
		this.value = value;
	}
	
	public static AvgPulseDoc of(PulseProbe pulseProbe) {
		return new AvgPulseDoc
				(pulseProbe.patientId, 
						LocalDateTime.ofInstant(Instant.ofEpochMilli(pulseProbe.timestamp), 
								ZoneId.systemDefault()), pulseProbe.value);
	}

	public long getPatientId() {
		return patientId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public int getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(patientId, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvgPulseDoc other = (AvgPulseDoc) obj;
		return patientId == other.patientId && value == other.value;
	}

}
