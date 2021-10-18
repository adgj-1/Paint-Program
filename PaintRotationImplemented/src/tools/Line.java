package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import main.Canvas;
import main.Clickable;
import main.HasUI;
import main.Main;
import main.Paint;
import main.Tool;
import main.TypeLine;
import main.Typeable;

public class Line extends Tool implements Typeable, Clickable, TypeLine, HasUI {
	
	public static int size = 10;
	
	private int x1 = -1;
	private int y1 = -1;
	private int x2 = -1;
	private int y2 = -1;
	
	private int rx1 = -1;
	private int ry1 = -1;
	private int rx2 = -1;
	private int ry2 = -1;
	
	public void init() {
		size = Paint.defaultSize;
		x1 = -1;
		y1 = -1;
		x2 = -1;
		y2 = -1;
		
		rx1 = -1;
		ry1 = -1;
		rx2 = -1;
		ry2 = -1;
		
		Paint.enableRotation = true;
	}

	@Override
	public void clickEvent() {
		// TODO Auto-generated method stub
		size = Paint.defaultSize;
		
	}

	public void upEvent() {
	}
	
	@Override
	public void moveEvent() {
		// TODO Auto-generated method stub
	}

	
	
	@Override
	public void updateEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Line";
	}

	@Override
	public void click(MouseEvent m) {
		// TODO Auto-generated method stub
		if (!m.isMetaDown()) {
			x1 = Canvas.mouseX;//(int) ((m.getX()-Canvas.imagePosX) / Canvas.imageScale);
			y1 = Canvas.mouseY;//(int) ((m.getY()-Canvas.imagePosY) / Canvas.imageScale);
			
			rx1 = (int) ((m.getX()-Canvas.imagePosX) / Canvas.imageScale);
			ry1 = (int) ((m.getY()-Canvas.imagePosY) / Canvas.imageScale);
			
		} else {
			x2 = Canvas.mouseX;//(int) ((m.getX()-Canvas.imagePosX) / Canvas.imageScale);
			y2 = Canvas.mouseY;//(int) ((m.getY()-Canvas.imagePosY) / Canvas.imageScale);
			
			rx2 = (int) ((m.getX()-Canvas.imagePosX) / Canvas.imageScale);
			ry2 = (int) ((m.getY()-Canvas.imagePosY) / Canvas.imageScale);
		}
	}

	@Override
	public void down(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SHIFT) {
			super.clickEvent();
			finishDrawing();
		}
	}

	@Override
	public void up(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doLocationAction(int x, int y) {
		// TODO Auto-generated method stub
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int scopeX = (size/2) - i;
				int scopeY = (size/2) - j;
				if (Math.sqrt(scopeX * scopeX + scopeY * scopeY) < size/2) {
					Paint.img.setRGB(x + i - size/2, y + j - size/2, Main.colorSelected.getRGB());
				}
			}
		}
	}

	@Override
	public void drawUI(Graphics g) {
		// TODO Auto-generated method stub
		int rawx1 = (int) ((rx1 * Canvas.imageScale) + Canvas.imagePosX);
		int rawy1 = (int) ((ry1 * Canvas.imageScale) + Canvas.imagePosY);
		int rawx2 = (int) ((rx2 * Canvas.imageScale) + Canvas.imagePosX);
		int rawy2 = (int) ((ry2 * Canvas.imageScale) + Canvas.imagePosY);
		g.setColor(Color.BLACK);
		g.drawLine(rawx1 - 5, rawy1, rawx1 + 5, rawy1);
		g.drawLine(rawx1, rawy1 -5, rawx1, rawy1 + 5);
		g.setColor(Color.BLUE);
		g.drawLine(rawx2 - 5, rawy2, rawx2 + 5, rawy2);
		g.drawLine(rawx2, rawy2 -5, rawx2, rawy2 + 5);
		g.setColor(Color.RED);
		if (x1 != -1 && y1 != -1 && x2 != -1 && y2 != -1) {
			g.drawLine(rawx1, rawy1, rawx2, rawy2);
		}
	}
	
	public void finishDrawing() {
		if (x1 == -1 || y1 == -1 || x2 == -1 || y2 == -1) {
			x1 = -1;
			y1 = -1;
			x2 = -1;
			y2 = -1;
			
			rx1 = -1;
			ry1 = -1;
			rx2 = -1;
			ry2 = -1;
			
			return;
		} else {
			Paint.drawLine(x1, y1, x2, y2, this);
			x1 = -1;
			y1 = -1;
			x2 = -1;
			y2 = -1;
			
			rx1 = -1;
			ry1 = -1;
			rx2 = -1;
			ry2 = -1;
		}
	}
	
}
