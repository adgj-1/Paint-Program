package util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import util.brush.Brush;

public class BrushSelector extends JPanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static List<Brush> brushList = new ArrayList<Brush>();
	
	private static Brush brushSelected;
	
	public BrushSelector() {
		super();
		repaint();
	}
	
	public void paint(Graphics g) {
		update(g);
	}
	

	public static void toDefaultBrush() {
		brushSelected = brushList.get(0);
	}
	
	public void update(Graphics g) {
		if (brushSelected == null) {
			brushSelected = brushList.get(0); // Set to default brush
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for (int i = 0; i < brushList.size(); i++) {
			brushList.get(i).draw(g, i * 50, 0);
		}
	}
	
	public static Brush getCurrentBrush() {
		return brushSelected;
	}
	
	public static List<Brush> getBrushList() {
		return brushList;
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
		for (Brush brush : brushList) {
			if (brush.click(e.getX(), e.getY())) {
				brushSelected = brush;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
