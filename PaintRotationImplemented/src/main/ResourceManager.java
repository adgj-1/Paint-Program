package main;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ResourceManager {
	
	public static String path = Main.class.getResource("").toString();
	
	public static Icon getIconFromName(String name) {
		URL url = null;
		try {
			url = new URL(path + "resources/images/" + name + ".png");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Paint.throwError(e.getMessage());
			return null;
		}
		img = img.getScaledInstance(25, 25, 0);
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(img);
		} catch (NullPointerException e) {
			Paint.throwError(e.getMessage());
		}
		return icon;
	}
}
