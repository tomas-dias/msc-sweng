package sut;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("<= Testing TST Put Method =>")  // Customizes class names for test reports
public class TSTPutTest 
{
	private TST<Integer> tst;
	
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	@Test
	public void nullArgumentTest() {
		String key = null;
		int val = 0;
		assertThrows(NumberFormatException.class, () -> {
			tst.put(key, val);
		});
	}
	
}
