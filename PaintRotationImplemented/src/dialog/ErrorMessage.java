package dialog;

import java.awt.*;
import java.awt.event.*;

public class ErrorMessage extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String m;
	
	public ErrorMessage(Frame parent, String message) {
		super(parent, true);
		setTitle("Error");
		m = message;
		repaint();
		setLocation(350,150);
		setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        Panel panel = new Panel();
        panel.add(new Button("Ok"));
        
        add("South", panel);
        setSize(300,100);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
            	
               dispose();
            }
         });
	}
	
	public boolean action(Event evt, Object arg){
        if(arg.equals("Ok")){
           dispose();
           return true;
        }
        return false;
     }

	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.drawString(m, 50, 50);
		setSize(m.length() * 6 + 90, 100);
	}
}
