package dialog;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import main.Main;
import util.BrushSelector;
import util.brush.Brush;

public class LoadBrushMenu extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String path;
	private TextField tf = new TextField(36);
	public LoadBrushMenu(Frame parent) {
		super(parent, true);
		
		setTitle("Load File");
		setLocation(350,150);
		setBackground(Color.gray);
        setLayout(new BorderLayout());
        Panel panel = new Panel();
        Panel textPanel = new Panel();
        panel.add(new Button("Load"));
        
        textPanel.add(tf);
        textPanel.setBounds(0, 0, 100, 100);
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
        if(arg.equals("Load")){
        	path = tf.getText();
        	Brush b = new Brush(Main.getPathImage(path));
        	BrushSelector.getBrushList().add(b);
        	SaveBrushMenu.relocateBrush(b);//Place in right spot
        	SaveBrushMenu.saveBrush(path);//Save path into text file
        	b.existInFile();//Set exist mode to true
           dispose();
           return true;
        }
        return false;
     }

	
}
