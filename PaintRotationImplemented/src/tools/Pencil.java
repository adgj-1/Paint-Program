package tools;

import main.Canvas;
import main.Main;
import main.Paint;
import main.Tool;
import main.TypeLine;

public class Pencil extends Tool implements TypeLine {
	
	private boolean mouseDown;
	
	private int lastX;
	
	private int lastY;
	
	public void init() {
		mouseDown = false;
		Paint.enableRotation = true;
	}

	@Override
	public void clickEvent() {
		super.clickEvent();
		// TODO Auto-generated method stub
		mouseDown = true;
		lastX = Canvas.mouseX;
		lastY = Canvas.mouseY;
		Paint.img.setRGB(lastX, lastY, Main.colorSelected.getRGB());
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
		return "Pencil";
	}

	@Override
	public void doLocationAction(int x, int y) {
		
		Paint.img.setRGB(x, y, Main.colorSelected.getRGB());
		
	}
	
	
}
