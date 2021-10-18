package main;

public abstract class Tool {
	
	public abstract void init();
	
	public void clickEvent() {
		Paint.lastImg = Main.copyImage(Paint.img);
	}
	
	public abstract void upEvent();
	
	public abstract void moveEvent();
	
	public abstract void updateEvent();
	
	public void scrollUp() {
		
	}
	
	public void scrollDown() {
		
	}
	
	public abstract String getName();
	
}
