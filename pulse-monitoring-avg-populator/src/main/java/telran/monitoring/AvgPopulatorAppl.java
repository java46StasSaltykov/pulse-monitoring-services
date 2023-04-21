package telran.monitoring;

import java.util.function.Consumer;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import telran.monitoring.entities.AvgPulseDoc;
import telran.monitoring.model.PulseProbe;
import telran.monitoring.repo.AvgPulseRepository;

@SpringBootApplication
public class AvgPopulatorAppl {
	
	@Autowired
	AvgPulseRepository avgPulseRepository;
	
	static Logger LOG = LoggerFactory.getLogger(AvgPopulatorAppl.class);

	public static void main(String[] args) {
		SpringApplication.run(AvgPopulatorAppl.class, args);
	}
	
	@Bean
	Consumer<PulseProbe> avgPulseConsumer() {
		return this::getAvgPulseConsumer;
	}
	
	void getAvgPulseConsumer(PulseProbe pulseProbe) {
		LOG.trace("received pulse probe of patient {}", pulseProbe.patientId);
		AvgPulseDoc pulseDoc = AvgPulseDoc.of(pulseProbe);
		avgPulseRepository.save(pulseDoc);
	}

}
