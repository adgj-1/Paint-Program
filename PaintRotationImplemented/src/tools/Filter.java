package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import main.Main;
import main.Paint;
import main.Tool;

public class Filter extends Tool {

	private static int dif = 10;
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		Paint.enableRotation = false;
	}

	@Override
	public void upEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEvent() {
		// TODO Auto-generated method stub
		
	}

	public void scrollUp() {
		modify();
	}
	
	public void scrollDown() {
		modify();
	}
	
	public void modify() {
		dif = Paint.defaultSize;
		BufferedImage img = Paint.img;
		Color c = Main.colorSelected;
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				
				Color pixelColor = new Color(img.getRGB(i, j),true);
				int r = pixelColor.getRed();
				int g = pixelColor.getGreen();
				int b = pixelColor.getBlue();
				int a = pixelColor.getAlpha();
				
				int t = r + g + b;
				if (Math.abs(r - c.getRed()) < dif && Math.abs(g - c.getGreen()) < dif && Math.abs(b - c.getBlue()) < dif) {
					
				} else {
					r = 255;
					g = 255;
					b = 255;
				}
				
				img.setRGB(i, j, new Color(r,g,b,a).getRGB());
			}
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Filter";
	}

}
