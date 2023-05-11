package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import actionListeners.RotatorMouseListener;

public class Rotator extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public double angle = 0;
	double posX = this.getWidth()/2;
	double posY = this.getHeight()/2;
	double rotX;
	double rotY;
	double srotX;
	double srotY;
	double rawImagePosX;
	double rawImagePosY;
	int currentImageX;
	int currentImageY;
	Font font = new Font("Calibri",Font.BOLD,20);
	public Rotator() {
		this.addMouseListener(new RotatorMouseListener());
		this.addMouseMotionListener(new RotatorMouseListener());
		this.setBounds(this.getX(), this.getY(), 100, 100);
		currentImageX = Canvas.imagePosX;
		currentImageY = Canvas.imagePosY;
	}
	
	public void paint(Graphics g) {
		update(g);
	}
	
	public void update(Graphics g) {
		if (Paint.enableRotation) {
			g.setColor(Color.GRAY);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			posX = this.getWidth()/2;
			posY = this.getHeight()/2;
			
			srotX = Math.cos(angle - Math.PI/2) * 25 + posX;
			srotY = Math.sin(angle - Math.PI/2) * 25 + posY;
			
			rotX = Math.cos(angle - Math.PI/2) * 50 + posX;
			rotY = Math.sin(angle - Math.PI/2) * 50 + posY;
			g.setColor(Color.RED);
			g.drawLine((int)srotX, (int)srotY, (int)rotX, (int)rotY);
			
			srotX = Math.cos(angle + Math.PI/2) * 25 + posX;
			srotY = Math.sin(angle + Math.PI/2) * 25 + posY;
			
			rotX = Math.cos(angle + Math.PI/2) * 50 + posX;
			rotY = Math.sin(angle + Math.PI/2) * 50 + posY;
			
			g.setColor(Color.WHITE);
			g.drawLine((int)srotX, (int)srotY, (int)rotX, (int)rotY);
			
			g.setColor(Color.YELLOW);
			g.drawOval((int)posX - 25, (int)posY - 25, 50, 50);
			g.setFont(font);
			g.drawString("R", (int)posX-5, (int)posY+5);
		} else {
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setFont(font);
			g.setColor(Color.RED);
			g.drawString("Rotation UnAvailable", (int)posX, (int)posY + 50);
		}
		
		
	}
	
	public void update() {
		if  (!Paint.enableRotation) {
			angle = 0;
		}
		if (currentImageX != Canvas.imagePosX || currentImageY != Canvas.imagePosY) {
			rawImagePosX = Canvas.imagePosX;
			rawImagePosY = Canvas.imagePosY;
			currentImageX = Canvas.imagePosX;
			currentImageY = Canvas.imagePosY;
		}
	}
	
	public void input(MouseEvent e) {
		//System.out.println("tt");
		double preAngle = angle;// Record previous angle
		
		double run = e.getX() - posX;
		double rise = e.getY() - posY;
		
		if (Math.sqrt(run * run + rise * rise) < 25) {
			angle = 0;
		} else {
			angle = Math.atan2(rise, run) + Math.PI/2;
		}
		
		localRelocation(preAngle, angle);
	}
	
	public void rotate(double a) {
		double preAngle = angle;
		angle = a;
		localRelocation(preAngle,angle);
	}
	
	private void localRelocation(double preAngle, double angle) {
		double imgCenterX = Canvas.imagePosX + Paint.uiLayer.getWidth() * Canvas.imageScale/2;
		double imgCenterY = Canvas.imagePosY + Paint.uiLayer.getHeight() * Canvas.imageScale/2;
		
		double localCenterX = Main.c.getWidth()/2;
		double localCenterY = Main.c.getHeight()/2;
		
		double x = imgCenterX - localCenterX;
		double y = imgCenterY - localCenterY;
		double[] oldPolar = toPolar(x,y);
		double[] newPos = toCartesian(oldPolar[0] + (angle-preAngle),oldPolar[1]);
		double moveX = (newPos[0] - x);
		double moveY = (newPos[1] - y);
		rawImagePosX += moveX;
		rawImagePosY += moveY;
		
		Canvas.imagePosX = (int) rawImagePosX;
		Canvas.imagePosY = (int) rawImagePosY;
		currentImageX = Canvas.imagePosX;
		currentImageY = Canvas.imagePosY;
	}
	
	public static double[] toCartesian(double angle, double radius) {
		double x = Math.cos(angle) * radius;
		double y = Math.sin(angle) * radius;
		return new double[] {x,y};
	}
	
	public static double[] toPolar(double x, double y) {
		double angle = Math.atan2(y, x);
		double radius = Math.sqrt(x * x + y * y);
		return new double[] {angle, radius};
	}
	
	public int[] rotatedPos(int mouseX, int mouseY) {
		int[] res = new int[2];
		double mX = (int) (mouseX/Canvas.imageScale)-(int)(Canvas.imagePosX/Canvas.imageScale);
		double mY = (int) (mouseY/Canvas.imageScale)-(int)(Canvas.imagePosY/Canvas.imageScale);
		double centerX = 0;
		double centerY = 0;
		if (Paint.img != null) {
			//Center compared to inside of canvas
			centerX = Paint.img.getWidth()/2;
			centerY = Paint.img.getHeight()/2;
		}
		
		double run = mX - centerX;
		double rise = mY - centerY;
		
		double mAngle = Math.atan2(rise, run);
		double hyp = Math.sqrt(run * run + rise * rise);
		
		double resX = centerX + Math.cos(mAngle - angle) * hyp;
		double resY = centerY + Math.sin(mAngle - angle) * hyp;
		res[0] = (int)resX;
		res[1] = (int)resY;
		return res;
	}
}
