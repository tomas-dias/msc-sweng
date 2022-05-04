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

@DisplayName("<= Testing LongestPrefixOf Method - Edge-Pair Coverage =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTLongestPrefixOfEdgePairTest {
	
	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Tests when the given query is null.
	// Requirements satisfied for Edge-Pairs: [1,2]
	@Disabled
	@Test
	@DisplayName("throws IllegalArgumentException when the given query is null")
	public void nullQueryTest() {
		String query = null;
		assertThrows(IllegalArgumentException.class, () -> { tst.longestPrefixOf(query); });
	}
	
	// Tests when the given query is empty.
	// Requirements satisfied for Edge-Pairs: [1,3,4]
	@Disabled
	@Test
	@DisplayName("is null when the given query is empty")
	public void emptyQueryTest() {
		String query = "";
		assertEquals(null, tst.longestPrefixOf(query), () -> "should be null");
	}
	
	// Tests when the given query equals some key in the symbol table.
	// Requirements satisfied for Edge-Pairs:
	// [1,3,5] [3,5,6] [5,6,7] [6,7,8] [7,8,9] [8,9,6] [9,6,7] [7,8,10] [8,10,12] [10,12,13] [12,13,15] [13,15,6] [15,6,7] 
	// [12,13,14] [13,14,15] [14,15,6] [15,6,16]
	// @Disabled
	@Test
	@DisplayName("is 'by' when the given query is 'by")
	public void queryEqualsKeyTest(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		String query = "by";
		assertEquals("by", tst.longestPrefixOf(query), () -> "should be by");
	}
	
	// Tests when the given query doesn't find any key in the symbol table.
	// Requirements satisfied for Edge-Pairs:
	// [1,3,5] [3,5,6] [5,6,7] [6,7,8] [7,8,10] [8,10,11] [10,11,6] [11,6,7] [11,6,16]
	@Disabled
	@Test
	@DisplayName("is empty when the given query doesn't find any key in the symbol table")
	public void queryDoesNotFindKeyTest(String[] keys) {
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		String query = "z";
		assertEquals("", tst.longestPrefixOf(query), () -> "should be empty");
	}
	
	// Tests when the given query doesn't find any key in the symbol table.
	// Requirements satisfied for Edge-Pairs:
	// [1,3,5] [3,5,6] [5,6,7] [6,7,8] [7,8,9] [8,9,6] [9,6,7] [9,6,16]
	@Disabled
	@Test
	@DisplayName("is empty when the given query doesn't find any key in the symbol table")
	public void queryDoesNotFindKey2Test(String[] keys) {
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		String query = "a";
		assertEquals("", tst.longestPrefixOf(query), () -> "should be empty");
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
