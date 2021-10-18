package main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import dialog.ColorMenu;

public class ColorSelector extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public void paint(Graphics gr) {
		update(gr);
	}
	
	public void update(Graphics gr) {
		
		gr.setColor(new Color(ColorMenu.cR,ColorMenu.cG,ColorMenu.cB));
		gr.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
