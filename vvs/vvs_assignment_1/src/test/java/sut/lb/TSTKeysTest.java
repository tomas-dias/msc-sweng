package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;
import sut.util.TSTKeysResolver;

@DisplayName("<= Testing Keys Method =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTKeysTest {
	
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
		Queue<String> queue = new LinkedList<>();
		assertEquals(queue, tst.keys(), () -> "should be empty");
	}
	
	// Tests retrieving keys from the symbol table.
	@Test
	@DisplayName("is sorted keys[] when retrieving keys from the symbol table")
	public void retrieveKeysTest(String[] keys) { // parameter injected by TSTKeysResolver
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);
		
		Arrays.sort(keys);
		Queue<String> queue = new LinkedList<>();
		
		for(int i = 0; i < keys.length; i++) {
			if(i == 0 || !keys[i].equals(keys[i - 1]))
				queue.add(keys[i]);
		}
		assertEquals(queue, tst.keys(), () -> "should be sorted keys[]");
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
