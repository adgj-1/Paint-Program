package dialog;

import java.awt.*;
import java.awt.event.*;

import main.Main;

public class NewMenu extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int x;
	public static int y;
	private TextField xtf = new TextField(3);
	private TextField ytf = new TextField(3);
	
	public NewMenu(Frame parent) {
		super(parent, true);
		setTitle("Create New Image");
		setLocation(350,150);
		setBackground(Color.gray);
        setLayout(new BorderLayout());
        Panel panel = new Panel();
        Panel textPanel = new Panel();
        panel.add(new Button("Create"));
        
        textPanel.add(xtf);
        textPanel.add(ytf);
        textPanel.setBounds(0, 0, 100, 100);
        add("South", panel);
        add("North", textPanel);
        setSize(200,120);
        xtf.setText("300");
        ytf.setText("300");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
            	
               dispose();
            }
         });
	}
	
	public boolean action(Event evt, Object arg){
        if(arg.equals("Create")){
        	try {
        	x = Integer.parseInt(xtf.getText());
        	y = Integer.parseInt(ytf.getText());
        	Main.createImage(x,y);
        	} catch (Exception e) {
        		
        	}
           dispose();
           return true;
        }
        return false;
     }

	
}
