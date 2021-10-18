package tools;

import dialog.SpritesheetMenu;
import main.Main;
import main.Paint;
import main.Tool;

public class SpriteSheetGenerator extends Tool {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Paint.enableRotation = false;
		SpritesheetMenu m = new SpritesheetMenu(Main.j);
		m.setVisible(true);
	}

	@Override
	public void upEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SpritesheetGenerator";
	}

}
