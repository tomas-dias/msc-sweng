package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;
import sut.util.TSTKeysResolver;

@DisplayName("<= Testing KeysThatMatch Method =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTKeysThatMatchTest {
	
	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Tests when the symbol table is empty.
	@Test
	@DisplayName("is empty when the symbol table is empty")
	public void tableEmptyTest() { 
		String pattern = "....";
		Queue<String> queue = new LinkedList<>();
		assertEquals(queue, tst.keysThatMatch(pattern), () -> "should be empty");
	}
	
	// Tests retrieving keys with a given pattern.
	@Test
	@DisplayName("is [shells] when retrieving keys from the symbol table with the given pattern")
	public void retrieveKeysThatMatchPatternTest(String[] keys) { // Parameter injected by TSTKeysResolver.
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		String pattern = ".he.l.";
		
		Queue<String> queue = new LinkedList<>();
		queue.add(keys[3]); // shells
		
		assertEquals(queue, tst.keysThatMatch(pattern), () -> "should be [shells]");
	}
	
	// Tests retrieving keys when the given pattern matches a null value in the symbol table.
	@Test
	@DisplayName("is empty when retrieving keys from the symbol table with the given pattern matching a null value")
	public void retrieveKeysWhenPatternMatchesNullValueTest(String[] keys) {
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		
		String pattern = "sh";
		
		Queue<String> queue = new LinkedList<>();
		
		assertEquals(queue, tst.keysThatMatch(pattern), () -> "should be empty");
	}
	
	// Tests retrieving keys when the given pattern doesn't match any key in the symbol table.
	@Test
	@DisplayName("is empty when retrieving keys from the symbol table with the given pattern not matching any key")
	public void retrieveKeysWhenPatternDoesNotMatchAnyKeyTest(String[] keys) {
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		
		String pattern = "dontmatch";
		
		Queue<String> queue = new LinkedList<>();
		
		assertEquals(queue, tst.keysThatMatch(pattern), () -> "should be empty");
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
