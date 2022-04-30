package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sut.TST;

@DisplayName("<= Testing KeysThatMatch Method =>")
public class TSTKeysThatMatchTest {
	
	private TST<Integer> tst;
	
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	@Test
	public void tableEmptyTest() {
		String pattern = "test";
		Queue<String> queue = new LinkedList<>();
		
		assertEquals(queue, tst.keysThatMatch(pattern));
	}
	
	@Test
	public void retrieveKeysThatMatchPatternTest() {
		String left = "ignore";
		String mid = "testone";
		String right = "testtwo";
		
		tst.put(left, 0);
		tst.put(mid, 1);
		tst.put(right, 2);
		
		String pattern = "..st...";
		
		Queue<String> queue = new LinkedList<>();
		queue.add(mid);
		queue.add(right);
		
		assertEquals(queue, tst.keysThatMatch(pattern));
	}
	
	@Test
	public void retrieveKeysWhenPatternMatchNullValueTest() {
		String left = "ignore";
		String mid = "testone";
		String right = "testtwo";
		
		tst.put(left, 0);
		tst.put(mid, 1);
		tst.put(right, 2);
		
		String pattern = "ign";
		
		Queue<String> queue = new LinkedList<>();
		
		assertEquals(queue, tst.keysThatMatch(pattern));
	}
	
	@Test
	public void retrieveKeysWhenPatternDoesNotMatchAnyKeyTest() {
		String left = "ignore";
		String mid = "testone";
		String right = "testtwo";
		
		tst.put(left, 0);
		tst.put(mid, 1);
		tst.put(right, 2);
		
		String pattern = "dontmatch";
		
		Queue<String> queue = new LinkedList<>();
		
		assertEquals(queue, tst.keysThatMatch(pattern));
	}
	
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
