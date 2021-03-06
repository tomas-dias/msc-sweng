package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;
import sut.util.TSTKeysResolver;

@DisplayName("<= Testing LongestPrefixOf Method =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTLongestPrefixOfTest {

	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Tests when the given query is null.
	@Test
	@DisplayName("throws IllegalArgumentException when the given query is null")
	public void nullQueryTest() {
		String query = null;
		assertThrows(IllegalArgumentException.class, () -> { tst.longestPrefixOf(query); });
	}
	
	// Tests when the given query is empty.
	@Test
	@DisplayName("is null when the given query is empty")
	public void emptyQueryTest() {
		String query = "";
		assertEquals(null, tst.longestPrefixOf(query), () -> "should be null");
	}
	
	// Tests when the given query doesn't retrieve any key.
	@Test
	@DisplayName("is null when the given query is empty")
	public void queryResultIsEmptyTest(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++) {
			if(!keys[i].equals("shore"))
				tst.put(keys[i], i);
		}	
		String query = "query";
		assertEquals("", tst.longestPrefixOf(query), () -> "should be ''");
	}
	
	// Tests when the search ends at end of string value that is not null.
	@Test
	@DisplayName("is 'she' when the search ends at end of string value that is not null")
	public void retrieveLongestKeyWithNotNullValueTest(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++) {
			if(!keys[i].equals("shore"))
				tst.put(keys[i], i);
		}	
		String query = "she";	
		assertEquals("she", tst.longestPrefixOf(query), () -> "should be 'she'");
	}
	
	// Tests when the search ends at end of string value that is null.
	@Test
	@DisplayName("is 'she' (last key on path) when the search ends at end of string value that is null")
	public void retrieveLongestKeyWithNullValueTest(String[] keys) {
		for(int i = 0; i < keys.length; i++) {
			if(!keys[i].equals("shore"))
				tst.put(keys[i], i);
		}
		String query = "shell";
		assertEquals("she", tst.longestPrefixOf(query), () -> "should be 'she'");
	}
	
	// Tests when the search ends at null link.
	@Test
	@DisplayName("is 'shells' (last key on path) when the search ends at null link")
	public void retrieveLongestKeyWithNullLinkTest(String[] keys) {
		for(int i = 0; i < keys.length; i++) {
			if(!keys[i].equals("shore"))
				tst.put(keys[i], i);
		}
		String query = "shellsort";
		assertEquals("shells", tst.longestPrefixOf(query), () -> "should be 'shells'");
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
