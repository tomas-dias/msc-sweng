package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sut.TST;

@DisplayName("<= Testing Keys Method =>")
public class TSTKeysTest {
	
	private TST<Integer> tst;
	
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	@Test
	public void tableEmptyTest() {
		Queue<String> queue = new LinkedList<>();
		assertEquals(queue, tst.keys());
	}
	
	@Test
	public void retrieveKeysTest() {
		String left = "left";
		String mid = "mid";
		String right = "right";
		
		tst.put(left, 0);
		tst.put(mid, 1);
		tst.put(right, 2);
		
		Queue<String> queue = new LinkedList<>();
		queue.add(left);
		queue.add(mid);
		queue.add(right);
		
		assertEquals(queue, tst.keys());
	}
	
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
