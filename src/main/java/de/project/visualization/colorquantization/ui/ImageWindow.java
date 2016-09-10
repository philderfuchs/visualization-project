package de.project.visualization.colorquantization.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ImageWindow extends JFrame {

	public ImageWindow (File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException ex) {
			// handle exception...
		}
		ImagePanel iPanel = new ImagePanel(image);
		add(iPanel);
		setVisible(true);
		setResizable(false);
		setSize(iPanel.getImageWidth(), iPanel.getImageHeight());
		
	}
	
}
