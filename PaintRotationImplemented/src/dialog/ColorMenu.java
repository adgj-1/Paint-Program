package dialog;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.ColorSelector;
import main.Main;

public class ColorMenu extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int cR;
	public static int cG;
	public static int cB;
	
	private TextField r = new TextField(3);
	private TextField g = new TextField(3);
	private TextField b = new TextField(3);
	
	private ColorSelector colorSelector = new ColorSelector();
	
	public ColorMenu(Frame parent) {
		super(parent, true);
		setTitle("Color Selector");
		setLocation(350,150);
		setBackground(Color.gray);
        setLayout(new BorderLayout());
        Panel panel = new Panel();
        Panel textPanel = new Panel();
        Panel sliderPanel = new Panel();
        sliderPanel.setLayout(new FlowLayout());
        panel.add(new Button("Select"));
        
        textPanel.add(r);
        textPanel.add(g);
        textPanel.add(b);
        textPanel.setBounds(0, 0, 100, 100);
        add("South", panel);
        add("North", textPanel);
        add("West",sliderPanel);
        setSize(700,200);
        
        r.setText(String.valueOf(Main.colorSelected.getRed()));
        g.setText(String.valueOf(Main.colorSelected.getGreen()));
        b.setText(String.valueOf(Main.colorSelected.getBlue()));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
            	
               dispose();
            }
         });
        
        //Slider R
        JSlider sliderR = new JSlider(JSlider.HORIZONTAL,0,255,Main.colorSelected.getRed());
        sliderR.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		r.setText("" + ((JSlider) e.getSource()).getValue());
        		paintCanvas();
        	}
        });
        
        sliderPanel.add(sliderR);
        
      //Slider G
        JSlider sliderG = new JSlider(JSlider.HORIZONTAL,0,255,Main.colorSelected.getGreen());
        sliderG.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		g.setText("" + ((JSlider) e.getSource()).getValue());
        		paintCanvas();
        	}
        });
        
        sliderPanel.add(sliderG);
        
      //Slider B
        JSlider sliderB = new JSlider(JSlider.HORIZONTAL,0,255,Main.colorSelected.getBlue());
        sliderB.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		b.setText("" + ((JSlider) e.getSource()).getValue());
        		paintCanvas();
        	}
        });
        
        sliderPanel.add(sliderB);
        
        textPanel.add(colorSelector);
        paintCanvas();
	}
	
	public boolean action(Event evt, Object arg){
        if(arg.equals("Select")){
        	cR = Integer.parseInt(r.getText());
        	cG = Integer.parseInt(g.getText());
        	cB = Integer.parseInt(b.getText());
        	Main.colorSelected = new Color(cR, cG, cB);
           dispose();
           return true;
        }
        return false;
     }

	public void paintCanvas() {
		cR = Integer.parseInt(r.getText());
    	cG = Integer.parseInt(g.getText());
    	cB = Integer.parseInt(b.getText());
    	colorSelector.repaint();
	}
	
}
