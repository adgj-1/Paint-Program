package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class MiniCanvas
  extends JPanel
{
  private static final long serialVersionUID = 1L;
  
  public MiniCanvas() {}
  
  public void paint(Graphics g) { if (Paint.img != null ) {update(g);} }
  

  public void update(Graphics g)
  {
	
	
	double mapScale;
	if (Paint.img.getWidth() > Paint.img.getHeight()) {
		mapScale = this.getWidth() / (double)Paint.img.getWidth();
	} else {
		mapScale = this.getHeight() / (double)Paint.img.getHeight();
	}
	int margin = (int)((this.getWidth() - (Paint.img.getWidth() * mapScale)) / 2);
    g.setColor(Canvas.alpha);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    
    // Do Rotation
 	double locationX = 0;
 	double locationY = 0;
 	if (Paint.img != null) {
 		locationX = getWidth() / 2;
 		locationY = getHeight() / 2;
 	}
 	Graphics2D g2d = (Graphics2D) g;
 	g2d.rotate(Main.rotator.angle,locationX,locationY);
    
    if (!Canvas.showAllLayers) {
      if (Paint.img != null) {
        g2d.drawImage(Paint.img, margin, 0, (int)(Paint.img.getWidth() * mapScale), (int)(Paint.img.getHeight() * mapScale), null);
      }
    } else {
      for (int i = 0; i < Paint.layers.size(); i++) {
        if ((Paint.layers.get(i) != null) && (((Boolean)Paint.lVisible.get(i)).booleanValue())) {
          g2d.drawImage(Paint.layers.get(i), margin, 0, (int)((Paint.layers.get(i)).getWidth() * mapScale), (int)((Paint.layers.get(i)).getHeight() * mapScale), null);
        }
      }
    }
    g2d.rotate(-Main.rotator.angle,locationX,locationY);
	int rX = (int)((0 - (double)Canvas.imagePosX) * ((double)mapScale / (double)Canvas.imageScale)) + margin;
	int rY = (int)((0 - (double)Canvas.imagePosY) * ((double)mapScale / (double)Canvas.imageScale));
	
	int rW = (int)((double)Main.c.getWidth() / ((double)Paint.img.getWidth() * Canvas.imageScale) * ((double)Paint.img.getWidth() * (double)mapScale));
	int rH = (int)((double)Main.c.getHeight() / ((double)Paint.img.getHeight() * Canvas.imageScale) * ((double)Paint.img.getHeight() * (double)mapScale));
	g.setColor(Color.MAGENTA);
	g.drawRect(rX,rY,rW,rH);
  }
  
  public void drag(int x, int y) {
	  double mapScale;
		if (Paint.img.getWidth() > Paint.img.getHeight()) {
			mapScale = this.getWidth() / (double)Paint.img.getWidth();
		} else {
			mapScale = this.getHeight() / (double)Paint.img.getHeight();
		}
		int margin = (int)((this.getWidth() - (Paint.img.getWidth() * mapScale)) / 2);
		
		int rW = (int)((double)Main.c.getWidth() / ((double)Paint.img.getWidth() * Canvas.imageScale) * ((double)Paint.img.getWidth() * (double)mapScale));
		int rH = (int)((double)Main.c.getHeight() / ((double)Paint.img.getHeight() * Canvas.imageScale) * ((double)Paint.img.getHeight() * (double)mapScale));
		
	   double posX = -(x - rW/2 - margin)/((double)mapScale / (double)Canvas.imageScale);
	   double posY = -(y - rH/2)/((double)mapScale / (double)Canvas.imageScale);
	   Canvas.imagePosX = (int) (posX);
	   Canvas.imagePosY = (int) (posY);
  }
  
}
