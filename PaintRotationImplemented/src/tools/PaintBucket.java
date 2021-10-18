package tools;

import java.awt.Color;

import main.Canvas;
import main.Main;
import main.Paint;
import main.Tool;

public class PaintBucket extends Tool {
	
	private static final int SIZE = 250;
	
	public void init() {
		Paint.enableRotation = true;
	}

	@Override
	public void clickEvent() {
		super.clickEvent();
		// TODO Auto-generated method stub
		
		int oldRGB = Paint.img.getRGB(Canvas.mouseX, Canvas.mouseX);
		int newRGB = Main.colorSelected.getRGB();
		try {
		//fill(Canvas.mouseX,Canvas.mouseY,oldRGB,newRGB,Canvas.mouseX,Canvas.mouseY);
		loopFill(Canvas.mouseX,Canvas.mouseY,new Color(oldRGB),new Color(newRGB), Canvas.mouseX, Canvas.mouseY);
		} catch (StackOverflowError e) {
			Paint.undo();
			Paint.throwError(e.toString());
		}
		
//		Color oldRGB = new Color(Paint.img.getRGB(Canvas.mouseX, Canvas.mouseY),true);
//		Color newRGB = Main.colorSelected;
//		loopFill(Canvas.mouseX,Canvas.mouseY,oldRGB,newRGB);
	}

	public void upEvent() {
	}
	
	@Override
	public void moveEvent() {
		
	}

	@Override
	public void updateEvent() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "PaintBucket";
	}
	
	@SuppressWarnings("unused")
	private void fill(int x, int y, int originalRGB, int newRGB, int oX, int oY) {
		if (originalRGB != newRGB && Paint.img.getRGB(x, y) == originalRGB) {
			
			Paint.img.setRGB(x, y, newRGB);
			
			if (x > 0 && oX - x < SIZE) {
				 fill(x - 1, y, originalRGB,newRGB, oX,oY);
			}
			
			
			if (x < Paint.img.getWidth() - 1 && x - oX < SIZE) {
				fill(x + 1, y, originalRGB,newRGB, oX,oY);
			}
			
			if (y > 0 && oY - y < SIZE) {
				fill(x, y - 1, originalRGB,newRGB, oX,oY);
			}
			
			
			if (y < Paint.img.getHeight() - 1 && y - oY < SIZE) {
				fill(x, y + 1, originalRGB,newRGB, oX,oY);
			}
		}
		
	}
	
	private void loopFill(int x, int y, Color originalRGB, Color newRGB,int oX, int oY) {
		boolean blocked;
		while (true) {
			
			
			if (x >= Paint.img.getWidth() - 1) {
				
				break;
			}
			if (originalRGB != newRGB && new Color(Paint.img.getRGB(x, y)).equals(originalRGB)) {
				
				blocked = false;
				if (!(new Color(Paint.img.getRGB(x + 1, y)).equals(originalRGB))) {
					blocked = true;
				}
				while (true) {
					
					if (originalRGB != newRGB && new Color(Paint.img.getRGB(x, y)).equals(originalRGB)) {
						Paint.img.setRGB(x, y, newRGB.getRGB());
					} else {
						break;
					}
					
					if (!(new Color(Paint.img.getRGB(x + 1, y)).equals(originalRGB))) {
						blocked = true;
					} else {
						if (blocked) {
							loopFill(x + 1,y,originalRGB, newRGB, x + 1, y);
							//System.out.println("recur");
							//blocked = false;
						}
					}
					
					if (y >= Paint.img.getHeight() - 1) {
						break;
					}
					y++;
				}
				y = oY;
				Paint.img.setRGB(x, oY, originalRGB.getRGB());
				blocked = false;
				while (true) {
					
					if (originalRGB != newRGB && new Color(Paint.img.getRGB(x, y)).equals(originalRGB)) {
						Paint.img.setRGB(x, y, newRGB.getRGB());
					} else {
						break;
					}
					
					if (!(new Color(Paint.img.getRGB(x + 1, y)).equals(originalRGB))) {
						blocked = true;
					} else {
						if (blocked) {
							loopFill(x + 1,y,originalRGB, newRGB, x + 1, y);
							//System.out.println("recur");
							//blocked = false;
						}
					}
					
					if (y <= 0) {
						break;
					}
					y--;
				}
				y = oY;
			} else {
				//System.out.println(new Color(Paint.img.getRGB(x, y)) + " " + newRGB + " " + originalRGB);
				break;
			}
			
			x++;
		}
		
		x = oX;
		Paint.img.setRGB(oX, y, originalRGB.getRGB());
		while (true) {
			
			
			if (x <= 0) {
				
				break;
			}
			if (originalRGB != newRGB && new Color(Paint.img.getRGB(x, y)).equals(originalRGB)) {
				
				blocked = false;
				if (!(new Color(Paint.img.getRGB(x - 1, y)).equals(originalRGB))) {
					blocked = true;
				}
				while (true) {
					
					if (originalRGB != newRGB && new Color(Paint.img.getRGB(x, y)).equals(originalRGB)) {
						Paint.img.setRGB(x, y, newRGB.getRGB());
					} else {
						break;
					}
					
					if (!(new Color(Paint.img.getRGB(x - 1, y)).equals(originalRGB))) {
						blocked = true;
					} else {
						if (blocked) {
							loopFill(x - 1,y,originalRGB, newRGB, x - 1, y);
							//System.out.println("recur");
							//blocked = false;
						}
					}
					
					if (y >= Paint.img.getHeight() - 1) {
						break;
					}
					y++;
				}
				y = oY;
				Paint.img.setRGB(x, oY, originalRGB.getRGB());
				
				blocked = false;
				
				while (true) {
					
					if (originalRGB != newRGB && new Color(Paint.img.getRGB(x, y)).equals(originalRGB)) {
						Paint.img.setRGB(x, y, newRGB.getRGB());
					} else {
						break;
					}
					
					if (!(new Color(Paint.img.getRGB(x - 1, y)).equals(originalRGB))) {
						blocked = true;
					} else {
						if (blocked) {
							loopFill(x - 1,y,originalRGB, newRGB, x - 1, y);
							//System.out.println("recur");
							//blocked = false;
						}
					}
					
					if (y <= 0) {
						break;
					}
					y--;
				}
				y = oY;
			} else {
				//System.out.println(new Color(Paint.img.getRGB(x, y)) + " " + newRGB + " " + originalRGB);
				break;
			}
			
			x--;
		}
	}
}


