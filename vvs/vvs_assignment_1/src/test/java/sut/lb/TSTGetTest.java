package sut.lb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;

@DisplayName("<= Testing Get Method =>")
@ExtendWith(TSTResolver.class)
public class TSTGetTest {
	
	private TST<Integer> tst;
	
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	@Test
	public void nullKeyTest() {
		String key = null;
		
		assertThrows(IllegalArgumentException.class, () -> {
			tst.get(key);
		});
	}
	
	@Test
	public void keyLengthEqualsZeroTest() {
		String key = "";
		
		assertThrows(IllegalArgumentException.class, () -> {
			tst.get(key);
		});
	}
	
	@Test
	public void nullRootTest() {
		String key = "test";
		
		assertEquals(null, tst.get(key), () -> "should return null");
	}
	
	@Test
	public void findValueByKeySimpleTest() {
		String key = "test";
		tst.put(key, 0);
		
		assertEquals(0, (int) tst.get(key));
	}
	
	@Disabled("still to do...")
	@Test
	public void findValueByKeyTest(Map<String, Integer> map) {
		map.forEach((k, v) -> {
			tst.put(k, v);
			assertEquals(map.get(k), tst.get(k));});
	}
	
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
