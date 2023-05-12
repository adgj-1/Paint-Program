package tools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import main.Canvas;
import main.Main;
import main.Paint;
import main.Tool;
import util.Vector2;

public class PaintBucket extends Tool {
	
	private static final int SIZE = 250;
	
	public void init() {
		Paint.enableRotation = true;
	}

	@Override
	public void clickEvent() {
		super.clickEvent();
		// TODO Auto-generated method stub
		
		int oldRGB = Paint.img.getRGB(Canvas.mouseX, Canvas.mouseY);
		int newRGB = Main.colorSelected.getRGB();
		try {
		//fill(Canvas.mouseX,Canvas.mouseY,oldRGB,newRGB,Canvas.mouseX,Canvas.mouseY);
		//loopFill(Canvas.mouseX,Canvas.mouseY,new Color(oldRGB),new Color(newRGB), Canvas.mouseX, Canvas.mouseY);
		tailRecursiveFill(Canvas.mouseX,Canvas.mouseY,oldRGB,newRGB);
		} catch (StackOverflowError e) {
			Paint.undo();
			Paint.throwError(e.toString());
		}
		
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
	
	private void tailRecursiveFill(int x, int y, int originalRGB, int newRGB) {
		List<Vector2> frontier = new ArrayList<Vector2>();
		frontier.add(new Vector2(x,y));
		
		while (!frontier.isEmpty()) {
			Vector2 loc = frontier.remove(0);
			int xi = loc.getX();
			int yi = loc.getY();
			
			if (originalRGB != newRGB && Paint.img.getRGB(xi, yi) == originalRGB) {
				
				Paint.img.setRGB(xi, yi, newRGB);
				
				if (xi > 0) {
					frontier.add(new Vector2(xi-1, yi));
				}
				
				if (xi < Paint.img.getWidth() - 1) {
					frontier.add(new Vector2(xi+1, yi));
				}
				
				if (yi > 0) {
					frontier.add(new Vector2(xi, yi-1));
				}
				
				
				if (yi < Paint.img.getHeight() - 1) {
					frontier.add(new Vector2(xi, yi+1));
				}
			}
			
		}
	}
	
	/*
	 * Deprecated, kept for future references
	 */
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
	
	/*
	 * Deprecated, kept for future references
	 */
	@SuppressWarnings("unused")
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


