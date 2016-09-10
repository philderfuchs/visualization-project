package de.project.visualization.colorquantization.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import de.project.visualization.colorquantization.read.ImageQuantizer;

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
		
//		JPanel container = new JPanel();
		ImagePanel original = new ImagePanel(image);
		quantized = new ImagePanel(new ImageQuantizer().quantize(image, vClusters));
		add(original);
//		add(Box.createRigidArea(new Dimension(original.getImageWidth(), original.getImageHeight())));
		add(quantized);
//		add(Box.createRigidArea(new Dimension(quantized.getImageWidth(), quantized.getImageHeight())));
//		add(original, BorderLayout.CENTER);
//		add(quantized, BorderLayout.CENTER);
		setVisible(true);
		setResizable(false);
		// container.setSize(original.getImageWidth()*2,
		// original.getImageHeight());
		setSize(quantized.getImageWidth() * 2, quantized.getImageHeight());
	}

	public void refresh(ArrayList<VisualCluster> vClusters) {
		this.remove(quantized);
		quantized = new ImagePanel(new ImageQuantizer().quantize(image, vClusters));
		add(quantized);
		revalidate();
		repaint();
	}

}
