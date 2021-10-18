package actionListeners;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import main.Canvas;
import main.Paint;
import main.ToolManager;

public class MouseWheelController implements MouseWheelListener {

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		if (e.getWheelRotation() == -1 && Canvas.imageScale < 5) {
			if (e.isAltDown()) {
				imageScaleUp();
			} else {
				if (ToolManager.getActiveTool() != null) {
					ToolManager.getActiveTool().scrollUp();
				}
			}
		} else if (e.getWheelRotation() == 1 && Canvas.imageScale > 0.2) {
			if (e.isAltDown()) {
				imageScaleDown();
			} else {
				if (ToolManager.getActiveTool() != null) {
					ToolManager.getActiveTool().scrollDown();
				}
			}
		}
	}
	
	public void imageScaleUp() {
		Canvas.imageScale += 0.1;
		Canvas.imagePosX-= Paint.img.getWidth()/20;
		Canvas.imagePosY-= Paint.img.getHeight()/20;
	}
	
	public void imageScaleDown() {
		Canvas.imageScale -= 0.1;
		Canvas.imagePosX+= Paint.img.getWidth()/20;
		Canvas.imagePosY+= Paint.img.getHeight()/20;
	}

}
