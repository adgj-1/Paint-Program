package tools;

import java.awt.Color;

import dialog.EraserMenu;
import main.Canvas;
import main.Main;
import main.Paint;
import main.Tool;

public class Eraser extends Tool {
	
	private boolean mouseDown;
	
	public static int size = 10;
	
	public static boolean isAlpha = true;
	public void init() {
		mouseDown = false;
		Paint.enableRotation = true;
		EraserMenu m = new EraserMenu(Main.j);
		m.setVisible(true);
		size = Paint.defaultSize;
	}

	@Override
	public void clickEvent() {
		// TODO Auto-generated method stub
		super.clickEvent();
		size = Paint.defaultSize;
		mouseDown = true;
		
	}

	public void upEvent() {
		mouseDown = false;
	}
	
	@Override
	public void moveEvent() {
		// TODO Auto-generated method stub
		if (mouseDown) {
			
			for (int i = Canvas.mouseX - size; i < Canvas.mouseX + size; i++) {
				for (int j = Canvas.mouseY - size; j < Canvas.mouseY + size; j++) {
					if (i >= 0 && i < Paint.img.getWidth() && j >= 0 && j < Paint.img.getHeight()) {
						if (isAlpha) {
							Paint.img.setRGB(i, j, Color.TRANSLUCENT);
						} else {
							Paint.img.setRGB(i, j, Color.WHITE.getRGB());
						}
					}
				}
			}
		}
	}

	
	
	@Override
	public void updateEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Eraser";
	}
	
}
