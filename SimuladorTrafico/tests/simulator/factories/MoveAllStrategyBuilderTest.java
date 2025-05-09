package simulator.factories;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import simulator.model.*;
import simulator.factories.*;

class MoveAllStrategyBuilderTest {

	@Test
	void test_1() {
		MoveAllStrategyBuilder eb = new MoveAllStrategyBuilder();
		
		String dataJSon = "{}";
		DequeuingStrategy o = eb.create_instance(new JSONObject(dataJSon));
		assertTrue( o instanceof MoveAllStrategy );
		
	}

	@Test
	void test_2() {
		MoveAllStrategyBuilder eb = new MoveAllStrategyBuilder();
		
		String dataJSon = "{}";
		DequeuingStrategy o = eb.create_instance(new JSONObject(dataJSon));
		assertTrue( o instanceof MoveAllStrategy );
		
	}

}
