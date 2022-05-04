package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;
import sut.util.TSTKeysResolver;

@DisplayName("<= Testing KeysWithPrefix Method =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTKeysWithPrefixTest {
	
	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Tests when the given prefix is null.
	@Test
	@DisplayName("throws IllegalArgumentException when the given prefix is null")
	public void nullPrefixTest() {
		String prefix = null;
		assertThrows(IllegalArgumentException.class, () -> { tst.keysWithPrefix(prefix); });
	}
	
	// Tests when the symbol table is empty.
	@Test
	@DisplayName("is empty when the symbol table is empty")
	public void tableEmptyTest(String[] keys) { // Parameter injected by TSTKeysResolver.
		String prefix = keys[0];
		Queue<String> queue = new LinkedList<>();
		assertEquals(queue, tst.keysWithPrefix(prefix), () -> "should be empty");
	}
	
	// Tests retrieving keys with a given prefix.
	@Test
	@DisplayName("is [she, shells, shore] when retrieving keys from the symbol table with the given prefix")
	public void retrieveKeysWithPrefixTest(String[] keys) {
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		
		String prefix = "sh";
		
		Queue<String> queue = new LinkedList<>();
		queue.add(keys[0]); // she
		queue.add(keys[3]); // shells
		queue.add(keys[7]); // shore
		
		assertEquals(queue, tst.keysWithPrefix(prefix), () -> "should be [she, shells, shore]");
	}
	
	// Tests retrieving keys when the given prefix equals key.
	@Test
	@DisplayName("is [shells] when retrieving keys from the symbol table with the given prefix equals key")
	public void retrieveKeysWhenPrefixEqualsKeyTest(String[] keys) {
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		
		String prefix = keys[3]; // shells
		
		Queue<String> queue = new LinkedList<>();
		queue.add(keys[3]);
		
		assertEquals(queue, tst.keysWithPrefix(prefix), () -> "should be [shells]");
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
