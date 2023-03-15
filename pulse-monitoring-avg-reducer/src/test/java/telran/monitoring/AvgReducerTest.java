package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.*;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import telran.monitoring.model.*;
import telran.monitoring.service.AvgReducerService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AvgReducerTest {
	
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	@MockBean
	AvgReducerService service;
	
	PulseProbe probeAvg = new PulseProbe(1, System.currentTimeMillis(), 0, 80);
	PulseProbe probeNoAvg = new PulseProbe();
	
	@BeforeEach
	void mockingService() {
		when(service.reduce(probeAvg)).thenReturn(80);
		when(service.reduce(probeNoAvg)).thenReturn(null);
	}

	@Test
	void contextLoads() {
		
	}
	
	@Test
	void receivingProbNoAvg() {
		producer.send(new GenericMessage<PulseProbe>(probeNoAvg), "pulseProbeConsumer-in-0");
		Message<byte[]> message = consumer.receive(10, "average-out-0");
		assertNull(message);
	}
	
	@Test
	void receivingProbAvg() throws Exception {
		producer.send(new GenericMessage<PulseProbe>(probeAvg), "pulseProbeConsumer-in-0");
		Message<byte[]> message = consumer.receive(10, "average-out-0");
		assertNotNull(message);
		ObjectMapper mapper = new ObjectMapper();
		PulseProbe probe = mapper.readValue(message.getPayload(), PulseProbe.class);
		assertEquals(probeAvg, probe);
	}

}
