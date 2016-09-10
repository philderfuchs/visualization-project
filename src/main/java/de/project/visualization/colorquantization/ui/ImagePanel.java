package de.project.visualization.colorquantization.ui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImagePanel extends JPanel {

	private BufferedImage image;
	private static final int maxWidth = 400;

	public ImagePanel(BufferedImage image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image.getWidth() > maxWidth) {
			g.drawImage(image.getScaledInstance(maxWidth, -1, Image.SCALE_DEFAULT), 0, 0, null);
		} else {
			g.drawImage(image, 0, 0, null);
		}
	}
	
	public int getImageHeight() {
		if(image.getWidth() > maxWidth) {
			return image.getScaledInstance(maxWidth, -1, Image.SCALE_DEFAULT).getHeight(new ImageObserver(){

				public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
					// TODO Auto-generated method stub
					return false;
				}});
		} else {
			return image.getHeight();
		}
	}
	
	
	public int getImageWidth() {
		if(image.getWidth() > maxWidth) {
			return image.getScaledInstance(maxWidth, -1, Image.SCALE_DEFAULT).getWidth(new ImageObserver(){

				public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
					// TODO Auto-generated method stub
					return false;
				}});
		} else {
			return image.getWidth();
		}
	}

}
