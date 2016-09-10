package de.project.visualization.colorquantization.ui;

import java.io.File;

import javax.swing.JFrame;

public class ImageWindow extends JFrame {

	public ImageWindow (File file) {
		ImagePanel iPanel = new ImagePanel(file);
		add(iPanel);
		setVisible(true);
		setResizable(false);
		setSize(iPanel.getImageWidth(), iPanel.getImageHeight());
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}
