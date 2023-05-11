package dialog;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import main.Main;
import main.Paint;
import util.BrushSelector;
import util.brush.Brush;

public class BrushSelectorMenu extends Dialog{
	
	/**
	 * 
	 */
	Panel panel = new Panel();
	BrushSelector panel2 = new BrushSelector();
	private static final long serialVersionUID = 1L;
	public BrushSelectorMenu(Frame parent) {
		super(parent, true);
		
		setTitle("Select Brush");
		setLocation(350,150);
		setBackground(Color.gray);
		panel.add(new Button("Save"));
		panel.add(new Button("Delete In File"));
		panel.add(new Button("Add"));
		panel.add(new Button("Remove"));
		panel.add(new Button("Create"));
        panel.add(new Button("Ok"));
        panel2.addMouseListener(panel2);
        
        setSize(400,300);
        add(panel2, "Center");
        add(panel, "South");
        
        Paint.viewables.add(panel2);
        
        panel2.setVisible(true);
        panel.setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
            	Paint.viewables.remove(panel2);
               dispose();
            }
         });
	}
	
	public boolean action(Event evt, Object arg){
        if (arg.equals("Ok")){
        	Paint.viewables.remove(panel2);
           dispose();
           return true;
        } else if (arg.equals("Create")) {
        	BrushCreatorMenu m = new BrushCreatorMenu(Main.j);
        	m.setVisible(true);
        } else if (arg.equals("Remove")) {
        	if (BrushSelector.getCurrentBrush() != BrushSelector.getBrushList().get(0)) {
        		removeBrush(BrushSelector.getBrushList().indexOf(BrushSelector.getCurrentBrush()) - 1);
        		BrushSelector.getBrushList().remove(BrushSelector.getCurrentBrush());
        		BrushSelector.toDefaultBrush();
        	}
        } else if (arg.equals("Save")) {
        	if (!BrushSelector.getCurrentBrush().isInFile() && BrushSelector.getCurrentBrush().getShape() != null) {
        		SaveBrushMenu m = new SaveBrushMenu(Main.j);
        		m.setVisible(true);
        	}
        } else if (arg.equals("Delete In File")) {
        	if (BrushSelector.getCurrentBrush() != BrushSelector.getBrushList().get(0)) {
        		deleteBrush(BrushSelector.getBrushList().indexOf(BrushSelector.getCurrentBrush()) - 1);
        		removeBrush(BrushSelector.getBrushList().indexOf(BrushSelector.getCurrentBrush()) - 1);
        		BrushSelector.getBrushList().remove(BrushSelector.getCurrentBrush());
        		BrushSelector.toDefaultBrush();
        	}
        } else if (arg.equals("Add")) {
        	LoadBrushMenu m = new LoadBrushMenu(Main.j);
        	m.setVisible(true);
        }
        return false;
     }

	public static void deleteBrush(int index) {
		String directory = "";
        try {
			 directory = new File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        File path = new File(directory + "\\brush_path.txt");
        if (!path.exists()) {
        	try {
				path.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Scanner scn = null;
		try {
			scn = new Scanner(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
        while (scn != null && scn.hasNextLine()) {
        	String str = scn.nextLine();
        	if (i == index) {
        		File imgToDelete = new File(str);
        		imgToDelete.delete();
        		break;
        	}
        	i++;
        }
	}
	
	public static void removeBrush(int index) {
		String directory = "";
        try {
			 directory = new File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        File path = new File(directory + "\\brush_path.txt");
        if (!path.exists()) {
        	try {
				path.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Scanner scn = null;
		try {
			scn = new Scanner(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		ArrayList<String> brushes = new ArrayList<String>();
        while (scn != null && scn.hasNextLine()) {
        	String str = scn.nextLine();
        	if (i == index) {
        		i++;
        		continue;
        	}
        	brushes.add(str);
        	i++;
        }
        
        //Write
        PrintWriter writer = null;
        try {
			writer = new PrintWriter(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        for (String str : brushes) {
        	writer.println(str);
        }
        writer.close();
        
	}
	
	public static void loadExternalBrush() {
		String directory = "";
        try {
			 directory = new File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        File path = new File(directory + "\\brush_path.txt");
        if (!path.exists()) {
        	try {
				path.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        Scanner scn = null;
		try {
			scn = new Scanner(path);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        while (scn != null && scn.hasNextLine()) {
        	String str = scn.nextLine();
        	if (str.equals("")) {
        		scn.close();
        		return;
        	}
        	Brush b = new Brush(Main.getPathImage(str));
        	BrushSelector.getBrushList().add(b);
        	b.existInFile();
        }
        scn.close();
	}
	
}
