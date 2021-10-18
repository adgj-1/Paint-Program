package actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import dialog.ApproximationMenu;
import dialog.ColorMenu;
import dialog.ImportMenu;
import dialog.LoadMenu;
import dialog.NewMenu;
import dialog.SaveMenu;
import main.Canvas;
import main.LayerManager;
import main.Main;
import main.Paint;
import main.ToolManager;

public class MenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		
		case "Exit": {
			System.exit(0);
		}
		
		case "Load": {
			LoadMenu m = new LoadMenu(Main.j);
			m.setVisible(true);
			break;
		}
		
		case "Import": {
			ImportMenu m = new ImportMenu(Main.j);
			m.setVisible(true);
			break;
		}
		
		case "New": {
			NewMenu m = new NewMenu(Main.j);
			m.setVisible(true);
			break;
		}
		
		case "Save": {
			SaveMenu m = new SaveMenu(Main.j);
			m.setVisible(true);
			break;
		}
		
		case "Select Color": {
			ColorMenu m = new ColorMenu(Main.j);
			m.setVisible(true);
			break;
		}
		
		case "Clear": {
			Paint.clear();
			break;
		}
		
		case "Undo": {
			Paint.undo();
			break;
		}
		
		case "New Layer": {
			Paint.createLayer();
			break;
		}
		
		case "Duplicate Layer": {
			BufferedImage newLayer = new BufferedImage(Paint.img.getWidth(), Paint.img.getHeight(), BufferedImage.TYPE_INT_ARGB);
			for (int i = 0; i < newLayer.getWidth(); i++) {
				for (int j = 0; j < newLayer.getHeight(); j++) {
					newLayer.setRGB(i, j, Paint.img.getRGB(i, j));
				}
			}
			Paint.createLayer(newLayer);
			break;
		}
		
		case "Screen Capture": {
			Paint.screenCapture();
			break;
		}
		
		default: {
			if (e.getActionCommand().startsWith("#Layer")) {
				Paint.currentLayer = Integer.parseInt(e.getActionCommand().substring(6));
				Paint.img = Paint.layers.get(Paint.currentLayer);
				Main.layerMenuSetup();
			} else if (e.getActionCommand().equals("Delete Layer")) {
				LayerManager.deleteLayer();
			} else if (e.getActionCommand().equals("Show/Hide Other Layers")) {
				Canvas.showAllLayers=!Canvas.showAllLayers;
			} else if (e.getActionCommand().equals("Approximation Properties")) {
				ApproximationMenu m = new ApproximationMenu(Main.j);
				m.setVisible(true);
			} else if (e.getActionCommand().startsWith("lVisible")) {
				Paint.lVisible.set(Integer.parseInt(e.getActionCommand().substring(8)),!Paint.lVisible.get(Integer.parseInt(e.getActionCommand().substring(8))));
				Main.layerMenuSetup();
			}
			break;
		}
		}


		
		if (Paint.img != null) {
			for (int i = 0; i < ToolManager.getToolList().size(); i++) {
				if (e.getActionCommand().equals(ToolManager.getToolList().get(i).getName())) {
					ToolManager.setActiveTool(ToolManager.getToolList().get(i));
					ToolManager.getToolList().get(i).init();
					break;
				}
			}
		}
		
		LayerManager.menuActivations(e.getActionCommand());
	}
	
	

}
