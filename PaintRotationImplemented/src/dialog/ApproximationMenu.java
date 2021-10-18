package dialog;

import java.awt.*;
import java.awt.event.*;

import main.Approximation;

public class ApproximationMenu extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String path;
	private TextField tf = new TextField(36);
	public ApproximationMenu(Frame parent) {
		super(parent, true);
		
		setTitle("Approximation Properties");
		setLocation(350,150);
		setBackground(Color.gray);
        setLayout(new BorderLayout());
        Panel panel = new Panel();
        Panel textPanel = new Panel();
        panel.add(new Button("Off"));
        panel.add(new Button("On"));
        
        textPanel.add(tf);
        textPanel.setBounds(0, 0, 100, 100);
        add("South", panel);
        add("North", textPanel);
        setSize(400,120);
        
        tf.setText("10");
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
            	
               dispose();
            }
         });
	}
	
	public boolean action(Event evt, Object arg){
        if(arg.equals("Off")){
        	Approximation.isOn = false;
        	Approximation.step = Integer.parseInt(tf.getText());
           dispose();
           return true;
        } else if (arg.equals("On")){
        	Approximation.isOn = true;
        	Approximation.step = Integer.parseInt(tf.getText());
           dispose();
           return true;
        }
        return false;
     }

	
}
