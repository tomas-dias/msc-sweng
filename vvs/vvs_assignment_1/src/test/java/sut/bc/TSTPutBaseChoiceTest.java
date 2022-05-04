package sut.bc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;
import sut.util.TSTKeysResolver;

@DisplayName("<= Testing Put Method - Base Choice Coverage =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTPutBaseChoiceTest {

	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Partitions:
	// [new key, existent key] [new key prefix, existent key prefix]
	// [empty, not empty] [smallest key, largest key, typical key]
	
	// Tests the base choice (new key, new key prefix, empty, typical key).
	@Test
	@DisplayName("is 0 when the given key value is 0")
	public void baseChoiceTest(String[] keys) { // parameter injected by TSTKeysResolver
		assumeTrue(tst.size() == 0);
		assumeFalse(tst.size() != 0);
		
		String key = keys[0]; // she
		int val = 0;
		tst.put(key, val);
		
		assumeTrue(tst.size() != 0);
		assumeFalse(tst.size() == 0);
		assertEquals(0, (int) tst.get(key), () -> "should be 0");
	}
	
	// Tests the existent key characteristic.
	@Test
	@DisplayName("is 1 when the given key value is overwritten")
	public void existentKeyTest(String[] keys) {
		assumeTrue(tst.size() == 0);
		assumeFalse(tst.size() != 0);
		
		String key = keys[0]; // she
		tst.put(key, 0);
		
		assumeTrue(tst.size() != 0);
		assumeFalse(tst.size() == 0);
		
		tst.put(key, 1);
		assertEquals(1, (int) tst.get(key), () -> "should be 1");
	}
	
	// Tests the existent key prefix characteristic.
	@Test
	@DisplayName("is 'shells' when the given key prefix is 'she")
	public void existentKeyPrefixTest(String[] keys) {
		assumeTrue(tst.size() == 0);
		assumeFalse(tst.size() != 0);
		
		String prefix = keys[0]; // she
		tst.put(prefix, 0);
		
		assumeTrue(tst.size() != 0);
		assumeFalse(tst.size() == 0);
		
		String key = keys[3]; // shells
		tst.put(key, 1);
		assertEquals(key, tst.longestPrefixOf(key), () -> "should be 'shells'");
	}
	
	// Tests when the tree is not empty characteristic.
	@Test
	@DisplayName("is 8 when the given key value is 8")
	public void treeIsNotEmptyTest(String[] keys) {
		assumeTrue(tst.size() == 0);
		assumeFalse(tst.size() != 0);
		
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		
		String key = "test";
		tst.put(key, 8);
		
		assumeTrue(tst.size() != 0);
		assumeFalse(tst.size() == 0);
		assertEquals(8, (int) tst.get(key), () -> "should be 8");
	}
	
	// Tests the smallest key characteristic.
	@Test
	@DisplayName("is 2 when the given key value is 2")
	public void smallestKeyTest(String[] keys) { // parameter injected by TSTKeysResolver
		assumeTrue(tst.size() == 0);
		assumeFalse(tst.size() != 0);
		
		tst.put(keys[0], 0); // she
		tst.put(keys[5], 1); // the
		
		String key = keys[4]; // by (smallest)
		tst.put(key, 2);
		
		assumeTrue(tst.size() != 0);
		assumeFalse(tst.size() == 0);
		
		assertEquals(2, (int) tst.get(key), () -> "should be 2");
	}
	
	// Tests the largest key characteristic.
	@Test
	@DisplayName("is 1 when the given key value is 1")
	public void largestKeyTest(String[] keys) { // parameter injected by TSTKeysResolver
		assumeTrue(tst.size() == 0);
		assumeFalse(tst.size() != 0);
		
		tst.put(keys[0], 0); // she
		tst.put(keys[4], 2); // by
		
		String key = keys[5]; // the (largest)
		tst.put(key, 1);
		
		assumeTrue(tst.size() != 0);
		assumeFalse(tst.size() == 0);
		
		assertEquals(1, (int) tst.get(key), () -> "should be 2");
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
