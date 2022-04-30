package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;

@DisplayName("<= Testing Put Method =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTPutTest 
{
	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Tests when the given key is null.
	@Test
	@DisplayName("throws IllegalArgumentException when the given key is null")
	public void nullKeyTest() {
		String key = null;
		int val = 0;
		assertThrows(IllegalArgumentException.class, () -> { tst.put(key, val); });
	}
	
	// Tests insertion of the given key when the symbol table doesn't contain it.
	@Test
	@DisplayName("is true when the given key is inserted in the symbol table")
	public void tableDoesNotContainKeyTest(String[] keys) { // Parameter injected by TSTKeysResolver.
		assumeTrue(tst.size() == 0);
		assumeFalse(tst.size() != 0);		
		
		String key = keys[0];
		int val = 0;
		
		tst.put(key, val);
		assertTrue(tst.contains(key));
	}
	
	// Tests if the given key value is overwritten when the symbol table already contains the key.
	@Test
	@DisplayName("is 1 when the given key value is overwritten")
	public void tableContainsKeyTest(String[] keys) {
		String key = keys[0];
		tst.put(key, 0);
		
		assumeTrue(tst.size() != 0);
		assumeFalse(tst.size() == 0);

		tst.put(key, 1);
		assertEquals(1, (int) tst.get(key));
	}
	
	@Test
	public void multipleKeyInsertionsTest(String[] keys) {
		assumeTrue(tst.size() == 0);
		assumeFalse(tst.size() != 0);	
		
		for(int i = 0; i < keys.length; i++) {
			tst.put(keys[i], i);
			System.out.println(keys[i]);
		}
		assertEquals(8, (int) tst.size());
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
