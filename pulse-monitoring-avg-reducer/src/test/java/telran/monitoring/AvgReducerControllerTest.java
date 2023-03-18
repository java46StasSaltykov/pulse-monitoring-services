package telran.monitoring;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.IOException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.*;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.*;
import telran.monitoring.model.*;
import telran.monitoring.service.*;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class AvgReducerControllerTest {
	
	private static final long PATIENT_ID_NO_AVG = 123;
	private static final int VALUE = 100;
	private static final long PATIENT_ID_AVG = 125;

	@MockBean
	AvgReducerService service;
	
	@Autowired
	InputDestination producer;
	
	@Autowired
	OutputDestination consumer;
	
	PulseProbe probeNoAvg = new PulseProbe(PATIENT_ID_NO_AVG, 0, 0, VALUE);
	PulseProbe probeAvgExpected = new PulseProbe(PATIENT_ID_AVG, 0, 0, VALUE);

	String bindingNameProducer = "pulseProbeConsumer-in-0";
	
	@Value("${app.avg.binding.name}")
	String bindingNameConsumer;

	@BeforeEach
	void mockingService() {
		when(service.reduce(probeAvgExpected)).thenReturn(VALUE);
		when(service.reduce(probeNoAvg)).thenReturn(null);
	}

	@Test
	void receivingProbNoAvg() {
		producer.send(new GenericMessage<PulseProbe>(probeNoAvg), bindingNameProducer);
		Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
		assertNull(message);
	}

	@Test
	void receivingProbAvg() throws StreamReadException, DatabindException, IOException {
		producer.send(new GenericMessage<PulseProbe>(probeAvgExpected), bindingNameProducer);
		Message<byte[]> message = consumer.receive(10, bindingNameConsumer);
		assertNotNull(message);
		ObjectMapper mapper = new ObjectMapper();
		PulseProbe probeAvgActual = mapper.readValue(message.getPayload(), PulseProbe.class);
		assertEquals(probeAvgExpected, probeAvgActual);
	}

}