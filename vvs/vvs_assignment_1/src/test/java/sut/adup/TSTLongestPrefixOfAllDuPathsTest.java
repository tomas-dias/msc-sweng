package sut.adup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;
import sut.util.TSTKeysResolver;

@DisplayName("<= Testing LongestPrefixOf Method - All-Du-Paths Coverage =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTLongestPrefixOfAllDuPathsTest {

	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Tests when the given query is empty.
	// Requirements satisfied for All-Du-Paths: [1,3,4]
	// @Disabled
	@Test
	@DisplayName("is null when the given query is empty")
	public void emptyQueryTest() {
		String query = "";
		assertEquals(null, tst.longestPrefixOf(query), () -> "should be null");
	}
	
	// Tests when the given query doesn't retrieve any key.
	// @Disabled
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
	// @Disabled
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
	
	// Tests when the search ends at end of string value that is not null.
	// @Disabled
	@Test
	@DisplayName("is 'the' when the search ends at end of string value that is not null")
	public void retrieveLongestKeyWithNotNullValue2Test(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		
		String query = "the";
		assertEquals("the", tst.longestPrefixOf(query), () -> "should be the");
	}
	
	// Tests when the search ends at end of string value that is null.
	// @Disabled
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
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
