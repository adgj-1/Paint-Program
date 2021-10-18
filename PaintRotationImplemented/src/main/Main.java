package main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import actionListeners.KeyController;
import actionListeners.MenuListener;
import actionListeners.MinimapMouseListener;
import actionListeners.MouseEventColorSelection;
import actionListeners.MouseEventController;
import actionListeners.MouseWheelController;


public class Main {
	public static JFrame j;
	public static Canvas c;
	public static JPanel sidePanel;
	public static JPanel layerPanel;
	public static SelectedColor colorPanel;
	public static JPanel rightPanel;
	public static MiniCanvas miniCanvas;
	public static MenuListener l = new MenuListener();
	public static Color colorSelected;
	public static JScrollPane scrollPane;
	public static Rotator rotator;
	public static void main(String[] args) {
		
		//Initiate Window
		j = new JFrame("Paint");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon icon = new ImageIcon(Main.class.getResource("icon.png"));
		j.setIconImage(icon.getImage());
		j.setLocation(300, 100);
		
		//Create Panels
		c = new Canvas();
		sidePanel = new JPanel();
		layerPanel = new JPanel();
		colorPanel = new SelectedColor();
		rightPanel = new JPanel();
		miniCanvas = new MiniCanvas();
		scrollPane = new JScrollPane();
		rotator = new Rotator();
		//Layout
		BorderLayout layout = new BorderLayout();
		j.setLayout(layout);
		
		//right Panel
		GridBagLayout rightLayout = new GridBagLayout();
		GridBagConstraints cgbc = new GridBagConstraints();
		cgbc.fill = GridBagConstraints.BOTH;
		rightPanel.setLayout(rightLayout);
		
		cgbc.insets = new Insets(0,5,15,5);
		
		cgbc.gridx = 0;
		cgbc.gridy = 0;
		cgbc.weighty = 0;
		cgbc.ipady = 100;
		rightPanel.add(rotator, cgbc);
		
		cgbc.gridx = 0;
		cgbc.gridy = 1;
		cgbc.weighty = 0;
		cgbc.ipady = 100;
		rightPanel.add(miniCanvas, cgbc);
		cgbc.ipadx = 0;
		
		cgbc.insets = new Insets(5,20,0,20);
		cgbc.gridx = 0;
		cgbc.gridy = 2;
		cgbc.weighty = 0;
		cgbc.ipady = 60;
		rightPanel.add(colorPanel, cgbc);
		cgbc.gridx = 0;
		cgbc.gridy = 3;
		cgbc.weighty = 0;
		cgbc.ipady = 1;
		rightPanel.add(scrollPane, cgbc);
		
		//End Of Layout
		
		//Add Other Panels
		j.add(c,"Center");
		j.add(rightPanel, "East");
		j.add(sidePanel,"West");
		
		//Finish Up
		Paint.init();
		menuSetup();
		toolButtonSetup();
		layerMenuSetup();
		
		j.setSize(600,500);
		j.setVisible(true);
		
		
		KeyboardFocusManager k = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		k.addKeyEventDispatcher(new KeyController());
		
		
		c.addMouseMotionListener(c);
		colorPanel.addMouseListener(new MouseEventColorSelection());
		
		MouseEventController m = new MouseEventController();
		c.addMouseListener(m);
		MouseWheelController w = new MouseWheelController();
		c.addMouseWheelListener(w);
		
		MinimapMouseListener canvasMouse = new MinimapMouseListener();
		miniCanvas.addMouseListener(canvasMouse);
		miniCanvas.addMouseMotionListener(canvasMouse);
		Paint.start();
		
	}
	
	public static void menuSetup() {
		
		MenuBar mb = new MenuBar();
		
		//File Menu
		Menu menu = new Menu("File");
		MenuItem exitButton = new MenuItem("Exit");
		exitButton.addActionListener(l);
		
		MenuItem loadButton = new MenuItem("Load");
		loadButton.addActionListener(l);
		
		MenuItem importButton = new MenuItem("Import");
		importButton.addActionListener(l);
		
		MenuItem newButton = new MenuItem("New");
		newButton.addActionListener(l);
		
		MenuItem saveButton = new MenuItem("Save");
		saveButton.addActionListener(l);
		
		MenuItem scButton = new MenuItem("Screen Capture");
		scButton.addActionListener(l);
		
		menu.add(newButton);
		menu.add(loadButton);
		menu.add(importButton);
		menu.add(scButton);
		menu.add(saveButton);
		menu.add(exitButton);
		
		//Color Menu
		Menu cMenu = new Menu("Color");
		MenuItem colorButton = new MenuItem("Select Color");
		colorButton.addActionListener(l);
		
		cMenu.add(colorButton);
		
		//Edit Menu
		Menu eMenu = new Menu("Edit");
		
		MenuItem undoButton = new MenuItem("Undo");
		undoButton.addActionListener(l);
		eMenu.add(undoButton);
		
		MenuItem clearButton = new MenuItem("Clear");
		clearButton.addActionListener(l);
		eMenu.add(clearButton);
		
		MenuItem layerButton = new MenuItem("New Layer");
		layerButton.addActionListener(l);
		eMenu.add(layerButton);
		
		
		//Approximation Menu
		Menu aMenu = new Menu("Approximation");
		MenuItem aButton = new MenuItem("Approximation Properties");
		aButton.addActionListener(l);
		
		aMenu.add(aButton);
				
		mb.add(menu);
		mb.add(eMenu);
		mb.add(ToolManager.toolMenu());
		mb.add(LayerManager.getlayerMenu());
		mb.add(cMenu);
		mb.add(aMenu);
		
		j.setMenuBar(mb);
	}
	
	public static void layerMenuSetup() {
		layerPanel.setAutoscrolls(true);
		scrollPane.setVisible(true);
		scrollPane.setPreferredSize(new Dimension(150,200));
		scrollPane.setViewportView(layerPanel);
		scrollPane.setEnabled(true);
		scrollPane.setBorder(null);
		layerPanel.removeAll();
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout layout = new GridBagLayout();
		layerPanel.setLayout(layout);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		
			Label tag = new Label("Current Layer: " + Paint.currentLayer);
		
			gbc.gridx = 0;
			gbc.gridy = 0;
			layerPanel.add(tag, gbc);
		
			Label tag2 = new Label("   ");
			
			gbc.gridx = 1;
			gbc.gridy = 0;
			layerPanel.add(tag2, gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			
			
		for (int i = 0; i < Paint.layers.size(); i++) {
			Button b = new Button("Layer" + i);
			b.setActionCommand("#Layer" + i);
			b.addActionListener(l);
			b.setVisible(true);
			gbc.gridx = 0;
			gbc.gridy = i+1;
			layerPanel.add(b,gbc);
			Button b2;
			if (Paint.lVisible.get(i)) {
				b2 = new Button("x");
			} else {
				b2 = new Button("o");
			}
			b2.setActionCommand("lVisible" + i);
			b2.addActionListener(l);
			b2.setVisible(true);
			gbc.gridx = 1;
			gbc.gridy = i+1;
			layerPanel.add(b2,gbc);
		}
		
		
		gbc.gridx = 0;
		gbc.gridy = Paint.layers.size() + 1;
		Button clB = new Button("Combine Layers");
		clB.setActionCommand("Combine Layers");
		clB.addActionListener(l);
		layerPanel.add(clB,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = Paint.layers.size() + 2;
		Button nB = new Button("New Layer");
		nB.setActionCommand("New Layer");
		nB.addActionListener(l);
		layerPanel.add(nB,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = Paint.layers.size() + 3;
		Button dB = new Button("Delete Layer");
		dB.setActionCommand("Delete Layer");
		dB.addActionListener(l);
		layerPanel.add(dB,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = Paint.layers.size() + 5;
		Button uB = new Button("Move Up");
		uB.setActionCommand("Move Up");
		uB.addActionListener(l);
		layerPanel.add(uB,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = Paint.layers.size() + 4;
		Button dnB = new Button("Move Down");
		dnB.setActionCommand("Move Down");
		dnB.addActionListener(l);
		layerPanel.add(dnB,gbc);
		
		gbc.gridx = 0;
		gbc.gridy = Paint.layers.size() + 6;
		Button dupB = new Button("Duplicate");
		dupB.setActionCommand("Duplicate Layer");
		dupB.addActionListener(l);
		layerPanel.add(dupB,gbc);
		
		j.setVisible(true);
	}
	
	public static void toolButtonSetup() {
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagLayout layout = new GridBagLayout();
		sidePanel.setLayout(layout);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		for (int i = 0; i < ToolManager.getToolList().size(); i++) {
			JButton b = new JButton(ResourceManager.getIconFromName(ToolManager.getToolList().get(i).getName()));
			b.setActionCommand(ToolManager.getToolList().get(i).getName());
			b.addActionListener(l);
			gbc.gridx = i%2;
			gbc.gridy = i/2;
			gbc.insets = new Insets(5,5,5,5);
			gbc.ipadx = -25;
			sidePanel.add(b,gbc);
		}
		gbc.ipadx = 0;
		gbc.gridwidth = 2;
		//Size/Hardness
		TextField size = new TextField(3);
		TextField hardness = new TextField(3);
		
		size.setText("" + Paint.defaultSize);
		hardness.setText("" + Paint.defaultTransparency);
		
		Label s = new Label("Size");
		Label h = new Label("Hardness");
		
		gbc.gridx = 0;
		gbc.gridy = ToolManager.getToolList().size();
		sidePanel.add(s, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = ToolManager.getToolList().size() + 1;
		sidePanel.add(size, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = ToolManager.getToolList().size() + 2;
		sidePanel.add(h, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = ToolManager.getToolList().size() + 3;
		sidePanel.add(hardness, gbc);
		
		size.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Paint.defaultSize = Integer.parseInt(size.getText());
				} catch (Exception e_) {
					Paint.throwError("Please Type In A Number");
				}
			}
			
		});
		
		hardness.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					Paint.defaultTransparency = Integer.parseInt(hardness.getText());
				} catch (Exception e_) {
					Paint.throwError("Please Type In A Number");
				}
			}
			
		});

		
		
		//
		
		
		
	}
	
	public static void loadImage(String path) {
		Toolkit t = j.getToolkit();
		MediaTracker tracker = new MediaTracker(j);
		java.awt.Image i = t.getImage(path);
		tracker.addImage(i, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Paint.throwError("Loading interrupted");
			e.printStackTrace();
		}
		if (i.getHeight(null) == -1 && i.getWidth(null) == -1) {
			return;
		}
		Paint.img = toBufferedImage(i);
		Paint.layers = new ArrayList<BufferedImage>();
		Paint.createLayer(Paint.img);
		LayerManager.menuReset();
		j.setSize(i.getWidth(null) + 300, i.getHeight(null) + 200);
		j.setTitle("Paint - " + path);
		
		Paint.uiLayer = new BufferedImage(Paint.img.getWidth(), Paint.img.getHeight(), BufferedImage.TYPE_INT_ARGB);
	}
	
	public static void importImage(String path) {
		if (Paint.img == null) {
			Paint.throwError("There are no project open");
			return;
		}
		Toolkit t = j.getToolkit();
		MediaTracker tracker = new MediaTracker(j);
		java.awt.Image i = t.getImage(path);
		tracker.addImage(i, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			Paint.throwError("Loading interrupted");
			e.printStackTrace();
		}
		if (i.getHeight(null) == -1 && i.getWidth(null) == -1) {
			return;
		}
		Paint.img = fitSize(toBufferedImage(i), Paint.img.getWidth(),Paint.img.getHeight());
		//Paint.img = toBufferedImage(i);
		Paint.createLayer(Paint.img);
	}
	
	public static void createImage(int sizeX, int sizeY) {
		Paint.layers = new ArrayList<BufferedImage>();
		Paint.lVisible = new ArrayList<Boolean>();
		Paint.img = new BufferedImage(sizeX,sizeY,BufferedImage.TYPE_INT_ARGB);
		j.setSize(Paint.img.getWidth(null) + 300, Paint.img.getHeight(null) + 200);
		j.setTitle("Paint - unknown");
		for (int i = 0; i < Paint.img.getHeight(); i++) {
			for (int j = 0; j < Paint.img.getWidth(); j++) {
				Paint.img.setRGB(j, i, Color.WHITE.getRGB());
			}
		}
		
		LayerManager.menuReset();
		Paint.createLayer(Paint.img);
		
		Paint.uiLayer = new BufferedImage(Paint.img.getWidth(), Paint.img.getHeight(), BufferedImage.TYPE_INT_ARGB);
	}
	
	public static void saveImage(String path) {
		if (Paint.img != null) {
			try {
				ImageIO.write(Paint.img, "png", new File(path));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Paint.throwError("No image to save");
		}
	}
	
	public static BufferedImage copyImage(BufferedImage source) {
		BufferedImage res = new BufferedImage(source.getWidth(),source.getHeight(),source.getType());
		Graphics g = res.getGraphics();
		g.drawImage(source, 0, 0, null);
		g.dispose();
		return res;
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
	
	public static BufferedImage fitSize(Image source, int w, int h) {
		BufferedImage bimage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(source, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
