package actionListeners;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import main.Paint;
import main.ToolManager;
import main.Typeable;

public class KeyController implements KeyEventDispatcher {


	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			
			if (e.isControlDown()) {
				
				if (e.getKeyCode() == 90) {
					Paint.undo();
				}
			} else {
				if (ToolManager.getActiveTool() instanceof Typeable) {//(ToolManager.getActiveTool().getClass().isAssignableFrom(Typeable.class)) {
					((Typeable) ToolManager.getActiveTool()).down(e);
				}
			}
		} else if (e.getID() == KeyEvent.KEY_RELEASED) {
			if (ToolManager.getActiveTool() instanceof Typeable) {
				((Typeable) ToolManager.getActiveTool()).up(e);
			}
		} else if (e.getID() == KeyEvent.KEY_TYPED) {
			
		}
		return false;
	}

}
