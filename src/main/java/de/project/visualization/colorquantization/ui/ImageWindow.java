package de.project.visualization.colorquantization.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.project.visualization.colorquantization.entities.VisualCluster;
import de.project.visualization.colorquantization.read.ImageQuantizer;

public class ImageWindow extends JFrame {

	public ImageWindow (File file, ArrayList<VisualCluster> vClusters) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException ex) {
			// handle exception...
		}
		ImagePanel iPanel = new ImagePanel(new ImageQuantizer().quantize(image, vClusters));
		add(iPanel);
		setVisible(true);
		setResizable(false);
		setSize(iPanel.getImageWidth(), iPanel.getImageHeight());
		
	}
	
}
