package dialog;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import main.Main;
import util.BrushSelector;
import util.brush.Brush;

public class SaveBrushMenu extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String path;
	private TextField tf = new TextField(36);
	public SaveBrushMenu(Frame parent) {
		super(parent, true);
		
		setTitle("Save Brush");
		setLocation(350,150);
		setBackground(Color.gray);
        setLayout(new BorderLayout());
        Panel panel = new Panel();
        Panel textPanel = new Panel();
        panel.add(new Button("Save"));
        
        textPanel.add(tf);
        textPanel.setBounds(0, 0, 100, 100);
        tf.setBounds(0, 0, 100, 25);
        add("South", panel);
        add("North", textPanel);
        setSize(400,120);
        String directory = "";
        try {
			 directory = new File(".").getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        tf.setText(directory + "\\unknown.png");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
            	
               dispose();
            }
         });
	}
	
	public boolean action(Event evt, Object arg){
        if(arg.equals("Save")){
        	path = tf.getText();
        	Main.saveImage(path,BrushSelector.getCurrentBrush().getShape());
        	relocateBrush(BrushSelector.getCurrentBrush());
        	saveBrush(path);
        	BrushSelector.getCurrentBrush().existInFile();
           dispose();
           return true;
        }
        return false;
     }

	public static void relocateBrush(Brush b) {
		int index = BrushSelector.getBrushList().indexOf(b);
		for (int i = 1; i < BrushSelector.getBrushList().size(); i++) {
			if (!BrushSelector.getBrushList().get(i).isInFile()) {
				swapBrush(index,i);
			}
		}
	}
	
	public static void swapBrush(int a, int b) {
		Brush temp = BrushSelector.getBrushList().get(a);
		BrushSelector.getBrushList().set(a, BrushSelector.getBrushList().get(b));
		BrushSelector.getBrushList().set(b, temp);
	}
	
	public static void saveBrush(String save_path) {
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
		ArrayList<String> brushes = new ArrayList<String>();
        while (scn != null && scn.hasNextLine()) {
        	String str = scn.nextLine();
        	brushes.add(str);
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
        writer.println(save_path);
        writer.close();
        
	}
}
