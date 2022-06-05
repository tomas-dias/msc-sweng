package webapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import webapp.persistence.AddressRowDataGateway;
import webapp.persistence.CustomerFinder;
import webapp.persistence.CustomerRowDataGateway;
import webapp.persistence.PersistenceException;
import webapp.refactoring.NewCustomerService;
import webapp.services.AddressDTO;
import webapp.services.AddressesDTO;
import webapp.services.ApplicationException;
import webapp.services.CustomerDTO;
import webapp.services.CustomersDTO;

public class MocksBasedTest {
	
	// To be tested
	NewCustomerService cs;
	
	// Mock objects used by all tests (they will be injected below)
	@Mock CustomerRowDataGateway customer;
	@Mock CustomerFinder finder;
	@Mock AddressRowDataGateway address;
	
	@Spy CustomerRowDataGateway CUSTOMER_GATEWAY = 
			new CustomerRowDataGateway(CUSTOMER_ID, VAT, DESIGNATION, PHONE_NUMBER);
	
	// Literal values used in tests
	private static final int CUSTOMER_ID = 1;
	private static final int ADDRESS_ID = 1;
	private static final int VAT = 123456789;
	private static final int PHONE_NUMBER = 217500000;
	private static final String DESIGNATION = "FCUL";
	private static final String ADDR = "CAMPO GRANDE;016;1749-016;LISBOA";
	private static AddressRowDataGateway ADDRESS_GATEWAY;
	private static List<CustomerRowDataGateway> CUSTOMER_LIST;
	private static List<AddressRowDataGateway> ADDRESS_LIST;
	
	//Setup method executed before each  test
	@BeforeEach
	public void setup() throws PersistenceException {
		

		CUSTOMER_LIST = new ArrayList<CustomerRowDataGateway>();
		CUSTOMER_LIST.add(CUSTOMER_GATEWAY);
		
		ADDRESS_GATEWAY = new AddressRowDataGateway(ADDR, VAT);
		ADDRESS_LIST = new ArrayList<AddressRowDataGateway>();
		ADDRESS_LIST.add(ADDRESS_GATEWAY);
		
		// Instantiate all fields annotated with @Mock
		// no need to call Mockito.mock manually.
	    MockitoAnnotations.initMocks(this);
	    
	    when(finder.getCustomerByVATNumber(VAT)).thenReturn(CUSTOMER_GATEWAY);
	    doNothing().when(CUSTOMER_GATEWAY).setPhoneNumber(PHONE_NUMBER);
	    doNothing().when(CUSTOMER_GATEWAY).updatePhoneNumber();
	    doNothing().when(CUSTOMER_GATEWAY).removeCustomer();
	    when(customer.getAllCustomers()).thenReturn(CUSTOMER_LIST);
	    when(address.getId()).thenReturn(ADDRESS_ID);
	    when(address.getAddress()).thenReturn(ADDR);
	    when(address.getCustomerAddresses(VAT)).thenReturn(ADDRESS_LIST);
	    
	    cs = new NewCustomerService(customer, finder, address);
	    
	}
	
	@Test
	public void getCustomerTest() throws ApplicationException, PersistenceException {
		CustomerDTO customerDTO = cs.getCustomerByVat(VAT);
		
		assertEquals(CUSTOMER_ID, customerDTO.id);
		assertEquals(VAT, customerDTO.vat);
		assertEquals(DESIGNATION, customerDTO.designation);
		assertEquals(PHONE_NUMBER, customerDTO.phoneNumber);
		
		verify(finder, times(4)).getCustomerByVATNumber(VAT);
	}
	
	@Test
	public void addCustomerTest() throws ApplicationException, PersistenceException {
		cs.addCustomer(VAT);
		verify(customer, times(1)).insert();
	}
	
	@Test
	public void getAllCustomersTest() throws ApplicationException, PersistenceException {
		List<CustomerDTO> expected = new ArrayList<CustomerDTO>();
		expected.add(new CustomerDTO(CUSTOMER_ID, VAT, DESIGNATION, PHONE_NUMBER));
		
		CustomersDTO actual = cs.getAllCustomers();
		assertEquals(expected.size(), actual.customers.size());
		
		verify(customer, times(1)).getAllCustomers();
		
	}
	
	@Test
	public void addCustomerAddressTest() throws ApplicationException, PersistenceException {
		cs.addAddressToCustomer(VAT);
		verify(address, times(1)).insert();
	}
	
	@Test
	public void getAllAddressesTest() throws ApplicationException, PersistenceException {
		List<AddressDTO> expected = new ArrayList<AddressDTO>();
		expected.add(new AddressDTO(ADDRESS_ID, VAT, ADDR));
		
		AddressesDTO actual = cs.getAllAddresses(VAT);
		assertEquals(expected.size(), actual.addrs.size());
		
		verify(address, times(1)).getCustomerAddresses(VAT);
	}
	
	@Test
	public void updateCustomerPhoneTest() throws ApplicationException, PersistenceException {
		cs.updateCustomerPhone(VAT, PHONE_NUMBER);
		
		verify(finder, times(2)).getCustomerByVATNumber(VAT);
	}
	
	@Test
	public void removeCustomerTest() throws ApplicationException, PersistenceException {
		cs.removeCustomer(VAT);
		
		verify(finder, times(1)).getCustomerByVATNumber(VAT);
	}
}
