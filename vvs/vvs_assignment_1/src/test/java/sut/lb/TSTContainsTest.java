package sut.lb;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;

@DisplayName("<= Testing Contains Method =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTContainsTest {

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
		assertThrows(IllegalArgumentException.class, () -> { tst.contains(key); });
	}
	
	// Tests when the symbol table doesn´t contain the given key.
	@Test
	@DisplayName("is false when the table doesn't contain the given key")
	public void tableDoesNotContainKeyTest(String[] keys) { // Parameter injected by TSTKeysResolver.
		String key = keys[0];
		assertFalse(tst.contains(key));
	}
	
	// Tests when the symbol table contains the given key.
	@Test
	@DisplayName("is true when the table contains the given key")
	public void tableContainsKeyTest(String[] keys) {
		String key = keys[0];
		tst.put(key, 0);
		assertTrue(tst.contains(key));
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
