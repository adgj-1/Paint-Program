package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Canvas;
import main.Clickable;
import main.HasUI;
import main.Paint;
import main.Tool;
import main.Typeable;

public class Crop extends Tool implements Typeable, Clickable, HasUI {
	
	public static int size = 10;
	
	private int x1 = -1;
	private int y1 = -1;
	private int x2 = -1;
	private int y2 = -1;
	
	public void init() {
		size = Paint.defaultSize;
		x1 = -1;
		y1 = -1;
		x2 = -1;
		y2 = -1;
		Paint.enableRotation = false;
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
		return "Crop";
	}

	@Override
	public void click(MouseEvent m) {
		// TODO Auto-generated method stub
		if (!m.isMetaDown()) {
			x1 = (int) ((m.getX()-Canvas.imagePosX) / Canvas.imageScale);
			y1 = (int) ((m.getY()-Canvas.imagePosY) / Canvas.imageScale);
		} else {
			x2 = (int) ((m.getX()-Canvas.imagePosX) / Canvas.imageScale);
			y2 = (int) ((m.getY()-Canvas.imagePosY) / Canvas.imageScale);
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
	public void drawUI(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.RED);
		int posX1 = Canvas.imagePosX;
		int posY1 = Canvas.imagePosY;
		int posX2 = (int) ((Paint.img.getWidth() * Canvas.imageScale) + Canvas.imagePosX);
		int posY2 = (int) ((Paint.img.getHeight() * Canvas.imageScale) + Canvas.imagePosY);
		g.drawLine(posX1, posY1, posX2, posY1);
		g.drawLine(posX1, posY2, posX2, posY2);
		g.drawLine(posX1, posY1, posX1, posY2);
		g.drawLine(posX2, posY1, posX2, posY2);
		
		
		int rawx1 = (int) ((x1 * Canvas.imageScale) + Canvas.imagePosX);
		int rawy1 = (int) ((y1 * Canvas.imageScale) + Canvas.imagePosY);
		int rawx2 = (int) ((x2 * Canvas.imageScale) + Canvas.imagePosX);
		int rawy2 = (int) ((y2 * Canvas.imageScale) + Canvas.imagePosY);
		g.setColor(Color.BLACK);
		g.drawLine(rawx1 - 5, rawy1, rawx1 + 5, rawy1);
		g.drawLine(rawx1, rawy1 -5, rawx1, rawy1 + 5);
		g.setColor(Color.BLUE);
		g.drawLine(rawx2 - 5, rawy2, rawx2 + 5, rawy2);
		g.drawLine(rawx2, rawy2 -5, rawx2, rawy2 + 5);
		g.setColor(Color.RED);
		if (x1 != -1 && y1 != -1 && x2 != -1 && y2 != -1) {
			g.drawLine(rawx1, rawy1, rawx1, rawy2);
			g.drawLine(rawx2, rawy1, rawx2, rawy2);
			g.drawLine(rawx1, rawy1, rawx2, rawy1);
			g.drawLine(rawx1, rawy2, rawx2, rawy2);
			g.setColor(Color.CYAN);
			
			g.drawString("x: " + (Math.max(x1, x2) - Math.min(x1, x2)) + "  y: " + (Math.max(y1, y2) - Math.min(y1, y2)), Math.max(rawx1, rawx2) + 10, Math.max(rawy1, rawy2) + 5);
		}
	}
	
	public void finishDrawing() {
		if (x1 == -1 || y1 == -1 || x2 == -1 || y2 == -1) {
			x1 = -1;
			y1 = -1;
			x2 = -1;
			y2 = -1;
			return;
		} else {
			for (int i = 0; i < Paint.layers.size(); i++) {
				Paint.layers.set(i,crop(Paint.layers.get(i),x1, y1, x2, y2));
			}
			Paint.img = Paint.layers.get(Paint.currentLayer);
			x1 = -1;
			y1 = -1;
			x2 = -1;
			y2 = -1;
		}
	}
	
	public static BufferedImage crop(BufferedImage img, int xa, int ya, int xb, int yb) {
		int x2 = Math.max(xa, xb);
		int x1 = Math.min(xa, xb);
		int y2 = Math.max(ya, yb);
		int y1 = Math.min(ya, yb);
		
		int offsetX = 0;
		int offsetY = 0;
		if (x1 < 0) {
			offsetX = 0 - x1;
		}
		if (y1 < 0) {
			offsetY = 0 - y1;
		}
		BufferedImage newImage = new BufferedImage(x2 - x1,y2 - y1,BufferedImage.TYPE_INT_ARGB);
		Paint.uiLayer = new BufferedImage(x2 - x1,y2 - y1,BufferedImage.TYPE_INT_ARGB);
		x1 = Math.max(x1,0);
		y1 = Math.max(y1,0);
		x2 = Math.min(x2, img.getWidth());
		y2 = Math.min(y2, img.getHeight());
		BufferedImage subImage = img.getSubimage(x1, y1, x2 - x1, y2 - y1);
		
		for (int i = 0; i < subImage.getWidth(); i++) {
			for (int j = 0; j < subImage.getHeight(); j++) {
				newImage.setRGB(i + offsetX, j + offsetY, subImage.getRGB(i, j));
			}
		}
		return newImage;
	}
	
}
