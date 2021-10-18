package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

import main.Paint;
import main.Tool;

public class Contrast extends Tool {

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
		BufferedImage img = Paint.img;
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color pixelColor = new Color(img.getRGB(i, j),true);
				int r = pixelColor.getRed();
				int g = pixelColor.getGreen();
				int b = pixelColor.getBlue();
				int a = pixelColor.getAlpha();
				
				int t = r + g + b;
//				r *= ((double)t/377);
//				g *= ((double)t/377);
//				b *= ((double)t/377);
				r += (((double)t/377)-1)*5;
				g += (((double)t/377)-1)*5;
				b += (((double)t/377)-1)*5;
				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				if (b > 255) {
					b = 255;
				}
				
				if (r < 0) {
					r = 0;
				}
				if (g < 0) {
					g = 0;
				}
				if (b < 0) {
					b = 0;
				}
				img.setRGB(i, j, new Color(r,g,b,a).getRGB());
			}
		}
	}
	
	public void scrollDown() {
		BufferedImage img = Paint.img;
		for (int i = 0; i < img.getWidth(); i++) {
			for (int j = 0; j < img.getHeight(); j++) {
				Color pixelColor = new Color(img.getRGB(i, j),true);
				int r = pixelColor.getRed();
				int g = pixelColor.getGreen();
				int b = pixelColor.getBlue();
				int a = pixelColor.getAlpha();
				
				int t = r + g + b;
				r -= (((double)t/377)-1)*5;
				g -= (((double)t/377)-1)*5;
				b -= (((double)t/377)-1)*5;
				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				if (b > 255) {
					b = 255;
				}
				if (r < 0) {
					r = 0;
				}
				if (g < 0) {
					g = 0;
				}
				if (b < 0) {
					b = 0;
				}
				img.setRGB(i, j, new Color(r,g,b,a).getRGB());
			}
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Contrast";
	}

}
