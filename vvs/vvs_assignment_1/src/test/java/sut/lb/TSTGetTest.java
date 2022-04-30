package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;

@DisplayName("<= Testing Get Method =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTGetTest {
	
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
		assertThrows(IllegalArgumentException.class, () -> { tst.get(key); });
	}
	
	// Tests when the size of the given key is 0.
	@Test
	@DisplayName("throws IllegalArgumentException when the size of the given key is 0")
	public void keyLengthEqualsZeroTest() {
		String key = "";
		assertThrows(IllegalArgumentException.class, () -> { tst.get(key); });
	}
	
	// Tests when the subtrie corresponding to the given key is null.
	@Test
	@DisplayName("is null when the subtrie corresponding to the given key is null")
	public void nullSubtrieTest(String[] keys) { // Parameter injected by TSTKeysResolver.
		String key = keys[0];
		assertEquals(null, tst.get(key), () -> "should be null");
	}
	
	// Tests if the value in the node corresponding to the last given key character is found.
	@Test
	@DisplayName("is 5 if the value in the node corresponding to the last given key character is found")
	public void hitValueByLastKeyCharTest(String[] keys) {
		String key = keys[5]; // shells
		for(int i = 0; i < keys.length; i++) {
			if(i != 6) // trie without shore key
				tst.put(keys[i], i);
		}
		assertEquals(5, (int) tst.get(key));
	}
	
	// Tests if the search for the value is terminated at an internal node.
	@Test
	@DisplayName("is 3 if the search for the value is terminated at an internal node")
	public void hitValueByInternalKeyCharTest(String[] keys) {
		String key = keys[3]; // she
		for(int i = 0; i < keys.length; i++) {
			if(i != 6)
				tst.put(keys[i], i);
		}
		assertEquals(3, (int) tst.get(key));
	}
	
	// Tests if the value in the node corresponding to the last given key character is null.
	@Test
	@DisplayName("is null if the value in the node corresponding to the last given key character is null")
	public void missValueByLastKeyCharTest(String[] keys) {
		String key = "shell";
		for(int i = 0; i < keys.length; i++) {
			if(i != 6)
				tst.put(keys[i], i);
		}
		assertEquals(null, tst.get(key));
	}
	
	// Tests if no link exists for some char of the given key, the value is null.
	@Test
	@DisplayName("is null if no link exists for some char of the given key")
	public void missValueByInternalKeyCharTest(String[] keys) {
		String key = keys[6];
		for(int i = 0; i < keys.length; i++) {
			if(i != 6)
				tst.put(keys[i], i);
		}
		assertEquals(null, tst.get(key));
	}
	
	// Tests if the given key isn't in the symbol table, the value is null.
	@Test
	@DisplayName("is null if the given key isn't in the symbol table")
	public void missingKeyInTheTableTest(String[] keys) {
		String key = "missing";
		for(int i = 0; i < keys.length; i++) {
			if(i != 6)
				tst.put(keys[i], i);
		}
		assertEquals(null, tst.get(key));
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
