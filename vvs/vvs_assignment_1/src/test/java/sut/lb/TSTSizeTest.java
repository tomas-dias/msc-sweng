package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;

@DisplayName("<= Testing Size Method =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTSizeTest {

	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Tests the size when the symbol table is empty.
	@Test
	@DisplayName("is 0 when the symbol table is empty")
	public void emptyTableSizeTest() {
		assertEquals(0, tst.size(), () -> "should be 0");
	}
	
	// Tests the size when the symbol table has keys.
	// Note: The size tested is 7 because this is the number of different keys contained in the injected array parameter.
	@Test
	@DisplayName("is 7 when the symbol table has keys")
	public void getSizeTest(String[] keys) { // Parameter injected by TSTKeysResolver.
		for(int i = 0; i < keys.length; i++)
			tst.put(keys[i], i);	
		assertEquals(7, tst.size(), () -> "should be 7");
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
