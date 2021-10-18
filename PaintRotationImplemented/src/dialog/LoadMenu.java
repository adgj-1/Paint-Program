package dialog;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import main.Main;

public class LoadMenu extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String path;
	private TextField tf = new TextField(36);
	public LoadMenu(Frame parent) {
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
        	Main.loadImage(path);
           dispose();
           return true;
        }
        return false;
     }

	
}
