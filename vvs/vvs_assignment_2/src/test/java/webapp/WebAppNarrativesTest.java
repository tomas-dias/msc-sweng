package webapp;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class WebAppNarrativesTest {

	private static final String APPLICATION_URL = "http://localhost:8080/VVS_assignment_2/";
	private static HtmlPage page;
	
	@BeforeAll
	public static void setUpClass() throws Exception {
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
	
			// possible configurations needed to prevent JUnit tests to fail for complex HTML pages
            webClient.setJavaScriptTimeout(15000);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
		    
			page = webClient.getPage(APPLICATION_URL);
			assertEquals(200, page.getWebResponse().getStatusCode()); // OK status
		}
	}
	
	/**
	 * 2.a)
	 * 
	 * Testing:
	 * 
	 * Insert a new address for an existing customer, 
	 * then the table of addresses of that client includes those addresses and its total row size increases by one.
	 * 
	 * @throws IOException
	 */
	
	//@Disabled
	@Test
	public void insertCustomerAddressTest() throws IOException {
		final String NPC = "197672337";
		
		// Customer address data
		final String ADDRESS = "CAMPO GRANDE";
		final String DOOR = "016";
		final String POSTAL_CODE = "1749-016";
		final String LOCALITY = "LISBOA";
		
		HtmlPage nextPage;
		HtmlTable table;
		WebRequest req;
		String textNextPage;
		String xpath;
		int tableSize;
		int newTableSize;
		
		// Check that customer exists
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		assertTrue(nextPage.asText().contains(NPC));
		
		// Check customer related data 
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetCustomerPageController"), HttpMethod.GET);
		req.setRequestParameters(asList(new NameValuePair("vat", NPC)));
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage); // check if is the next page
		
		// Xpath for table
		xpath = "/html/body//table";
		
		// Get customer addresses table size 
		tableSize = 0;
		if(nextPage.getByXPath(xpath).size() != 0) {
			table = (HtmlTable) nextPage.getByXPath(xpath).get(0);
			tableSize = table.getRowCount() - 1; // without header
		}
		
		// Place customer address data at form
		String formData = String.format("vat=%s&address=%s&door=%s&postalCode=%s&locality=%s", 
				NPC, ADDRESS, DOOR, POSTAL_CODE, LOCALITY);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetCustomerPageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage); // check if is the next page
		
		assertTrue(textNextPage.contains(ADDRESS));
		assertTrue(textNextPage.contains(DOOR));
		assertTrue(textNextPage.contains(POSTAL_CODE));
		assertTrue(textNextPage.contains(LOCALITY));
		
		// Get new customer addresses table size
		table = (HtmlTable) nextPage.getByXPath(xpath).get(0);
		newTableSize = table.getRowCount() - 1;
		
		assertEquals(tableSize + 1, newTableSize);
	}
	
	/**
	 * 2.b)
	 * 
	 * Testing:
	 * 
	 * A new sale will be listed as an open sale for the respective customer.
	 * 
	 * @throws IOException
	 */
	
	//@Disabled
	@Test
	public void newSaleListedAsOpenTest() throws IOException {
		final String NPC = "197672337";
		
		HtmlPage nextPage;
		HtmlAnchor link;
		HtmlForm form;
		HtmlTable table;
		HtmlTableRow row;
		HtmlInput input;
		WebRequest req;
		String textNextPage;
		String xpath;
		String status;
		
		// Check that customer exists
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		assertTrue(nextPage.asText().contains(NPC));
		
		// Get add sale page link
		link = page.getAnchorByHref("addSale.html");
		// Click on it
		nextPage = (HtmlPage) link.openLinkInNewWindow();
		
		// Place data at form
		form = nextPage.getForms().get(0);
		input = form.getInputByName("customerVat");
		input.setValueAttribute(NPC);
		input = form.getInputByValue("Add Sale");
		input.click();
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetSalePageController"), HttpMethod.GET);
		req.setRequestParameters(asList(new NameValuePair("customerVat", NPC)));
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage);  // check if is the next page
		
		// Check new sale status
		xpath = "/html/body//table";
		table = (HtmlTable) nextPage.getByXPath(xpath).get(0);
		row = table.getRow(table.getRowCount() - 1);
		status = row.getCell(3).asText();
		
		assertEquals("O", status);
		
	}
	
	/**
	 * 2.c)
	 * 
	 * Testing:
	 * 
	 * After closing a sale, it will be listed as closed.
	 * 
	 * @throws IOException
	 */
	
	//@Disabled
	@Test
	public void saleListedAsClosedTest() throws IOException {
		final String NPC = "197672337";
		
		HtmlPage nextPage;
		HtmlTable table;
		HtmlTableRow row;
		WebRequest req;
		String textNextPage;
		String formData;
		String xpath;
		String id;
		String status;
		
		// Check that customer exists
		HtmlAnchor getCustomersLink = page.getAnchorByHref("GetAllCustomersPageController");
		nextPage = (HtmlPage) getCustomersLink.openLinkInNewWindow();
		assertTrue(nextPage.asText().contains(NPC));
		
		// Place data at form
		formData = String.format("customerVat=%s", NPC);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSalePageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage);  // check if is the report page
		
		// Get new sale id
		xpath = "/html/body//table";
		table = (HtmlTable) nextPage.getByXPath(xpath).get(0);
		row = table.getRow(table.getRowCount() - 1);
		id = row.getCell(0).asText();
		
		// Update new sale status to 'CLOSED'
		formData = String.format("id=%s", id);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"UpdateSaleStatusPageControler"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage);  // check if is the report page
		
		// Check new sale updated status
		table = (HtmlTable) nextPage.getByXPath(xpath).get(0);
		row = table.getRow(table.getRowCount() - 1);
		status = row.getCell(3).asText();
		
		assertEquals("C", status);
	}
	
	/**
	 * 2.d)
	 * 
	 * Testing:
	 * 
	 * Create a new customer, create a new sale for her, 
	 * insert a delivery for that sale and then show the sale delivery. 
	 * Check that all intermediate pages have the expected information.
	 * 
	 * @throws IOException
	 */
	
	//@Disabled
	@Test
	public void insertSaleDeliveryTest() throws IOException {
		final String NPC = "503183504";
		
		// Customer address data
        final String DESIGNATION = "FCUL";
        final String PHONE = "217500000";
		final String ADDRESS = "CAMPO GRANDE";
		final String DOOR = "016";
		final String POSTAL_CODE = "1749-016";
		final String LOCALITY = "LISBOA";
		
		// Sale data
		final String DATE = java.time.LocalDate.now().toString();
		final String TOTAL = "0.0";
		final String STATUS = "O";
		
		HtmlPage nextPage;
		HtmlTable table;
		HtmlTableRow row;
		WebRequest req;
		String textNextPage;
		String formData;
		String xpath;
		String addr_id;
		String sale_id;
		
		HtmlInput vatInput;
		HtmlInput submit;
		HtmlAnchor link;
		HtmlForm form;
		
		// Place data at form
		formData = String.format("vat=%s&designation=%s&phone=%s", NPC, DESIGNATION, PHONE);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddCustomerPageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage);  // check if is the report page
		
		assertTrue(textNextPage.contains(DESIGNATION));
		assertTrue(textNextPage.contains(PHONE));
		
		// Place data at form
		formData = String.format("vat=%s&address=%s&door=%s&postalCode=%s&locality=%s", 
				NPC, ADDRESS, DOOR, POSTAL_CODE, LOCALITY);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetCustomerPageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage);  // check if is the report page
		
		assertTrue(textNextPage.contains(ADDRESS));
		assertTrue(textNextPage.contains(DOOR));
		assertTrue(textNextPage.contains(POSTAL_CODE));
		assertTrue(textNextPage.contains(LOCALITY));
		
		// Place data at form
		formData = String.format("customerVat=%s", NPC);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSalePageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage);  // check if is the report page
		
		assertTrue(textNextPage.contains(DATE));
		assertTrue(textNextPage.contains(TOTAL));
		assertTrue(textNextPage.contains(STATUS));
		assertTrue(textNextPage.contains(NPC));
		
		// Place data at form
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSaleDeliveryPageController"), HttpMethod.GET);
		req.setRequestParameters(asList(new NameValuePair("vat", NPC)));
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage);  // check if is the report page
		
		assertTrue(textNextPage.contains(ADDRESS));
		assertTrue(textNextPage.contains(DOOR));
		assertTrue(textNextPage.contains(POSTAL_CODE));
		assertTrue(textNextPage.contains(LOCALITY));
		assertTrue(textNextPage.contains(DATE));
		assertTrue(textNextPage.contains(TOTAL));
		assertTrue(textNextPage.contains(STATUS));
		assertTrue(textNextPage.contains(NPC));
		
		// Get last address id
		xpath = "/html/body//table";
		table = (HtmlTable) nextPage.getByXPath(xpath).get(0);
		row = table.getRow(table.getRowCount() - 1);
		addr_id = row.getCell(0).asText();
		
		// Get last sale id
		table = (HtmlTable) nextPage.getByXPath(xpath).get(1);
		row = table.getRow(table.getRowCount() - 1);
		sale_id = row.getCell(0).asText();
		
		// Place data at form
		formData = String.format("addr_id=%s&sale_id=%s", addr_id, sale_id);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSaleDeliveryPageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			nextPage = (HtmlPage) webClient.getPage(req);
		}
		
		textNextPage = nextPage.asText();
		System.out.println(textNextPage);  // check if is the report page
		
		assertTrue(textNextPage.contains(sale_id));
		assertTrue(textNextPage.contains(addr_id));
		
		// at index, goto Remove case use and remove the previous client
		link = page.getAnchorByHref("RemoveCustomerPageController");
		nextPage = (HtmlPage) link.openLinkInNewWindow();
		assertTrue(nextPage.asText().contains(NPC));
		
		form = nextPage.getForms().get(0);
		vatInput = form.getInputByName("vat");
		vatInput.setValueAttribute(NPC);
		submit = form.getInputByName("submit");
		submit.click();
		
		// now check that the new client was erased
		link = page.getAnchorByHref("GetAllCustomersPageController");
		nextPage = (HtmlPage) link.openLinkInNewWindow();
		assertFalse(nextPage.asText().contains(NPC));
	}
	
}
