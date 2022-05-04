package sut.eppp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;
import sut.util.TSTKeysResolver;

@DisplayName("<= Testing LongestPrefixOf Method - Prime Path Coverage =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTLongestPrefixOfPrimePathTest {
	
	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Tests when the given query is null.
	// Requirements satisfied for Prime Path: [1,2]
	//@Disabled
	@Test
	@DisplayName("throws IllegalArgumentException when the given query is null")
	public void nullQueryTest() {
		String query = null;
		assertThrows(IllegalArgumentException.class, () -> { tst.longestPrefixOf(query); });
	}
	
	// Tests when the given query is empty.
	// Requirements satisfied for Prime Path: [1,3,4]
	//@Disabled
	@Test
	@DisplayName("is null when the given query is empty")
	public void emptyQueryTest() {
		String query = "";
		assertEquals(null, tst.longestPrefixOf(query), () -> "should be null");
	}
	
	// Tests when the given query doesn't retrieve any key.
	// Requirements satisfied for Prime Path (check the report to view the prime path associated with the following indexes): 
	// 29; 31; 32; 34; 39; 40; 42
	// Test Path: [1,3,5,6,7,8,9,6,7,8,10,11,6,16]
	//@Disabled
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
	
	// Requirements satisfied for Prime Path (check the report to view the prime path associated with the following indexes): 
	// 42; 40; 39; 27; 21; 19; 18; 17; 7; 5; 4; 2;
	// Ignore: 41;
	// Test Path: [1,3,5,6,7,8,10,12,13,15,6,7,8,9,6,7,8,10,12,13,15,6,7,8,9,6,7,8,10,12,13,14,15,6,16]
	//@Disabled
	@Test
	@DisplayName("")
	public void retrieveLongestKeyWithNotNullValueTest(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		String query = "sea";
		assertEquals("sea", tst.longestPrefixOf(query), () -> "should be sea");
	}
	
	// Requirements satisfied for Prime Path (check the report to view the prime path associated with the following indexes): 
	// 36; 34; 33; 30; 28; 26; 23; 22; 21; 20; 17; 11; 5; 4
	// Test Path: [1,3,5,6,7,8,10,11,6,7,8,10,12,13,15,6,7,10,12,13,15,6,7,8,10,12,13,14,15,6,16]
	//@Disabled
	@Test
	@DisplayName("")
	public void retrieveLongestKeyWithNotNullValue2Test(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		String query = "the";
		assertEquals("the", tst.longestPrefixOf(query), () -> "should be sea");
	}
	// Requirements satisfied for Prime Path (check the report to view the prime path associated with the following indexes): 
	// 4; 5; 15; 29; 30; 32; 33; 34; 36; 39; 40; 42
	// Test Path: [1,3,5,6,7,8,9,6,7,8,10,11,6,7,8,10,12,13,14,15,6,16]
	//@Disabled
	@Test
	@DisplayName("")
	public void retrieveLongestKeyWithNotNullValue3Test(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		tst.put("k", 10);
		String query = "k";
		assertEquals("k", tst.longestPrefixOf(query), () -> "should be k");
	}
		
	// Tests when the search ends at end of string value that is not null.
	// Requirements satisfied for Prime Path (check the report to view the prime path associated with the following indexes): 
	// 2; 4; 5; 11; 17; 19; 21; 22; 23; 24; 26
	// Test Path: [1,3,5,6,7,8,10,12,13,15,6,7,8,10,12,13,15,6,7,8,10,12,13,14,15,6,16]
	//@Disabled
	@Test
	@DisplayName("is 'she' when the search ends at end of string value that is not null")
	public void retrieveLongestKeyWithNotNullValue4Test(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++) {
			if(!keys[i].equals("shore"))
				tst.put(keys[i], i);
		}	
		String query = "she";	
		assertEquals("she", tst.longestPrefixOf(query), () -> "should be 'she'");
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
