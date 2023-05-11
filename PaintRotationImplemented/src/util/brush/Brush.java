package util.brush;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import util.BrushSelector;

public class Brush {
	private BufferedImage shape;
	private boolean inFile;
	private int px;
	private int py;
	//private Font font = new Font();
	public Brush(BufferedImage shape) {
		this.shape = shape;
		inFile = false;
	}
	
	public void draw(Graphics g, int x, int y) {
		if (BrushSelector.getCurrentBrush() != null && BrushSelector.getCurrentBrush().equals(this)) {
			g.setColor(Color.CYAN);
			g.fillRect(x, y, 50, 50);
			
		}
		if (shape == null) {
			g.setColor(Color.DARK_GRAY);
			g.drawString("Default", x + 5, y+25);
		} else {
			g.drawImage(shape, x, y, 50,50, null);
		}
		
		if (inFile) {
			g.setColor(Color.MAGENTA);
			g.setFont(new Font("Calibri",Font.BOLD,20));
			g.drawString("F", x+5, y + 25);
		}
		px = x;
		py = y;
	}
	
	public void existInFile() {
		inFile = true;
	}
	
	public boolean isInFile() {
		return inFile;
	}
	
	public BufferedImage getShape() {
		return shape;
	}
	
	public boolean click(int x, int y) {
		return (x > px && x < px + 50 && y > py && y < py + 50);
	}
}
