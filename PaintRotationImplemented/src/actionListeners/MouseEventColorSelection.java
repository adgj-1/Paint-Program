package actionListeners;

import java.awt.event.MouseListener;

import dialog.ColorMenu;
import main.Main;

public class MouseEventColorSelection implements MouseListener {
	public MouseEventColorSelection() {
		super();
	}
	@Override
	public void mouseClicked(java.awt.event.MouseEvent m) {
		
		//System.out.println("Click");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent m) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent m) {
		// TODO Auto-generated method stub
		ColorMenu menu = new ColorMenu(Main.j);
		menu.setVisible(true);
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent m) {

		
	}

}
