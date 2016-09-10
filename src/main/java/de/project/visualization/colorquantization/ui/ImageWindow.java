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

	private ImagePanel quantized;
	private BufferedImage image;
	
	public ImageWindow (File file, ArrayList<VisualCluster> vClusters) {
		image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException ex) {
			// handle exception...
		}
		quantized = new ImagePanel(new ImageQuantizer().quantize(image, vClusters));
		add(quantized);
		setVisible(true);
		setResizable(false);
		setSize(quantized.getImageWidth(), quantized.getImageHeight());
	}
	
	public void refresh(ArrayList<VisualCluster> vClusters) {
		this.remove(quantized);
		quantized = new ImagePanel(new ImageQuantizer().quantize(image, vClusters));
		add(quantized);
		revalidate();
		repaint();
	}
	
}
