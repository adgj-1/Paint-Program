package main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SelectedColor extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Color currentColor;
	
	public static void updateComponent() {
		if (Main.colorSelected != currentColor) {
			Main.j.repaint();
		}
	}
	
	public void paint(Graphics gr) {
		update(gr);
	}
	
	public void update(Graphics gr) {
		this.setBounds(this.getX(), this.getY(), 120, 60);
		gr.setColor(Main.colorSelected);
		currentColor = Main.colorSelected;
		gr.fillRect(0, 0,this.getWidth(),this.getHeight());
		//gr.fillRect((int)(this.getWidth() * 0.1), 0, (int)(this.getWidth() * 0.6), this.getHeight());
		
	}
	
}
