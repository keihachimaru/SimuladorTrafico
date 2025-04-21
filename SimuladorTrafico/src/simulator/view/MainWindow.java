package simulator.view;

import java.awt.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;
import javax.swing.*;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;

	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		this.setBackground(new Color(34, 34, 34));
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(34, 34, 34));
		this.setContentPane(mainPanel);
		
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		viewsPanel.setBackground(new Color(34, 34, 34));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);

		JPanel tablesPanel = new JPanel();
		tablesPanel.setBackground(new Color(34, 34, 34));
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);

		JPanel mapsPanel = new JPanel();
		mapsPanel.setBackground(new Color(34, 34, 34));
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);

		// tables
		JTable eventsTable = new JTable(new EventsTableModel(_ctrl));
		JPanel eventsView = createViewPanel(eventsTable, "Events");
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		JTable vehiclesTable = new JTable(new VehiclesTableModel(_ctrl));
		JPanel vehiclesView = createViewPanel(vehiclesTable, "Vehicles");
		vehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(vehiclesView);
		
		JTable roadsTable = new JTable(new RoadsTableModel(_ctrl));
		JPanel roadsView = createViewPanel(roadsTable, "Roads");
		roadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(roadsView);
		
		JTable junctionsTable = new JTable(new JunctionsTableModel(_ctrl));
		JPanel junctionsView = createViewPanel(junctionsTable, "Junctions");
		junctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(junctionsView);
		
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		// TODO add a map for MapByRoadComponent
		// ...
		JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map by road");
		mapByRoadView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapByRoadView);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		p.setBackground(new Color(34, 34, 34));
		
		TitledBorder border = new TitledBorder(
       			BorderFactory.createLineBorder(Color.WHITE),
       			title,
       			TitledBorder.LEFT,
       			TitledBorder.TOP,
       			new Font("Segoe UI", Font.BOLD, 12),
       			Color.WHITE
       			);
       	p.setBorder(border);
		
		p.add(new JScrollPane(c));
		return p;
	}
}
