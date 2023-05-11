package main;

import java.awt.Menu;
import java.awt.MenuItem;
import java.util.ArrayList;
import java.util.List;

import tools.Contrast;
import tools.Crop;
import tools.Dropper;
import tools.Eraser;
import tools.Filter;
import tools.Highlighter;
import tools.Line;
import tools.MixerBrush;
import tools.PaintBrush;
import tools.PaintBucket;
import tools.Pencil;
import tools.Saturation;
import tools.SpriteSheetGenerator;

public class ToolManager {

	private static List<Tool> toolList = new ArrayList<Tool>();
	
	private static Tool activeTool;
	
	public static void loadTools() {
		toolList.add(new Pencil());
		toolList.add(new PaintBrush());
		toolList.add(new PaintBucket());
		toolList.add(new Eraser());
		toolList.add(new Contrast());
		toolList.add(new Saturation());
		toolList.add(new Highlighter());
		toolList.add(new MixerBrush());
		toolList.add(new Dropper());
		toolList.add(new Line());
		toolList.add(new SpriteSheetGenerator());
		toolList.add(new Crop());
		toolList.add(new Filter());
	}
	
	public static Menu toolMenu() {
		Menu res = new Menu("Tools");
		
		for (int i = 0; i < toolList.size(); i++) {
			MenuItem temp = new MenuItem(toolList.get(i).getName());
			temp.addActionListener(Main.l);
			res.add(temp);
		}
		
		return res;
	}
	
	public static Tool getActiveTool() {
		return activeTool;
	}
	
	public static List<Tool> getToolList() {
		return toolList;
	}
	
	public static void setActiveTool(Tool t) {
		activeTool = t;
	}
}
