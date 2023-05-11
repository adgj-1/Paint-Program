package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import dialog.BrushCreatorMenu;
import main.Paint;
import main.TypeLine;

public class BrushCanvas extends JPanel implements MouseListener, MouseMotionListener, TypeLine {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int lastMouseX;
	private int lastMouseY;
	
	public void paint(Graphics g) {
		update(g);
	}
	

	
	public void update(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 600, 600);
		g.drawImage(BrushCreatorMenu.bImg, 0, 0,600,600, null);
	}
	
	public void draw(int x, int y) {
		x = x/3;
		y = y/3;
		for (int i = 0; i < BrushCreatorMenu.size; i++) {
			for (int j = 0; j < BrushCreatorMenu.size; j++) {
				int pX = x + i - BrushCreatorMenu.size/2;
				int pY = y +  j - BrushCreatorMenu.size/2;
				if (pX <= 200 && pY <= 200 && pX >= 0 && pY >= 0) {
					BrushCreatorMenu.bImg.setRGB(pX, pY, new Color(0,0,0,BrushCreatorMenu.alpha).getRGB());
				}
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		draw(e.getX(),e.getY());
		lastMouseX = e.getX();
		lastMouseY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		Paint.drawLine2(lastMouseX,lastMouseY, e.getX(), e.getY(), this);
		lastMouseX = e.getX();
		lastMouseY = e.getY();
	}



	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void doLocationAction(int x, int y) {
		// TODO Auto-generated method stub
		draw(x,y);
	}

}
