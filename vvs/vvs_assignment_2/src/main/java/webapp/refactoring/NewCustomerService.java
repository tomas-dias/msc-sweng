package webapp.refactoring;

import java.util.ArrayList;
import java.util.List;

import webapp.persistence.AddressRowDataGateway;
import webapp.persistence.CustomerFinder;
import webapp.persistence.CustomerRowDataGateway;
import webapp.persistence.PersistenceException;
import webapp.services.AddressDTO;
import webapp.services.AddressesDTO;
import webapp.services.ApplicationException;
import webapp.services.CustomerDTO;
import webapp.services.CustomersDTO;


/**
 * Handles customer transactions. 
 * Each public method implements a transaction script.
 * 
 * @author 
 * @version
 *
 */
public class NewCustomerService {
	
	private CustomerRowDataGateway customer;
	private CustomerFinder finder;
	private AddressRowDataGateway address;
	
	public NewCustomerService(
			CustomerRowDataGateway customer,
			CustomerFinder finder,
			AddressRowDataGateway address) {
		this.customer = customer;
		this.finder = finder;
		this.address = address;
	}
	
	public CustomerDTO getCustomerByVat (int vat) throws ApplicationException, PersistenceException {
		if (!isValidVAT (vat))
			throw new ApplicationException ("Invalid VAT number: " + vat);
		else {
			return new CustomerDTO(
					finder.getCustomerByVATNumber(vat).getCustomerId(), 
					finder.getCustomerByVATNumber(vat).getVAT(), 
					finder.getCustomerByVATNumber(vat).getDesignation(),
					finder.getCustomerByVATNumber(vat).getPhoneNumber());
		}
	}
	
	public void addCustomer(int vat) throws ApplicationException {
		if (!isValidVAT (vat))
			throw new ApplicationException ("Invalid VAT number: " + vat);
		else try {
			customer.insert();
		} catch (PersistenceException e) {
				throw new ApplicationException ("Can't add customer with vat number " + vat + ".", e);
		}
	}
	
	public CustomersDTO getAllCustomers() throws ApplicationException {
		try {
			List<CustomerRowDataGateway> customers = customer.getAllCustomers();
			List<CustomerDTO> list = new ArrayList<CustomerDTO>();
			for(CustomerRowDataGateway cust : customers) {
				list.add(new CustomerDTO(cust.getCustomerId(), cust.getVAT(), 
					cust.getDesignation(), cust.getPhoneNumber()));
			}
			CustomersDTO c = new CustomersDTO(list);
			return c;
		} catch (PersistenceException e) {
				throw new ApplicationException ("Error getting all customers", e);
		}
	}
	
	public void addAddressToCustomer(int customerVat) throws ApplicationException {
		if (!isValidVAT (customerVat))
			throw new ApplicationException ("Invalid VAT number: " + customerVat);
		else try {
			address.insert();
			
		} catch (PersistenceException e) {
				throw new ApplicationException ("Can't add the address /n" + address.getAddress() + "/nTo customer with vat number " + customerVat + ".", e);
		}
	}
	
	public AddressesDTO getAllAddresses(int customerVat) throws ApplicationException {
		try {
			List<AddressRowDataGateway> addrs = address.getCustomerAddresses(customerVat);
			List<AddressDTO> list = new ArrayList<>();
			for(AddressRowDataGateway addr : addrs) {
				list.add(new AddressDTO(addr.getId(), addr.getCustVat(), addr.getAddress()));
			}
			AddressesDTO c = new AddressesDTO(list);
			return c;
		} catch (PersistenceException e) {
				throw new ApplicationException ("Error getting all customers", e);
		}
	}
	
	

	public void updateCustomerPhone(int vat, int phoneNumber) throws ApplicationException {
		if (!isValidVAT (vat))
			throw new ApplicationException ("Invalid VAT number: " + vat);
		else try {
			finder.getCustomerByVATNumber(vat).setPhoneNumber(phoneNumber);;
			finder.getCustomerByVATNumber(vat).updatePhoneNumber();
		} catch (PersistenceException e) {
				throw new ApplicationException ("Customer with vat number " + vat + " not found.", e);
		}
	}
	
	public void removeCustomer(int vat) throws ApplicationException {
		if (!isValidVAT (vat))
			throw new ApplicationException ("Invalid VAT number: " + vat);
		else try {
			finder.getCustomerByVATNumber(vat).removeCustomer();
		} catch (PersistenceException e) {
				throw new ApplicationException ("Customer with vat number " + vat + " doesn't exist.", e);
		}
	}

	 
	/**
	 * Checks if a VAT number is valid.
	 * 
	 * @param vat The number to be checked.
	 * @return Whether the VAT number is valid. 
	 */
	private boolean isValidVAT(int vat) {
		// If the number of digits is not 9, error!
		if (vat < 100000000 || vat > 999999999)
			return false;
		
		// If the first number is not 1, 2, 5, 6, 8, 9, error!
		int firstDigit = vat / 100000000;
		if (firstDigit != 1 && firstDigit != 2 && 
			firstDigit != 5 && firstDigit != 6 &&
			firstDigit != 8 && firstDigit != 9)
			return false;
		
		// Checks the congruence modules 11.
		int sum = 0;
		int checkDigit = vat % 10;
		vat /= 10;
		
		for (int i = 2; i < 10 && vat != 0; i++) {
			sum += vat % 10 * i;
			vat /= 10;
		}
		
		int checkDigitCalc = 11 - sum % 11;
		if (checkDigitCalc == 10)
			checkDigitCalc = 0;
		return checkDigit == checkDigitCalc;
	}


}
