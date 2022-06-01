package vvs_webapp;

import static java.util.Arrays.asList;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class WebAppNarrativesTest {

	private static final String APPLICATION_URL = "http://localhost:8080/VVS_assignment_2/";
	private static final int APPLICATION_NUMBER_USE_CASES = 11;

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
	

	@Disabled
	@Test
	public void insertCustomerAddressTest() throws IOException {
		final String NPC = "197672337";
		final String ADDRESS = "CAMPO GRANDE";
		final String DOOR = "016";
		final String POSTAL_CODE = "1749-016";
		final String LOCALITY = "LISBOA";
		
		HtmlPage reportPage;
		HtmlTable table;
		WebRequest req;
		String textReportPage;
		String xpath;
		int oldTableSize = 0;
		int newTableSize;
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetCustomerPageController"), HttpMethod.GET);
		req.setRequestParameters(asList(new NameValuePair("vat", NPC)));
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		//System.out.println(textReportPage);  // check if is the report page	
		xpath = "/html/body//table";
		
		if(reportPage.getByXPath(xpath).size() != 0) {
			table = (HtmlTable) reportPage.getByXPath(xpath).get(0);
			oldTableSize = table.getRowCount() - 1;
		}
		
		System.out.println(oldTableSize);
		//assertEquals(0, tableSize);
		
		
		String formData = String.format("vat=%s&address=%s&door=%s&postalCode=%s&locality=%s", 
				NPC, ADDRESS, DOOR, POSTAL_CODE, LOCALITY);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetCustomerPageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		
		//System.out.println(textReportPage);  // check if is the report page
		
		assertTrue(textReportPage.contains(ADDRESS));
		assertTrue(textReportPage.contains(DOOR));
		assertTrue(textReportPage.contains(POSTAL_CODE));
		assertTrue(textReportPage.contains(LOCALITY));
		
		table = (HtmlTable) reportPage.getByXPath(xpath).get(0);
		System.out.println(table.getRowCount() - 1);
		
		newTableSize = table.getRowCount() - 1;
		assertEquals(oldTableSize + 1, newTableSize);
		
		System.out.println("---------------------------------");
		for (final HtmlTableRow row : table.getRows()) {
		    System.out.println("Found row");
		    for (final HtmlTableCell cell : row.getCells()) {
		       System.out.println("   Found cell: " + cell.asText());
		    }
		}
		System.out.println("---------------------------------");
	}
	
	@Disabled
	@Test
	public void newSaleListedAsOpenTest() throws IOException {
		final String NPC = "197672337";
		
		HtmlPage reportPage;
		HtmlAnchor link;
		HtmlForm form;
		HtmlTable table;
		HtmlTableRow row;
		HtmlInput input;
		WebRequest req;
		String textReportPage;
		String xpath;
		String status;
		
		link = page.getAnchorByHref("addSale.html");
		reportPage = (HtmlPage) link.openLinkInNewWindow();
		
		form = reportPage.getForms().get(0);
		input = form.getInputByName("customerVat");
		input.setValueAttribute(NPC);
		input = form.getInputByValue("Add Sale");
		input.click();
		
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetSalePageController"), HttpMethod.GET);
		req.setRequestParameters(asList(new NameValuePair("customerVat", NPC)));
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		System.out.println(textReportPage);  // check if is the report page
		
		xpath = "/html/body//table";
		table = (HtmlTable) reportPage.getByXPath(xpath).get(0);
		System.out.println(table.getRowCount() - 1);
		
		row = table.getRow(table.getRowCount() - 1);
		status = row.getCell(3).asText();
		
		assertEquals("O", status);
		
	}
	
	@Disabled
	@Test
	public void saleListedAsClosedTest() throws IOException {
		final String NPC = "197672337";
		
		HtmlPage reportPage;
		HtmlAnchor link;
		HtmlForm form;
		HtmlTable table;
		HtmlTableRow row;
		HtmlInput input;
		WebRequest req;
		String textReportPage;
		String formData;
		String xpath;
		String id;
		String status;
		
		formData = String.format("customerVat=%s", NPC);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSalePageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		System.out.println(textReportPage);  // check if is the report page
		
		xpath = "/html/body//table";
		table = (HtmlTable) reportPage.getByXPath(xpath).get(0);
		System.out.println(table.getRowCount() - 1);
		
		row = table.getRow(table.getRowCount() - 1);
		id = row.getCell(0).asText();
		
		formData = String.format("id=%s", id);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"UpdateSaleStatusPageControler"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		System.out.println(textReportPage);  // check if is the report page
		
		table = (HtmlTable) reportPage.getByXPath(xpath).get(0);
		System.out.println(table.getRowCount() - 1);
		
		row = table.getRow(table.getRowCount() - 1);
		status = row.getCell(3).asText();
		
		assertEquals("C", status);
	}
	
	@Test
	public void insertSaleDeliveryTest() throws IOException {
		final String NPC = "503183504";
        final String DESIGNATION = "FCUL";
        final String PHONE = "217500000";
		final String ADDRESS = "CAMPO GRANDE";
		final String DOOR = "016";
		final String POSTAL_CODE = "1749-016";
		final String LOCALITY = "LISBOA";
		
		HtmlPage reportPage;
		HtmlAnchor link;
		HtmlForm form;
		HtmlTable table;
		HtmlTableRow row;
		HtmlInput input;
		WebRequest req;
		String textReportPage;
		String formData;
		String xpath;
		String addr_id, sale_id;
		String status;
		
		formData = String.format("vat=%s&designation=%s&phone=%s", NPC, DESIGNATION, PHONE);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddCustomerPageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		
		System.out.println(textReportPage);  // check if is the report page
		
		formData = String.format("vat=%s&address=%s&door=%s&postalCode=%s&locality=%s", 
				NPC, ADDRESS, DOOR, POSTAL_CODE, LOCALITY);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"GetCustomerPageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		
		System.out.println(textReportPage);  // check if is the report page
		
		
		formData = String.format("customerVat=%s", NPC);
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSalePageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		System.out.println(textReportPage);  // check if is the report page
		
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSaleDeliveryPageController"), HttpMethod.GET);
		req.setRequestParameters(asList(new NameValuePair("vat", NPC)));
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		System.out.println(textReportPage);  // check if is the report page
		
		xpath = "/html/body//table";
		table = (HtmlTable) reportPage.getByXPath(xpath).get(0);
		
		row = table.getRow(table.getRowCount() - 1);
		addr_id = row.getCell(0).asText();
		
		table = (HtmlTable) reportPage.getByXPath(xpath).get(1);
		
		row = table.getRow(table.getRowCount() - 1);
		sale_id = row.getCell(0).asText();
		
		formData = String.format("addr_id=%s&sale_id=%s", addr_id, sale_id);
		
		
		req = new WebRequest(new java.net.URL(APPLICATION_URL+"AddSaleDeliveryPageController"), HttpMethod.POST);
		req.setRequestBody(formData);
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) { 
			reportPage = (HtmlPage) webClient.getPage(req);
		}
		
		textReportPage = reportPage.asText();
		System.out.println(textReportPage);  // check if is the report page
		
	}
	
}
