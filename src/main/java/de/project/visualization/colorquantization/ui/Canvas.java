package de.project.visualization.colorquantization.ui;

import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.read.ImageReader;
import de.project.visualization.colorquantization.visu.HistogramVisualization;
import de.project.visualization.colorquantization.visu.KmeansVisualization;

import javax.media.j3d.Canvas3D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GraphicsConfiguration;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Canvas extends JPanel implements ActionListener {

	private KmeansVisualization kmeansVisu;
	private Histogram histo;
	private SimpleUniverse universe;
	
	private static String filename = "djmel.jpg";

	public Canvas() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);
		add("Center", canvas);

		
		JPanel southPanel = new JPanel();
		JButton init = new JButton("init");
		init.addActionListener(this);
		JButton step = new JButton("step");
		step.addActionListener(this);

		southPanel.add(init);
		southPanel.add(step);
		southPanel.add(new ClusterLabel());
		add("South", southPanel);
		add("North", new JLabel(filename));

		try {
			histo = new ImageReader("resources/" + filename).getHistogram();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HistogramVisualization visu = new HistogramVisualization(histo);
		universe = visu.visualizeHistogram(canvas);
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("init")) {
			if (kmeansVisu != null) {
				kmeansVisu.destroyVisualization();
			}
			kmeansVisu = new KmeansVisualization(5, universe);
			kmeansVisu.initKmeans(histo);
		}
		if (e.getActionCommand().equals("step")) {
			kmeansVisu.step(histo);
		}

	}

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.add(new JScrollPane(new Canvas()));
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}