package vvs_dbsetup;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static vvs_dbsetup.DBSetupUtils.DB_PASSWORD;
import static vvs_dbsetup.DBSetupUtils.DB_URL;
import static vvs_dbsetup.DBSetupUtils.DB_USERNAME;
import static vvs_dbsetup.DBSetupUtils.DELETE_ALL;
import static vvs_dbsetup.DBSetupUtils.INSERT_CUSTOMER_ADDRESS_DATA;
import static vvs_dbsetup.DBSetupUtils.INSERT_CUSTOMER_SALE_DATA;
import static vvs_dbsetup.DBSetupUtils.INSERT_CUSTOMER_ALL_DATA;
import static vvs_dbsetup.DBSetupUtils.startApplicationDatabaseForTesting;

import java.sql.SQLException;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import webapp.services.AddressesDTO;
import webapp.services.ApplicationException;
import webapp.services.CustomerDTO;
import webapp.services.CustomerService;
import webapp.services.CustomersDTO;
import webapp.services.SaleDTO;
import webapp.services.SaleService;
import webapp.services.SalesDTO;
import webapp.services.SalesDeliveryDTO;

public class DBBasedTest {

	private static Destination dataSource;
	
    // the tracker is static because JUnit uses a separate Test instance for every test method.
    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
    @BeforeAll
    public static void setupClass() {
    	startApplicationDatabaseForTesting();
		dataSource = DriverManagerDestination.with(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
	@BeforeEach
	public void setup() throws SQLException {

		Operation initDBOperations = Operations.sequenceOf(
			  DELETE_ALL
			, INSERT_CUSTOMER_ALL_DATA
			);
		
		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);
		
        // Use the tracker to launch the DBSetup. This will speed-up tests 
		// that do not change the DB. Otherwise, just use dbSetup.launch();
        dbSetupTracker.launchIfNecessary(dbSetup);
		
	}
	
	private boolean hasClient(int vat) throws ApplicationException {	
		CustomersDTO customersDTO = CustomerService.INSTANCE.getAllCustomers();
		
		for(CustomerDTO customer : customersDTO.customers)
			if (customer.vat == vat)
				return true;			
		return false;
	}
	
	@Disabled
	@Test
	public void addNewClientWithAnExistingVATTest() throws ApplicationException {
		assertTrue(hasClient(197672337));
		assertThrows(ApplicationException.class, () -> {
			CustomerService.INSTANCE.addCustomer(197672337, "FCUL", 217500000);
		});
	}
	
	@Disabled
	@Test
	public void updateClientContactTest() throws ApplicationException {
		assertTrue(hasClient(197672337));
		CustomerService.INSTANCE.updateCustomerPhone(197672337, 999999999);
		
		CustomerDTO customerDTO = CustomerService.INSTANCE.getCustomerByVat(197672337);
		assertEquals(999999999, customerDTO.phoneNumber);
	}
	
	@Disabled
	@Test
	public void deleteAllClientsTest() throws ApplicationException {
		CustomersDTO customersDTO = CustomerService.INSTANCE.getAllCustomers();
		assumeTrue(customersDTO.customers.size() != 0);
		
		for(CustomerDTO customer : customersDTO.customers)
			CustomerService.INSTANCE.removeCustomer(customer.vat);
		
		assertEquals(0, CustomerService.INSTANCE.getAllCustomers().customers.size());
	}
	
	@Disabled
	@Test
	public void addNewSaleTest() throws ApplicationException {
		SalesDTO salesDTO = SaleService.INSTANCE.getAllSales();
		int salesSize = salesDTO.sales.size();
		
		assertTrue(hasClient(197672337));
		SaleService.INSTANCE.addSale(197672337);
		
		assertEquals(salesSize + 1, SaleService.INSTANCE.getAllSales().sales.size());
	}
	
	@Disabled
	@Test
	public void updateSaleTest() throws ApplicationException {
		SalesDTO salesDTO = SaleService.INSTANCE.getAllSales();
		assumeTrue(salesDTO.sales.size() != 0);
		
		SaleDTO saleDTO = salesDTO.sales.get(0);
		SaleService.INSTANCE.updateSale(saleDTO.id);
		
		salesDTO = SaleService.INSTANCE.getAllSales();
		saleDTO = salesDTO.sales.get(0);
		
		assertEquals("C", saleDTO.statusId);
	}
	
	@Test
	public void addSaleDeliveryTest() throws ApplicationException {
		assertTrue(hasClient(197672337));
		SalesDTO salesDTO = SaleService.INSTANCE.getSaleByCustomerVat(197672337);
		assumeTrue(salesDTO.sales.size() != 0);
		SalesDeliveryDTO salesDeliveryDTO = SaleService.INSTANCE.getSalesDeliveryByVat(197672337);
		assumeTrue(salesDeliveryDTO.sales_delivery.size() == 0);
		
		AddressesDTO addressesDTO = CustomerService.INSTANCE.getAllAddresses(197672337);
		SaleService.INSTANCE.addSaleDelivery(salesDTO.sales.get(0).id, addressesDTO.addrs.get(0).id);
		
		assertEquals(1, SaleService.INSTANCE.getSalesDeliveryByVat(197672337).sales_delivery.size());
	}
}
