package tools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Canvas;
import main.Paint;
import main.Tool;
import main.TypeLine;

public class MixerBrush extends Tool implements TypeLine {
	
	private boolean mouseDown;
	
	private int lastX;
	
	private int lastY;
	
	private static int size;
	
	private List<Integer[]> save = new ArrayList<Integer[]>();
	
	public void init() {
//		MixerBrushMenu m = new MixerBrushMenu(Main.j);
//		m.setVisible(true);
		size = Paint.defaultSize;
		mouseDown = false;
		Paint.enableRotation = true;
	}

	@Override
	public void clickEvent() {
		super.clickEvent();
		// TODO Auto-generated method stub
		size = Paint.defaultSize;
		mouseDown = true;
		lastX = Canvas.mouseX;
		lastY = Canvas.mouseY;
		doLocationAction(lastX,lastY);
		
	}

	public void upEvent() {
		mouseDown = false;
		save.clear();
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
		return "MixerBrush";
	}


	public int setTransparency(int rgb, int alpha) {
		Color c = new Color(rgb, true);
		
		Color res = new Color(c.getRed(),c.getGreen(),c.getBlue(),alpha);
		return res.getRGB();
	}
	
	private int mix(int pixelID ,int nextRGB) {
		Color next = new Color(nextRGB,true);
		int res = 0;
		int alpha = next.getAlpha();
		int red = next.getRed();
		int green = next.getGreen();
		int blue = next.getBlue();
		
		if (save.size() > 20) {
			save.remove(0);
		}
		for (int i = 0; i < save.size(); i++) {
			if (save.get(i) == null || save.get(i)[pixelID] == null) {
				int index = i-1;
				
				while (index >= 0) {
					if (index < save.size() && save.get(index) != null && save.get(index)[pixelID] != null) {
						return save.get(index)[pixelID];
						
					}
					index--;
				}
				return 0;
			}
			Color past = null;
			try{
				past = new Color(save.get(i)[pixelID]);
			} catch (Exception e) {
				past = new Color(0,0,0,0);
			}
			
			//int div = (save.size() - i);
			if (next.getAlpha() != 0) {
				double alphaRatio = (double)past.getAlpha() / (double)next.getAlpha();
				red += past.getRed() * alphaRatio;
				green += past.getGreen() * alphaRatio;
				blue += past.getBlue() * alphaRatio;
			} else {
				red = past.getRed() * 2;
				green = past.getGreen() * 2;
				blue = past.getBlue() * 2;
			}
//			alpha = next.getAlpha();
			alpha = (int)((past.getAlpha() * 9 + next.getAlpha() * 0.1) / 2);
//			System.out.println(alpha);
//			red *= (1d + 1d/div)/2d;
//			green *= (1d + 1d/div)/2d;
//			blue *= (1d + 1d/div)/2d;
			
			red/=2;
			green/=2;
			blue/=2;
//			red = (int)(((double)red / div) * (div-1) + ((double)past.getRed()/div));
//			green = (int)(((double)green / div) * (div-1) + ((double)past.getGreen()/div));
//			blue = (int)(((double)blue / div) * (div-1) + ((double)past.getBlue()/div));
			
		}
		
		if (red > 255) {
			red = 255;
		}
		
		if (green > 255) {
			green = 255;
		}
		
		if (blue > 255) {
			blue = 255;
		}
		
		if (alpha > 255) {
			alpha = 255;
		}
		Color rc = new Color(red,green,blue,alpha);
		res = rc.getRGB();
		return res;
	}
	
	
	
	public int getIDNumber() {
		int res = 0;
		int mid = size/2;
		for (int i = 0; i <= size; i++) {
			for (int j = 0; j <= size; j++) {
				int dist = Paint.hyp(i-mid,j-mid);
				if (dist <= mid) {
					
					res++;
				}
			}
		}
		
		return res;
	}
	
	@Override
	public void doLocationAction(int x, int y) {
		Integer[] newData = new Integer[getIDNumber()];
		int mid = size/2;
		int fillColor;
		int pixelID = 0;
		for (int i = 0; i <= size; i++) {
			for (int j = 0; j <= size; j++) {
				int dist = Paint.hyp(i-mid,j-mid);
				if (dist <= mid) {
					
					int pX = x + i - mid;
					int pY = y + j - mid;
					if (pX >= 0 && pX < Paint.img.getWidth() && pY >= 0 && pY < Paint.img.getHeight()) {
						newData[pixelID] = Paint.img.getRGB(pX, pY);
						//int fillRGB = setTransparency(Main.colorSelected.getRGB(),255 - (int)(255 * ((double)(dist * 0.9)/mid)));
						fillColor = mix(pixelID,Paint.img.getRGB(pX, pY));
						Paint.img.setRGB(pX,pY , fillColor);
					}
					pixelID++;
				}
			}
		}
		save.add(newData);
		
	}
	
	public static void setSize(int s) {
		size = s;
	}
}
