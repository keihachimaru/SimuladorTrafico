package simulator.factories;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import simulator.model.*;
import simulator.factories.*;

class NewJunctionEventBuilderTest {

	@Test
	void test_1() throws Exception {

		TrafficSimulator ts = new TrafficSimulator();

		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);

		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);

		String dataJson = "{\n"
				+ "     	 \"time\" : 1,\n" + "         \"id\"   : \"j1\",\n" + "      	 \"coor\" : [100,200],\n"
				+ "      	 \"ls_strategy\" : { \"type\" : \"round_robin_lss\", \"data\" : {\"timeslot\" : 5} },\n"
				+ "      	 \"dq_strategy\" : { \"type\" : \"move_first_dqs\",  \"data\" : {} }\n" + "   	 }";

		NewJunctionEventBuilder eb = new NewJunctionEventBuilder(lssFactory, dqsFactory);
		Event e = eb.create_instance(new JSONObject(dataJson));

		ts.addEvent(e);

		ts.advance();

		String s = "{\"time\":1,\"state\":{\"roads\":[],\"vehicles\":[],\"junctions\":[{\"green\":\"none\",\"queues\":[],\"id\":\"j1\"}]}}";

		assertTrue(new JSONObject(s).similar(ts.report()));

	}

	@Test
	void test_2() {

		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);

		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);

		// error in id
		String dataJson = "{\n"
				+ "     	 \"time\" : 1,\n" + "         \"id\"   : 44,\n" + "      	 \"coor\" : [100,200],\n"
				+ "      	 \"ls_strategy\" : { \"type\" : \"round_robin_lss\", \"data\" : {\"timeslot\" : 5} },\n"
				+ "      	 \"dq_strategy\" : { \"type\" : \"move_first_dqs\",  \"data\" : {} }\n" + "   	 }";

		NewJunctionEventBuilder eb = new NewJunctionEventBuilder(lssFactory, dqsFactory);
		assertThrows(Exception.class, () -> eb.create_instance(new JSONObject(dataJson)));
	}

	@Test
	void test_3() {

		ArrayList<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);

		ArrayList<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);

		// if there is no type field, it should throw an exception (it should be done
		// automatically by the json library when looking for such field)
		String inputJson = "{}";

		NewJunctionEventBuilder eb = new NewJunctionEventBuilder(lssFactory, dqsFactory);
		assertThrows(Exception.class, () -> eb.create_instance(new JSONObject(inputJson)));
	}

}
