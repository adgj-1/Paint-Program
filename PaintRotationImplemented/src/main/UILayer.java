package main;

import java.awt.Color;

public class UILayer {

	private static int lastX = 0;
	private static int lastY = 0;
	
	static Color selection = new Color(0,0,0,126);
	
	public static void update() {
		try {
		if (Canvas.mouseX >=0 && Canvas.mouseX < Paint.img.getWidth() && Canvas.mouseY >=0 && Canvas.mouseY < Paint.img.getHeight()) {
			Paint.uiLayer.setRGB(lastX, lastY, Color.TRANSLUCENT);
			
			Paint.uiLayer.setRGB(Canvas.mouseX, Canvas.mouseY, selection.getRGB());
			lastX = Canvas.mouseX;
			lastY = Canvas.mouseY;
		}
		} catch (Exception e) {
			
		}
	}
}
