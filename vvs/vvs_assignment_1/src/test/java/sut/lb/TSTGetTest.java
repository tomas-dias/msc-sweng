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
import sut.util.TSTKeysResolver;

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
	// @Disabled
	@Test
	@DisplayName("throws IllegalArgumentException when the given key is null")
	public void nullKeyTest() {
		String key = null;
		assertThrows(IllegalArgumentException.class, () -> { tst.get(key); });
	}
	
	// Tests when the size of the given key is 0.
	// @Disabled
	@Test
	@DisplayName("throws IllegalArgumentException when the size of the given key is 0")
	public void keyLengthEqualsZeroTest() {
		String key = "";
		assertThrows(IllegalArgumentException.class, () -> { tst.get(key); });
	}
	
	// Tests when the subtrie corresponding to the given key is null.
	// @Disabled
	@Test
	@DisplayName("is null when the subtrie corresponding to the given key is null")
	public void nullSubtrieTest(String[] keys) { // Parameter injected by TSTKeysResolver.
		String key = keys[0];
		assertEquals(null, tst.get(key), () -> "should be null");
	}
	
	// Tests if the value in the node corresponding to the last given key character is found.
	// @Disabled
	@Test
	@DisplayName("is 3 if the value in the node corresponding to the last given key character is found")
	public void hitValueByLastKeyCharTest(String[] keys) {
		String key = keys[3]; // shells
		for(int i = 0; i < keys.length; i++) {
			if(!keys[i].equals("shore"))
				tst.put(keys[i], i);
		}
		assertEquals(3, (int) tst.get(key), () -> "should be 3");
	}
	
	// Tests if the search for the value is terminated at an internal node.
	// @Disabled
	@Test
	@DisplayName("is 0 if the search for the value is terminated at an internal node")
	public void hitValueByInternalKeyCharTest(String[] keys) {
		String key = keys[0]; // she
		for(int i = 0; i < keys.length; i++) {
			if(!keys[i].equals("shore"))
				tst.put(keys[i], i);
		}
		assertEquals(0, (int) tst.get(key), () -> "should be 0");
	}
	
	// Tests if the value in the node corresponding to the last given key character is null.
	// @Disabled
	@Test
	@DisplayName("is null if the value in the node corresponding to the last given key character is null")
	public void missValueByLastKeyCharTest(String[] keys) {
		String key = "shell";
		for(int i = 0; i < keys.length; i++) {
			if(!keys[i].equals("shore"))
				tst.put(keys[i], i);
		}
		assertEquals(null, tst.get(key), () -> "should be null");
	}
	
	// Tests if no link exists for some char of the given key, the value is null.
	// @Disabled
	@Test
	@DisplayName("is null if no link exists for some char of the given key")
	public void missValueByInternalKeyCharTest(String[] keys) {
		String key = keys[7]; //shore
		for(int i = 0; i < keys.length; i++) {
			if(!keys[i].equals("shore"))
				tst.put(keys[i], i);
		}
		assertEquals(null, tst.get(key), () -> "should be null");
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
