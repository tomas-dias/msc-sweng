package webapp;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import webapp.persistence.AddressRowDataGateway;
import webapp.persistence.PersistenceException;
import webapp.refactoring.NewCustomerService;
import webapp.services.ApplicationException;

public class MocksBasedTest {
	
	// To be tested
	NewCustomerService cs;
	
	// Mock objects used by all tests (they will be injected below)
	@Mock AddressRowDataGateway address;
	
	// Literal values used in tests
	private static final int VAT = 123456789;
	private static final String ADDR = "CAMPO GRANDE;016;1749-016;LISBOA";
	
	//Setup method executed before each  test
	@BeforeEach
	public void setup() {
		// Instantiate all fields annotated with @Mock
		// no need to call Mockito.mock manually.
	    MockitoAnnotations.initMocks(this);
	    cs = new NewCustomerService(address);
	    
	    
	}
	
	@Test
	public void addCustomerAddress() throws ApplicationException, PersistenceException {
		cs.addAddressToCustomer(VAT, ADDR);
		verify(address).insert();
	}
}
