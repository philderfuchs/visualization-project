package de.project.visualization.colorquantization.ui;

import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.entities.*;
import de.project.visualization.colorquantization.read.ImageReader;
import de.project.visualization.colorquantization.visu.ClusteringAlgorithmVisualization;
import de.project.visualization.colorquantization.visu.HistogramVisualization;
import de.project.visualization.colorquantization.visu.KmeansVisualization;

import javax.media.j3d.Canvas3D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
import java.util.ArrayList;

public class Canvas extends JPanel implements ActionListener {

	private ClusteringAlgorithmVisualization kmeansVisu;
	private Histogram histo;
	private SimpleUniverse universe;
	private JPanel clusterColorsPanel;
	private JTextField k;
	private boolean showClustersMode = false;
	private ArrayList<ClusterLabel> clusterLabels;

	private static String filename = "kanye_small.jpg";

	public Canvas() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);
		add("Center", canvas);

		JPanel kmeansControlPanel = new JPanel();
		k = new JTextField("5", 5);
		JButton init = new JButton("init");
		init.addActionListener(this);
		JButton step = new JButton("step");
		step.addActionListener(this);

		kmeansControlPanel.add(k);
		kmeansControlPanel.add(init);
		kmeansControlPanel.add(step);
		add("North", kmeansControlPanel);

		clusterColorsPanel = new JPanel();
		add("South", clusterColorsPanel);

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
			kmeansVisu = new KmeansVisualization(Integer.parseInt(k.getText()), universe);
			setUpLabels(kmeansVisu.init(histo));
		}
		if (e.getActionCommand().equals("step")) {
			kmeansVisu.hideAllClusters();
			setUpLabels(kmeansVisu.step(histo));
			if (showClustersMode) {
				kmeansVisu.showAllClusters();
			}
		}
		if (e.getActionCommand().equals("all")) {
			showClustersMode = !showClustersMode;
			if (showClustersMode) {
				kmeansVisu.showAllClusters();
				for(ClusterLabel l : clusterLabels) {
					l.setActive(false);
				}
			} else {
				kmeansVisu.hideAllClusters();
				for(ClusterLabel l : clusterLabels) {
					l.setActive(true);
				}
			}
		}

	}

	private void setUpLabels(ArrayList<VisualCluster> clusters) {
		clusterColorsPanel.removeAll();
		JButton all = new JButton("all");
		all.addActionListener(this);
		clusterColorsPanel.add(all);
		
		clusterLabels = new ArrayList<ClusterLabel>();
		for (VisualCluster c : clusters) {
			ClusterLabel label = new ClusterLabel(c, kmeansVisu, !showClustersMode);
			clusterLabels.add(label);
			clusterColorsPanel.add(label);
		}
		clusterColorsPanel.revalidate();
		clusterColorsPanel.repaint();
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.add(new JScrollPane(new Canvas()));
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}