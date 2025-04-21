package simulator.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Kbtn extends JButton {
	private String icon;
	private String tooltip;
	
	public Kbtn(String icon, String tip) {
		this.icon = icon;
		this.tooltip = tip;
		
		this.paint();
		
		this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	setBackground(new Color(80, 80, 80));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	setBackground(new Color(34, 34, 34));
            }
        });
	}
	
	private void paint() {
		//Ugly stuff
		this.setFocusPainted(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		
		//Base stuff
		ImageIcon rawIcon = new ImageIcon(this.icon);
		Image scaledImg = rawIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		
		this.setIcon(scaledIcon);
		this.setPreferredSize(new Dimension(35, 35));
		this.setMinimumSize(new Dimension(35, 35));
		this.setMaximumSize(new Dimension(35, 35));
		this.setToolTipText(this.tooltip);

		// ~Styling~
		this.setBackground(new Color(34, 34, 34));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setOpaque(true);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setVerticalTextPosition(SwingConstants.CENTER);
		
	}
}
