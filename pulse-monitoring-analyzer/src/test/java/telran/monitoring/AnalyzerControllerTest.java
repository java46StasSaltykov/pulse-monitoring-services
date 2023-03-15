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
import telran.monitoring.service.AnalyzerService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AnalyzerControllerTest {
	
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	@MockBean
	AnalyzerService service;
	
	PulseProbe probeNoJump = new PulseProbe (1, 0, 0, 70);
	PulseProbe probeJump = new PulseProbe (2, 0, 0, 200);
	PulseJump pulseJump = new PulseJump(2, 100, 200);
	
	@BeforeEach
	void mockingService() {
		when(service.processPulseProbe(probeJump)).thenReturn(pulseJump);
		when(service.processPulseProbe(probeNoJump)).thenReturn(null);
	}

	@Test
	void contextLoads() {
		
	}
	
	@Test
	void receivingProbNoJump() {
		producer.send(new GenericMessage<PulseProbe>(probeNoJump), "pulseProbeConsumer-in-0");
		Message<byte[]> message = consumer.receive(10, "jumps-out-0");
		assertNull(message);
	}
	
	@Test
	void receivingProbJump() throws Exception {
		producer.send(new GenericMessage<PulseProbe>(probeJump), "pulseProbeConsumer-in-0");
		Message<byte[]> message = consumer.receive(10, "jumps-out-0");
		assertNotNull(message);
		ObjectMapper mapper = new ObjectMapper();
		PulseJump jump = mapper.readValue(message.getPayload(), PulseJump.class);
		assertEquals(pulseJump, jump);
	}

}
