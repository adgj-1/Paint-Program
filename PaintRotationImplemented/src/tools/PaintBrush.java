package tools;

import java.awt.Color;

import main.Canvas;
import main.Main;
import main.Paint;
import main.Tool;
import main.TypeLine;

public class PaintBrush extends Tool implements TypeLine {
	
	private boolean mouseDown;
	
	private int lastX;
	
	private int lastY;
	
	private static int size;
	
	public void init() {
//		PaintBrushMenu m = new PaintBrushMenu(Main.j);
//		m.setVisible(true);
		
		size =Paint.defaultSize;
		mouseDown = false;
		Paint.enableRotation = true;
	}

	@Override
	public void clickEvent() {
		super.clickEvent();
		// TODO Auto-generated method stub
		size =Paint.defaultSize;
		mouseDown = true;
		
		lastX = Canvas.mouseX;
		lastY = Canvas.mouseY;
		doLocationAction(lastX,lastY);
	}

	public void upEvent() {
		mouseDown = false;
	}
	
	@Override
	public void moveEvent() {
		// TODO Auto-generated method stub
		if (mouseDown) {
			
			
			Paint.drawLine(Canvas.mouseX,Canvas.mouseY,lastX,lastY, this);
			
			lastX = Canvas.mouseX;
			lastY = Canvas.mouseY;
		}
	}

	
	
	
	@Override
	public void updateEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "PaintBrush";
	}

	
	public static int blendColor(int rgb1, int rgb2, double ratio) {
		Color c1 = new Color(rgb1, true);
		Color c2 = new Color(rgb2, true);
		int alpha = c1.getAlpha() + c2.getAlpha();
		int a1 = c1.getAlpha();
		int a2 = c2.getAlpha();
		ratio = ratio * 2 * ((double)a1/(a2+a1));
		//System.out.println(ratio + " a1: " + a1 + " a2: " + a2);
		int red = (int) (c1.getRed()*ratio + c2.getRed()*(2-ratio));
		red/=2;
		if (red > 255) {
			red = 255;
		}
		
		int green = (int) (c1.getGreen()*ratio + c2.getGreen()*(2-ratio));
		green/=2;
		if (green > 255) {
			green = 255;
		}
		
		int blue = (int) (c1.getBlue()*ratio + c2.getBlue()*(2-ratio));
		blue/=2;
		if (blue > 255) {
			blue = 255;
		}
		
		if (alpha > 255) {
			alpha = 255;
		}
		Color resc = new Color(red, green, blue, alpha);
		return resc.getRGB();
	}
	public int setTransparency(int rgb, int alpha) {
		Color c = new Color(rgb, true);
		
		Color res = new Color(c.getRed(),c.getGreen(),c.getBlue(),alpha);
		return res.getRGB();
	}
	
	
	@Override
	public void doLocationAction(int x, int y) {
		int mid = size/2;
		int fillColor;
		for (int i = 0; i <= size; i++) {
			for (int j = 0; j <= size; j++) {
				int dist = Paint.hyp(i-mid,j-mid);
				if (dist <= mid) {
					
					int pX = x + i - mid;
					int pY = y + j - mid;
					if (pX >= 0 && pX < Paint.img.getWidth() && pY >= 0 && pY < Paint.img.getHeight()) {
						int fillRGB = setTransparency(Main.colorSelected.getRGB(),255 - (int)(255 * ((double)(dist * 0.9)/mid)));
						fillColor = blendColor(Paint.img.getRGB(pX, pY),fillRGB,(double)dist/mid);
						Paint.img.setRGB(pX,pY , fillColor);
					}
				}
			}
		}
		
		
	}
	
	public static void setSize(int s) {
		size = s;
	}
}
