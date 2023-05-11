package main;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import dialog.BrushSelectorMenu;
import dialog.ErrorMessage;
import tools.Crop;
import util.BrushSelector;
import util.brush.Brush;

public class Paint {
	
	
	private static boolean isPlaying;
	
	public static BufferedImage img;
	
	public static BufferedImage lastImg;
	
	public static BufferedImage uiLayer;
	
	public static List<BufferedImage> layers = new ArrayList<BufferedImage>();
	
	public static List<Boolean> lVisible = new ArrayList<Boolean>();
	
	public static List<JPanel> viewables = new ArrayList<JPanel>();
	
	public static int currentLayer = 0;
	
	public static int defaultSize = 10;
	
	public static int defaultTransparency = 50;
	
	public static boolean enableRotation = true;
	
	public static void init() {
		Main.colorSelected = Color.BLACK;
		Canvas.alpha = Color.GRAY;
		ToolManager.loadTools();
		BrushSelector.getBrushList().add(new Brush(null));
		BrushSelectorMenu.loadExternalBrush();
	}
	
	public static void start() {
		
		isPlaying = true;
		
		while (isPlaying) {
			
			update();
			for (JPanel p : viewables) {
				p.repaint();
			}
			Main.rotator.repaint();
			Main.c.repaint();
			Main.miniCanvas.repaint();
			
			
			
			try {
				Thread.sleep(5);
			} catch(InterruptedException e) {
				
			}
			
		}
	}
	
	public static void stop() {
		isPlaying = false;
	}
	
	public static void update() {
		SelectedColor.updateComponent();
		Approximation.update();
		Main.rotator.update();
		if (uiLayer != null) {
			UILayer.update();
		}
		
		if (Main.j.getHeight() < 600) {
			Main.rotator.setVisible(false);
		} else {
			Main.rotator.setVisible(true);
		}
	}
	
	
	public static void clear() {
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				img.setRGB(j, i, Color.WHITE.getRGB());
			}
		}
	}
	
	public static void undo() {
		
		if (lastImg != null) {
			BufferedImage temp = img;
			img = lastImg;
			layers.set(currentLayer, lastImg);
			lastImg = temp;
		}
	}
	
	public static void throwError(String err) {
		ErrorMessage m = new ErrorMessage(Main.j,err);
		m.setVisible(true);
	}
	
	public static void drawLine(int x1, int y1, int x2, int y2, TypeLine l) {
		int step = hyp(x1 - x2, y1 - y2);
		double countX = x1;
		double countY = y1;
		for (int i = 0; i <= step; i++) {
			if (countX >= Paint.img.getWidth()) {
				countX = Paint.img.getWidth() - 1;
			}
			
			if (countY >= Paint.img.getHeight()) {
				countY = Paint.img.getHeight() - 1;
			}
			
			if (countX < 0) {
				countX = 0;
			}
			
			if (countY < 0) {
				countY = 0;
			}
			
			l.doLocationAction((int)countX, (int)countY);
			
			countX += (double)(x2 - x1) / step;
			countY += (double)(y2 - y1) / step;
		}
	}
	
	/**
	 * A DrawLine Function That Doesn't Have Border Restriction
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param l
	 */
	public static void drawLine2(int x1, int y1, int x2, int y2, TypeLine l) {
		int step = hyp(x1 - x2, y1 - y2);
		double countX = x1;
		double countY = y1;
		for (int i = 0; i <= step; i++) {
			
			l.doLocationAction((int)countX, (int)countY);
			
			countX += (double)(x2 - x1) / step;
			countY += (double)(y2 - y1) / step;
		}
	}
	
	public static int hyp(int x, int y) {
		x *= x;
		y *= y;
		
		return (int)(Math.sqrt(x + y)+0.5);
	}
	
	public static void createLayer() {
		layers.add(new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB));
		lVisible.add(true);
		
		
		currentLayer = layers.size() - 1;
		img = layers.get(currentLayer);
		LayerManager.add();
	}
	
	public static void createLayer(BufferedImage i) {
		layers.add(i);
		lVisible.add(true);
		
		
		currentLayer = layers.size() - 1;
		img = layers.get(currentLayer);
		Paint.uiLayer = new BufferedImage(Math.max(Paint.img.getWidth(),i.getWidth()), Math.max(Paint.img.getHeight(),i.getHeight()), BufferedImage.TYPE_INT_ARGB);
		LayerManager.add();
	}
	
	public static void screenCapture() {
		BufferedImage screen = null;
		try {
			screen = getScreenImage();
		} catch (AWTException e) {
			throwError(e.getLocalizedMessage());
			return;
		}
		
		for (int i = 0; i < layers.size(); i++) {
			layers.set(i, Crop.crop(layers.get(i), 0, 0, screen.getWidth(), screen.getHeight()));
		}
		createLayer(screen);
	}
	
	public static BufferedImage getScreenImage() throws AWTException {
		Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = new Robot().createScreenCapture(rect);
		return capture;
	}
	
}
