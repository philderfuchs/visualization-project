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

	private Image image;
	private static final int maxWidth = 400;

	public ImagePanel(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image.getWidth(getImageObserver()) > maxWidth) {
			g.drawImage(image.getScaledInstance(maxWidth, -1, Image.SCALE_SMOOTH), 0, 0, null);
		} else {
			g.drawImage(image, 0, 0, null);
		}
	}

	public int getImageHeight() {
		if (image.getWidth(getImageObserver()) > maxWidth) {
			return image.getScaledInstance(maxWidth, -1, Image.SCALE_SMOOTH).getHeight(new ImageObserver() {

				public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
					// TODO Auto-generated method stub
					return false;
				}
			});
		} else {
			return image.getHeight(getImageObserver());
		}
	}

	public int getImageWidth() {
		if(image.getWidth(getImageObserver()) > maxWidth) {
			return image.getScaledInstance(maxWidth, -1, Image.SCALE_SMOOTH).getWidth(getImageObserver());
		} else {
			return image.getWidth(getImageObserver());
		}
	}

	public ImageObserver getImageObserver() {
		return new ImageObserver() {

			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}
