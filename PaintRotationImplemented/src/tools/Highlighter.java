package tools;

import java.awt.Color;

import main.Canvas;
import main.LayerManager;
import main.Main;
import main.Paint;
import main.Tool;
import main.TypeLine;

public class Highlighter extends Tool implements TypeLine {
	
	private boolean mouseDown;
	
	private int lastX;
	
	private int lastY;
	
	private static int size;
	
	private static int alpha;
	
	//private List<Vector2> save = new ArrayList<Vector2>();
	
	private boolean[][] save;
	
	public void init() {
		save = new boolean[Paint.img.getWidth()][Paint.img.getHeight()];
//		HighlighterMenu m = new HighlighterMenu(Main.j);
//		m.setVisible(true);
		size = Paint.defaultSize;
		setTransparency(Paint.defaultTransparency);
		mouseDown = false;
		Paint.enableRotation = true;
	}

	@Override
	public void clickEvent() {
		super.clickEvent();
		// TODO Auto-generated method stub
		size = Paint.defaultSize;
		setTransparency(Paint.defaultTransparency);
		clearSave();
		mouseDown = true;
		lastX = Canvas.mouseX;
		lastY = Canvas.mouseY;
		doLocationAction(lastX,lastY);
		
	}

	public void upEvent() {
		//save.clear();
		clearSave();
		mouseDown = false;
	}
	
	
	public void clearSave() {
		for(int i = 0; i < save.length; i++) {
			for (int j = 0; j < save[i].length; j++) {
				save[i][j] = false;
			}
		}
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
		return "Highlighter";
	}

	
	public int setTransparency(int rgb, int alpha) {
		Color c = new Color(rgb, true);
		
		Color res = new Color(c.getRed(),c.getGreen(),c.getBlue(),alpha);
		return res.getRGB();
	}
	
	
	@Override
	public void doLocationAction(int x, int y) {
		int mid = size/2;
		
		for (int i = 0; i <= size; i++) {
			for (int j = 0; j <= size; j++) {
				int dist = Paint.hyp(i-mid,j-mid);
				if (dist <= mid) {
					
					int pX = x + i - mid;
					int pY = y + j - mid;
					if (pX >= 0 && pX < Paint.img.getWidth() && pY >= 0 && pY < Paint.img.getHeight()) {
						
						highlightPixel(pX,pY);
						
					}
				}
			}
		}
		
		
	}
	
	private void highlightPixel(int pX, int pY) {
//		for (int i = 0; i < save.size(); i++) {
//			if (save.get(i).getX() == pX && save.get(i).getY() == pY) {
//				return;
//			}
//		}
		
		if (save[pX][pY] == true) {
			return;
		}
		
		int fillColor;
		int fillRGB = setTransparency(Main.colorSelected.getRGB(),alpha);
		fillColor = LayerManager.stackColor(Paint.img.getRGB(pX, pY), fillRGB);
		Paint.img.setRGB(pX,pY , fillColor);
//		save.add(new Vector2(pX,pY));
		save[pX][pY] = true;
	}
	
	
	
	public static void setSize(int s) {
		size = s;
	}
	
	public static void setTransparency(int t) {
		alpha = (int)(((double)t/100) * 255);
	}
}
