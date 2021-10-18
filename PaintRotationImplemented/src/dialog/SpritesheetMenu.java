package dialog;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import main.LayerManager;
import main.Main;
import main.Paint;

public class SpritesheetMenu extends Dialog
{
  private static final long serialVersionUID = 1L;
  public static int x;
  public static int y;
  private TextField xtf = new TextField(3);
  private TextField ytf = new TextField(3);
  
  public SpritesheetMenu(Frame parent) {
    super(parent, true);
    setTitle("Generate Spritesheet");
    setLocation(350, 150);
    setBackground(Color.gray);
    setLayout(new java.awt.BorderLayout());
    Panel panel = new Panel();
    Panel textPanel = new Panel();
    panel.add(new java.awt.Button("Generate"));
    panel.add(new java.awt.Button("Cancel"));
    textPanel.add(xtf);
    textPanel.add(ytf);
    textPanel.setBounds(0, 0, 100, 100);
    add("South", panel);
    add("North", textPanel);
    setSize(200, 120);
    xtf.setText("1");
    ytf.setText("1");
    addWindowListener(new java.awt.event.WindowAdapter()
    {
      public void windowClosing(WindowEvent windowEvent) {
        dispose();
      }
    });
  }
  
  public boolean action(Event evt, Object arg) {
    if (arg.equals("Generate")) {
      try {
        x = Integer.parseInt(xtf.getText());
        y = Integer.parseInt(ytf.getText());
        int newWidth = Paint.img.getWidth() * x;
        int newHeight = Paint.img.getHeight() * y;
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, 2);
        Paint.uiLayer = new BufferedImage(newWidth, newHeight, 2);
        for (int i = 0; i < newWidth; i++) {
        	for (int j = 0; j < newHeight; j++) {
        		newImage.setRGB(i, j, new Color(0,0,0,0).getRGB());
        	}
        }
        for (int i = 0; i < Paint.layers.size(); i++) {
        	int posX = Paint.layers.get(i).getWidth() * i;
        	stamp(newImage, Paint.layers.get(i),posX % newWidth, (posX / newWidth) * Paint.img.getHeight());
        }
        Paint.img = newImage;
        Paint.layers.clear();
		Paint.lVisible.clear();
		Paint.currentLayer = 0;
		LayerManager.layerMenu.removeAll();
		
        Paint.layers.add(newImage);
        Paint.lVisible.add(true);
        MenuItem m = new MenuItem("#Layer" + (Paint.layers.size() - 1));
		m.addActionListener(LayerManager.l);
		LayerManager.layerMenu.add(m);
		Main.layerMenuSetup();
        Paint.img = Paint.layers.get(Paint.currentLayer);
      }
      catch (Exception localException) {Paint.throwError(localException.getMessage());}
      
      dispose();
      return true;
    } else if (arg.equals("Cancel")) {
    	dispose();
    	return true;
    }
    return false;
  }
  
  public void stamp(BufferedImage background, BufferedImage img, int posX, int posY) {
	  for (int i = 0; i < img.getWidth(); i++) {
		  for (int j = 0; j < img.getHeight(); j++) {
			  background.setRGB(posX + i, posY + j, img.getRGB(i, j));
		  }
	  }
  }
}
