package actionListeners;

import java.awt.event.MouseListener;

import main.Canvas;
import main.Clickable;
import main.ToolManager;

public class MouseEventController implements MouseListener {

	public static boolean drag = false;
	public static int dragLandPointX;
	public static int dragLandPointY;
	public static int dragImagePositionX;
	public static int dragImagePositionY;
	public MouseEventController() {
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
		if (!m.isAltDown()) {
			if (ToolManager.getActiveTool() != null) {
				ToolManager.getActiveTool().clickEvent();
			}
		} else {
			dragDown(m.getX(),m.getY());
		}
		if (ToolManager.getActiveTool() instanceof Clickable) {
			((Clickable) ToolManager.getActiveTool()).click(m);
		}
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent m) {
		// TODO Auto-generated method stub
		if (!m.isAltDown()) {
			if (ToolManager.getActiveTool() != null) {
				ToolManager.getActiveTool().upEvent();
			}
		} else {
			dragUp();
		}
		
	}

	public static void dragDown(int posX, int posY) {
		drag = true;
		dragLandPointX = posX;
		dragLandPointY = posY;
		dragImagePositionX = Canvas.imagePosX;
		dragImagePositionY = Canvas.imagePosY;
	}
	
	public static void dragUp() {
		drag = false;
	}
}
