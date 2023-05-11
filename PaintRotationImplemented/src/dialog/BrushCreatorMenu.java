package dialog;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.Paint;
import util.BrushCanvas;
import util.BrushSelector;
import util.brush.Brush;

public class BrushCreatorMenu extends Dialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int alpha;
	public static int size;
	private TextField abox = new TextField(3);
	
	private BrushCanvas bCanvas = new BrushCanvas();
	
	public static BufferedImage bImg;
	
	public BrushCreatorMenu(Frame parent) {
		super(parent, true);
		setTitle("Brush Creator");
		setLocation(350,100);
		setBackground(Color.gray);
        setLayout(new BorderLayout());
        Panel panel = new Panel();
        Panel textPanel = new Panel();
        Panel sliderPanel = new Panel();
        sliderPanel.setLayout(new FlowLayout());
        panel.add(new Button("Cancel"));
        panel.add(new Button("Done"));
        
        textPanel.add(abox);
        textPanel.setBounds(0, 0, 100, 100);
        add("South", panel);
        add("North", textPanel);
        textPanel.add(sliderPanel);
        setSize(600,720);
        
        abox.setText("255");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
            	
               dispose();
            }
         });
        
        //Slider
        JSlider slider = new JSlider(JSlider.HORIZONTAL,0,255,0);
        slider.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		abox.setText("" + ((JSlider) e.getSource()).getValue());
        		alpha = Integer.parseInt(abox.getText());
        	}
        });
        
        sliderPanel.add(slider);
        
      //Slider
        JSlider sliderS = new JSlider(JSlider.HORIZONTAL,1,10,1);
        sliderS.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent e) {
        		size = ((JSlider) e.getSource()).getValue();
        	}
        });
        
        sliderPanel.add(sliderS);
        size = 1;
        
        add("Center",bCanvas);
        Paint.viewables.add(bCanvas);
        
        //Setup image
        bCanvas.addMouseListener(bCanvas);
        bCanvas.addMouseMotionListener(bCanvas);
        bImg = new BufferedImage(200,200, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < bImg.getWidth(); i++) {
        	for (int j = 0; j < bImg.getHeight(); j++) {
        		bImg.setRGB(i, j, new Color(0,0,0,0).getRGB());
        	}
        }
	}
	
	public boolean action(Event evt, Object arg){
        if(arg.equals("Done")){
        	Paint.viewables.remove(bCanvas);
        	Brush b = new Brush(bImg);
        	BrushSelector.getBrushList().add(b);
           dispose();
           return true;
        } else if (arg.equals("Cancel")) {
        	Paint.viewables.remove(bCanvas);
            dispose();
            return true;
        }
        return false;
     }

	
	
}
