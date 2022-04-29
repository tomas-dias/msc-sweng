package sut.lb;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sut.TST;

@DisplayName("<= Testing Contains Method =>")
public class TSTContainsTest {

	private TST<Integer> tst;
	
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	@Test
	public void nullKeyTest() {
		String key = null;
		
		assertThrows(IllegalArgumentException.class, () -> {
			tst.contains(key);
		});
	}
	
	@Test
	public void tableDoesNotContainKeyTest() {
		String key = "test";
		
		assertFalse(tst.contains(key));
	}
	
	@Test
	public void tableContainsKeyTest() {
		String key = "test";
		int val = 0;
		tst.put(key, val);
		
		assertTrue(tst.contains(key));
	}
	
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
