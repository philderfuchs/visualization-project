package de.project.visualization.colorquantization.ui;

import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.read.ImageReader;
import de.project.visualization.colorquantization.visu.ClusteringVisualization;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GraphicsConfiguration;

import java.awt.BorderLayout;

import java.awt.Label;
import java.io.IOException;

public class Canvas extends JPanel {

	public Canvas() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);
		add("North", new Label("This is the top"));
		add("Center", canvas);
		add("South", new Label("This is the bottom"));

		try {
			Histogram histo = new ImageReader("resources/kanye_small.jpg").getHistogram();
			ClusteringVisualization visu = new ClusteringVisualization(histo);
			visu.visualizeHistogram(canvas);
			visu.showClusters();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.add(new JScrollPane(new Canvas()));
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}