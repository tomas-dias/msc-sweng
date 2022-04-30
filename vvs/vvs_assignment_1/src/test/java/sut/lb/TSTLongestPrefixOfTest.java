package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;

@DisplayName("<= Testing LongestPrefixOf Method =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTLongestPrefixOfTest {

	private TST<Integer> tst;
	
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	@Test
	public void nullQueryTest() {
		String query = null;
		
		assertThrows(IllegalArgumentException.class, () -> {
			tst.longestPrefixOf(query);
		});
	}
	
	@Test
	public void emptyQueryTest() {
		String query = "";
		
		assertEquals(null, tst.longestPrefixOf(query), () -> "should return null");
	}
	
	@Test
	public void retrieveLongestKeyTest(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		
		String query = "shell";
		
		assertEquals("she", tst.longestPrefixOf(query), () -> "should return 'she'");
	}
	

	@Test
	public void retrieveLongestKey2Test(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		
		String query = "ass";
		
		assertEquals("", tst.longestPrefixOf(query), () -> "should return 'by'");
	}
	
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
