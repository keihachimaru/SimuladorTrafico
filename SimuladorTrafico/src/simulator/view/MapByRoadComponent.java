package simulator.view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class MapByRoadComponent extends JPanel implements TrafficSimObserver {

	private static final long serialVersionUID = 1L;

	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = new Color(34, 34, 34);
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	private RoadMap _map;

	private Image _car;
	private Image[] _contaminationImgs;
	private Image[] _weatherImgs;

	MapByRoadComponent(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		_car = loadImage("car.png");
		
		_weatherImgs = new Image[5];
		_weatherImgs[0] = loadImage("sun.png");
		_weatherImgs[1] = loadImage("cloud.png");
		_weatherImgs[2] = loadImage("rain.png");
		_weatherImgs[3] = loadImage("wind.png");
		_weatherImgs[4] = loadImage("storm.png");
		
		_contaminationImgs = new Image[6];
		for (int i = 0; i < 6; i++) {
			_contaminationImgs[i] = loadImage("cont_" + i + ".png");
		}
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		int i = 0;
		for (Road r : _map.getRoads()) {
			int x1 = 50;
			int x2 = getWidth()-100;
			int y = (i+1)*50;

			g.setColor(Color.BLACK);
			g.drawLine(x1, y, x2, y);

			// Junction circles
			g.setColor(Color.BLUE);
			g.fillOval(x1 - 5, y - 5, 10, 10);
			
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				g.setColor(Color.GREEN);
			}
			else {
				g.setColor(Color.RED);
			}
			g.fillOval(x2 - 5, y - 5, 10, 10);

			// Road id
			g.setColor(Color.BLACK);
			g.drawString(r.getId(), x1-30, y + 2);
			g.setColor(Color.RED);
			g.drawString(r.getSrc().getId(), x1-2, y - 8);
			g.drawString(r.getDest().getId(), x2-2, y - 8);
			
			g.setColor(Color.GREEN);
			// Vehicles
			for (Vehicle v : r.getVehicles()) {
				int A = v.getLocation();
				int B = r.getLength();
				int carX = x1 + (int) ((x2 - x1) * ((double) A / (double) B));
				int carY = y - 10;

				g.drawImage(_car, carX, carY, 16, 16, this);
				g.drawString(v.getId(), carX, carY - 2);
			}

			// Weather image
			Image wImg = _weatherImgs[r.getWeather().ordinal()];
			g.drawImage(wImg, x2+15, y - 17, 32, 32, this);

			int A = r.getTotalCO2();
			int B = r.getContLimit();
			int C = (int) Math.floor(Math.min((double) A / (1.0 + (double) B), 1.0) / 0.19);
			C = Math.min(C, 5);
			g.drawImage(_contaminationImgs[C], x2+50, y - 17, 32, 32, this);

			i++;
		}
	}

	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}

	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(//
			Graphics g, //
			int x1, int y1, //
			int x2, int y2, //
			int w, int h, //
			Color lineColor, Color arrowColor) {

		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		update(map);
	}

}
