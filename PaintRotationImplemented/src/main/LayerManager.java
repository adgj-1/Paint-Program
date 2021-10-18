package main;

import java.awt.Color;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.image.BufferedImage;

import actionListeners.MenuListener;

public abstract class LayerManager {

	public static Menu layerMenu;
	
	public static MenuListener l = Main.l;
	
	public static Menu getlayerMenu() {
		layerMenu = new Menu("Layers");

//		MenuItem m = new MenuItem("Delete Layer");
//		m.addActionListener(l);
//		layerMenu.add(m);
//		
//		MenuItem s = new MenuItem("Show/Hide Other Layers");
//		s.addActionListener(l);
//		layerMenu.add(s);
//		addAll();
		createMenu();
		return layerMenu;
	}
	public static void addAll() {
		for (int i = 0; i < Paint.layers.size(); i++) {
			MenuItem m = new MenuItem("#Layer" + i);
			m.addActionListener(l);
			layerMenu.add(m);
		}
	}
	public static void add() {
		MenuItem m = new MenuItem("#Layer" + (Paint.layers.size() - 1));
		m.addActionListener(l);
		layerMenu.add(m);
		Main.layerMenuSetup();
	}
	
	public static void deleteLayer() {
		if (Paint.layers.size() > 1) {
			Paint.layers.remove(Paint.currentLayer);
			Paint.lVisible.remove(Paint.currentLayer);
			Paint.currentLayer = 0;
			layerMenu.remove(layerMenu.getItemCount()-1);
			Paint.img = Paint.layers.get(Paint.currentLayer);
			Main.layerMenuSetup();
		}
	}
	
	public static void clearMenu() {
		while (layerMenu.getItemCount() > 0) {
			layerMenu.remove(0);
		}
	}
	
	public static void createMenu() {
		MenuItem m = new MenuItem("Delete Layer");
		m.addActionListener(l);
		layerMenu.add(m);
		
		MenuItem s = new MenuItem("Show/Hide Other Layers");
		s.addActionListener(l);
		layerMenu.add(s);
		
		MenuItem mu = new MenuItem("Move Up");
		mu.addActionListener(l);
		layerMenu.add(mu);
		
		MenuItem md = new MenuItem("Move Down");
		md.addActionListener(l);
		layerMenu.add(md);
		
		MenuItem combine = new MenuItem("Combine Layers");
		combine.addActionListener(l);
		layerMenu.add(combine);
		
		
		
		addAll();
	}
	
	public static void menuReset() {
		clearMenu();
		createMenu();
		Main.layerMenuSetup();
	}
	
	
	public static void menuActivations(String actionCommand) {
		
		if (actionCommand.equals("Move Up")) {
			layerMoveUp();
		}
		
		if (actionCommand.equals("Move Down")) {
			layerMoveDown();
		}
		
		if (actionCommand.equals("Combine Layers")) {
			combineLayers();
		}
	}
	
	public static void layerMoveUp() {
		if (Paint.currentLayer < Paint.layers.size() - 1) {
			Paint.layers.add(Paint.currentLayer + 1, Paint.layers.remove(Paint.currentLayer));
			Paint.lVisible.add(Paint.currentLayer + 1, Paint.lVisible.remove(Paint.currentLayer));
			Paint.currentLayer++;
			Main.layerMenuSetup();
		}
	}
	
	public static void layerMoveDown() {
		if (Paint.currentLayer > 0) {
			Paint.layers.add(Paint.currentLayer - 1, Paint.layers.remove(Paint.currentLayer));
			Paint.lVisible.add(Paint.currentLayer - 1, Paint.lVisible.remove(Paint.currentLayer));
			Paint.currentLayer--;
			Main.layerMenuSetup();
		}
	}
	
	public static void combineLayers() {
		while (Paint.layers.size() > 1) {
			combineDown(Paint.layers.size() - 1);
		}
		menuReset();
		Paint.currentLayer = 0;
		Paint.img = Paint.layers.get(Paint.currentLayer);
	}
	
	public static void combineDown(int u) {
		BufferedImage base = Paint.layers.get(u - 1);
		BufferedImage top = Paint.layers.get(u);
		for (int i = 0; i < Paint.layers.get(u - 1).getWidth(); i++) {
			for (int j = 0; j < Paint.layers.get(u - 1).getHeight(); j++) {
				Paint.layers.get(u - 1).setRGB(i, j, stackColor(base.getRGB(i, j), top.getRGB(i, j)));
			}
		}
		
		Paint.layers.remove(u);
		Paint.lVisible.remove(u);
	}
	
	public static int stackColor(int rgb1, int rgb2) {
		
		Color c1 = new Color(rgb1, true);
		Color c2 = new Color(rgb2, true);
		int alpha = c1.getAlpha() + c2.getAlpha();
		int a1 = c1.getAlpha();
		int a2 = c2.getAlpha();
		double ratio = 2* ((double)a2 / 255);
		if (a1 != 0) {
			
			ratio = ratio / (a1/255);
		}else{
			ratio = 2;
		}
		int red = (int) (c1.getRed()*(2-ratio) + c2.getRed()*ratio);
		red/=2;
		if (red > 255) {
			red = 255;
		}
		
		int green = (int) (c1.getGreen()*(2-ratio) + c2.getGreen()*ratio);
		green/=2;
		if (green > 255) {
			green = 255;
		}
		
		int blue = (int) (c1.getBlue()*(2-ratio) + c2.getBlue()*ratio);
		blue/=2;
		if (blue > 255) {
			blue = 255;
		}
		
		if (alpha > 255) {
			alpha = 255;
		}
		Color resc = new Color(red, green, blue, alpha);
		return resc.getRGB();
	}
}
