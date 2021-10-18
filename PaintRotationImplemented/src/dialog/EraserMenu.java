package dialog;

import java.awt.*;
import java.awt.event.*;
import tools.Eraser;

public class EraserMenu extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public EraserMenu(Frame parent) {
		super(parent, true);
		
		setTitle("Eraser Properties");
		setLocation(350,150);
		setBackground(Color.gray);
        Panel panel = new Panel();
        panel.add(new Button("White"));
        panel.add(new Button("Alpha"));
        
        setSize(300,100);
        add(panel);
        panel.setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
            	
               dispose();
            }
         });
	}
	
	public boolean action(Event evt, Object arg){
        if(arg.equals("White")){
        	Eraser.isAlpha = false;
           dispose();
           return true;
        } else if (arg.equals("Alpha")){
        	Eraser.isAlpha = true;
           dispose();
           return true;
        }
        return false;
     }

	
}
