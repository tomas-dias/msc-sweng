package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sut.TST;

@DisplayName("<= Testing KeysWithPrefix Method =>")
public class TSTKeysWithPrefixTest {
	
	private TST<Integer> tst;
	
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	@Test
	public void nullPrefixTest() {
		String prefix = null;
		
		assertThrows(IllegalArgumentException.class, () -> {
			tst.keysWithPrefix(prefix);
		});
	}
	
	@Test
	public void tableEmptyTest() {
		String prefix = "test";
		Queue<String> queue = new LinkedList<>();
		
		assertEquals(queue, tst.keysWithPrefix(prefix));
	}
	
	@Test
	public void retrieveKeysWithPrefixTest() {
		String left = "ignore";
		String mid = "testone";
		String right = "testtwo";
		
		tst.put(left, 0);
		tst.put(mid, 1);
		tst.put(right, 2);
		
		String prefix = "test";
		
		Queue<String> queue = new LinkedList<>();
		queue.add(mid);
		queue.add(right);
		
		assertEquals(queue, tst.keysWithPrefix(prefix));
	}
	
	@Test
	public void retrieveKeysWhenPrefixEqualsKeyTest() {
		String left = "ignore";
		String mid = "testone";
		String right = "testtwo";
		
		tst.put(left, 0);
		tst.put(mid, 1);
		tst.put(right, 2);
		
		String prefix = "ignore";
		
		Queue<String> queue = new LinkedList<>();
		queue.add(left);
		
		assertEquals(queue, tst.keysWithPrefix(prefix));
	}
	
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
