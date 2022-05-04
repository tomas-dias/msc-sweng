package sut.acup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;
import sut.util.TSTKeysResolver;

@DisplayName("<= Testing Put Method - All-Coupling-Use-Paths Coverage =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTPutAllCouplingUsePathsTest {

	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
