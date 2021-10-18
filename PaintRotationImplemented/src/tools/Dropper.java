package tools;

import java.awt.Color;

import main.Canvas;
import main.Main;
import main.Paint;
import main.Tool;

public class Dropper extends Tool {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Paint.enableRotation = true;
	}

	public void clickEvent() {
		super.clickEvent();
		Main.colorSelected = new Color(Paint.img.getRGB(Canvas.mouseX, Canvas.mouseY),false);
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

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Dropper";
	}

}
