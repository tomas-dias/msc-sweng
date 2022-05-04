package sut.logic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import sut.TST;
import sut.util.TSTKeysResolver;

@DisplayName("<= Testing LongestPrefixOf Method - Logic-Based Coverage =>")
@ExtendWith(TSTKeysResolver.class)
public class TSTLongestPrefixOfLogicTest {

	private TST<Integer> tst;
	
	// Setup to testing.
	@BeforeEach
	public void setup() {
		tst = new TST<>();
	}
	
	// PREDICATES AND CLAUSES
	//
	// p1: c1               ; c1: query == null
	// p2: c2               ; c2: query.length() == 0
	// p3: c3 && c4         ; c3: x != null, c4: i < query.length();
	// p4: c5               ; c5: c < x.c;
	// p5: c6               ; c6: c > x.c;
	// p6: c7               ; c7: x.val != null
	
	// Teardown after testing.
	@AfterEach
	public void tearDown() {
		tst = null;
	}
}
