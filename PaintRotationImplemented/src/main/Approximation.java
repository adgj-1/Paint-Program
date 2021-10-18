package main;

import java.awt.Color;
import java.awt.Graphics;

public class Approximation {

	public static boolean isOn = false;
	
	private static double x = 0;
	private static double y = 0;
	public static double step = 20;
	public static void update() {
		if (!isOn) {
			return;
		}
		
		int rmouseX = (int) (x/Canvas.imageScale)-(int)(Canvas.imagePosX/Canvas.imageScale);
		int rmouseY = (int) (y/Canvas.imageScale) - (int)(Canvas.imagePosY/Canvas.imageScale);
		if (Main.rotator.angle != 0) {
			int[] newPoints = Main.rotator.rotatedPos((int)x, (int)y);
			Canvas.mouseX = newPoints[0];
			Canvas.mouseY = newPoints[1];
		} else {
			Canvas.mouseX = rmouseX;
			Canvas.mouseY = rmouseY;
		}
		
		x += ((double)Canvas.rawMouseX - (double)x)/step;
		y += ((double)Canvas.rawMouseY - (double)y)/step;
		if (Math.abs(Canvas.rawMouseX - (int)x) < 1) {
			x = Canvas.rawMouseX;
		}
		
		if (Math.abs(Canvas.rawMouseY - (int)y) < 1) {
			y = Canvas.rawMouseY;
		}
		
		if (Canvas.rawMouseX != (int) x || Canvas.rawMouseY != (int) y) {
			if (ToolManager.getActiveTool() != null) {
				ToolManager.getActiveTool().moveEvent();
			}
		}
		
	}
	
	public static void drawCursor(Graphics g) {
		if (!isOn) {
			return;
		}
		g.setColor(new Color(0,0,0,125));
		g.fillRect((int)x, (int)y, 10, 10);
	}
	
	public static int getX() {
		return (int)x;
	}
	
	public static int getY() {
		return (int)y;
	}
}
