package sut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

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
	
	//@ParameterizedTest(name = "check if {0} equals {1}")
	//@ArgumentsSource(TSTArgumentsProvider.class)
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
