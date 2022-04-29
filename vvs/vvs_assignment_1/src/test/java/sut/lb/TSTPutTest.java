package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sut.TST;

@DisplayName("<= Testing Put Method =>")  // Customizes class names for test reports
public class TSTPutTest 
{
	private TST<Integer> tst;
	
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	@Test
	public void nullKeyTest() {
		String key = null;
		int val = 0;
		
		assertThrows(IllegalArgumentException.class, () -> {
			tst.put(key, val);
		});
	}
	
	@Test
	public void tableDoesNotContainKeyTest() {
		assumeTrue(tst.size() == 0);
		assumeFalse(tst.size() != 0);
		
		String key = "test";
		int val = 0;
		
		tst.put(key, val);
		
		assertTrue(tst.contains(key));
	}
	
	@Test
	public void tableContainsKeyTest() {
		String key = "test";
		
		tst.put(key, 0);
		
		assumeTrue(tst.size() != 0);
		assumeFalse(tst.size() == 0);

		tst.put(key, 1);
		
		assertEquals(1, (int) tst.get(key));
	}
	
	@AfterEach
	public void tearDown() {
		tst = null;
	}
	
}
