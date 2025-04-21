package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.*;

public class Main {

	private static String _inFile = null;
	private static String _outFile = null;
	private static int _ticks = 10;
	private static Factory<Event> _eventsFactory = null;
	private static String _mode = "gui";


	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);
			parseModeOption(line);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator's main loop (default value is 10).").build());
		
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Execution mode: 'gui' or 'console' (default is 'gui')").build());

		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_mode.equals("console") && _inFile == null) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		if (_mode.equals("console")) {
			_outFile = line.getOptionValue("o");
		}
	}

	private static void parseModeOption(CommandLine line) throws ParseException {
		_mode = line.getOptionValue("m");
		if (_mode == null) _mode = "console";
		if (!_mode.equals("gui") && !_mode.equals("console")) {
			throw new ParseException("Invalid mode. Use 'gui' or 'console'.");
		}
	}
	
	private static void parseTicksOption(CommandLine line) throws ParseException {
		_ticks = Integer.parseInt(line.getOptionValue("t"));
		if (_ticks <= 0) {
			throw new ParseException("Number of ticks must be a positive integer.");
		}
	}


	private static void initFactories() {
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);
		
		// initialize the events factory
		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add(new NewJunctionEventBuilder(lssFactory,dqsFactory));
		ebs.add(new NewCityRoadEventBuilder());
		ebs.add(new NewInterCityRoadEventBuilder() );
		ebs.add(new NewVehicleEventBuilder());
		ebs.add(new SetWeatherEventBuilder());
		ebs.add(new SetContClassEventBuilder());
		Factory<Event> eventsFactory = new BuilderBasedFactory<>(ebs);
		
		_eventsFactory = eventsFactory;
	}

	private static void startBatchMode() throws Exception {
	    InputStream in = new FileInputStream(_inFile);
	    OutputStream out = new FileOutputStream(_outFile);
	    
	    TrafficSimulator simulator = new TrafficSimulator();
		
	    
	    Controller controller = new Controller(simulator, _eventsFactory);
		controller.loadEvents(in);
	    controller.run(_ticks, out);
	}

	private static void start(String[] args) throws Exception {
		initFactories();
		parseArgs(args);
		if(_mode.equals("console")) {
			startBatchMode();
		}
		else {
			startGUIMode();
		}
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	private static void startGUIMode() throws Exception {
		TrafficSimulator simulator = new TrafficSimulator();
		Controller ctrl = new Controller(simulator, _eventsFactory);
	
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new simulator.view.MainWindow(ctrl);
			}
		});
	}

	
	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
