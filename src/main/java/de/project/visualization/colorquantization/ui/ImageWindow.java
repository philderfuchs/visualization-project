package de.project.visualization.colorquantization.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.project.visualization.colorquantization.entities.VisualCluster;
import de.project.visualization.colorquantization.process.ImageQuantizer;

public class ImageWindow extends JFrame {

	private ImagePanel quantized;
	private BufferedImage image;

	public ImageWindow(File file, ArrayList<VisualCluster> vClusters) {
		image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException ex) {
			// handle exception...
		}
		setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
		
		ImagePanel original = new ImagePanel(image);
		quantized = new ImagePanel(new ImageQuantizer().quantize(image, vClusters));
		add(original);
		add(quantized);
		setVisible(true);
		setResizable(false);
		setSize(quantized.getImageWidth() * 2, quantized.getImageHeight());
	}

	public void refresh(ArrayList<VisualCluster> vClusters) {
		this.remove(quantized);
		quantized = new ImagePanel(new ImageQuantizer().quantize(image, vClusters));
		add(quantized);
		revalidate();
		repaint();
	}
	
	public void destroy() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

}
