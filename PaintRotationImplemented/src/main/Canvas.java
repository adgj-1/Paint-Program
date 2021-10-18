package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

import actionListeners.MouseEventController;

public class Canvas extends JPanel implements MouseMotionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int mouseX;
	public static int mouseY;
	public static int rawMouseX;
	public static int rawMouseY;
	public static Color alpha;
	public static double imageScale = 1;
	public static int imagePosX = 0;
	public static int imagePosY = 0;
	public static boolean showAllLayers = true;
	//public static BufferedImage img = new BufferedImage(300,300,BufferedImage.TYPE_INT_RGB);
	
	public void paint(Graphics g) {
		update(g);
	}
	

	
	public void update(Graphics g) {
		
		g.setColor(alpha);
		g.fillRect(0, 0, Main.j.getWidth(), Main.j.getHeight());
		
		// Do Rotation
		double locationX = 0;
		double locationY = 0;
		if (Paint.img != null) {
			locationX = Paint.img.getWidth() / 2 * imageScale + imagePosX;
			locationY = Paint.img.getHeight() / 2 * imageScale + imagePosY;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(Main.rotator.angle,locationX,locationY);
		if (!showAllLayers) {
			if (Paint.img != null) {
				g2d.drawImage(Paint.img, imagePosX, imagePosY, (int)(Paint.img.getWidth() * imageScale), (int)(Paint.img.getHeight() * imageScale),null);
			}
		} else {
			for (int i = 0; i < Paint.layers.size(); i++) {
				if (Paint.layers.get(i) != null && Paint.lVisible.get(i)) {
					g2d.drawImage(Paint.layers.get(i), imagePosX, imagePosY, (int)(Paint.layers.get(i).getWidth() * imageScale), (int)(Paint.layers.get(i).getHeight() * imageScale),null);
				}
			}
		}
		if (Paint.uiLayer != null) {
			g2d.drawImage(Paint.uiLayer, imagePosX, imagePosY, (int)(Paint.uiLayer.getWidth() * imageScale), (int)(Paint.uiLayer.getHeight() * imageScale),null);
		}
		
		
		
		g2d.rotate(-Main.rotator.angle,locationX,locationY);
		if (ToolManager.getActiveTool() instanceof HasUI) {
			((HasUI) ToolManager.getActiveTool()).drawUI(g);
		}
		Approximation.drawCursor(g);
		if (Paint.img != null) {
			g.setColor(Color.CYAN);
			g2d.drawString("Scale: " + (int)(imageScale * 100) + "%", Main.c.getWidth() - 200, Main.c.getHeight() - 30);
			g2d.drawString("x: " + Paint.img.getWidth() + "  y: " + Paint.img.getHeight(), Main.c.getWidth() - 100, Main.c.getHeight() - 30);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (!Approximation.isOn) {
			int rmouseX = (int) (e.getX()/imageScale)-(int)(imagePosX/imageScale);
			int rmouseY = (int) (e.getY()/imageScale) - (int)(imagePosY/imageScale);
			if (Main.rotator.angle != 0) {
				int[] newPoints = Main.rotator.rotatedPos(e.getX(), e.getY());
				mouseX = newPoints[0];
				mouseY = newPoints[1];
			} else {
				mouseX = rmouseX;
				mouseY = rmouseY;
			}
			if (!MouseEventController.drag) {
				if (ToolManager.getActiveTool() != null) {
					ToolManager.getActiveTool().moveEvent();
				}
			}
		}
		if (MouseEventController.drag) {
			dragImage();
		}
		rawMouseX = e.getX();
		rawMouseY = e.getY();
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!Approximation.isOn) {
			int rmouseX = (int) (e.getX()/imageScale)-(int)(imagePosX/imageScale);
			int rmouseY = (int) (e.getY()/imageScale) - (int)(imagePosY/imageScale);
			if (Main.rotator.angle != 0) {
				int[] newPoints = Main.rotator.rotatedPos(e.getX(), e.getY());
				mouseX = newPoints[0];
				mouseY = newPoints[1];
			} else {
				mouseX = rmouseX;
				mouseY = rmouseY;
			}
			if (ToolManager.getActiveTool() != null) {
				ToolManager.getActiveTool().moveEvent();
			}
		}
		rawMouseX = e.getX();
		rawMouseY = e.getY();
		
		
		
	}
	
	public void dragImage() {
		imagePosX = MouseEventController.dragImagePositionX + rawMouseX - MouseEventController.dragLandPointX;
		imagePosY = MouseEventController.dragImagePositionY + rawMouseY - MouseEventController.dragLandPointY;
	}
	
}
