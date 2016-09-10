package de.project.visualization.colorquantization.ui;

import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.clustering.ClusteringAlgorithm;
import de.project.visualization.colorquantization.entities.*;
import de.project.visualization.colorquantization.read.ImageReader;
import de.project.visualization.colorquantization.visu.ClusteringAlgorithmVisualization;
import de.project.visualization.colorquantization.visu.HistogramVisualization;
import de.project.visualization.colorquantization.visu.KmeansVisualization;
import de.project.visualization.colorquantization.visu.MedianCutVisualization;

import javax.media.j3d.Canvas3D;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.GraphicsConfiguration;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public class MainWindow extends JPanel implements ActionListener, ItemListener {

	// general settings
	private static int windowWidth = 700;
	private static int windowHeight = 700;
	private final static String KMEANS = "K-Means";
	private final static String MEDIANCUT = "Median Cut";
	private final static Color controlPanelColor = Color.darkGray;
	private ClusteringAlgorithm activeAlgorithm = ClusteringAlgorithm.KMEANS;

	// UI Elements
	// Where instance variables are declared:
	private JPanel cardsPanel;
	private JPanel clusterColorsPanel;
	private JTextField k;
	private ArrayList<ClusterLabel> clusterLabels;

	private ClusteringAlgorithmVisualization algoVisu;
	private Histogram histo;
	private SimpleUniverse universe;
	private boolean showClustersMode = false;

	private static String filename = "macmiller.png";

	public MainWindow() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);
		add("Center", canvas);

		// JPanel controlPanel = new JPanel();
		// controlPanel.setBackground(Color.BLACK);
		JPanel kmeansPanel = new JPanel();
		kmeansPanel.setBackground(controlPanelColor);
		JPanel medianCutPanel = new JPanel();
		medianCutPanel.setBackground(controlPanelColor);

		k = new JTextField("5", 5);
		JButton initKeans = new JButton("restart");
		initKeans.addActionListener(this);
		JButton stepKmeans = new JButton("step");
		stepKmeans.addActionListener(this);
		kmeansPanel.add(k);
		kmeansPanel.add(initKeans);
		kmeansPanel.add(stepKmeans);

		JButton meandCutInit = new JButton("restart");
		meandCutInit.addActionListener(this);
		JButton medianCutStep = new JButton("step");
		medianCutStep.addActionListener(this);
		medianCutPanel.add(meandCutInit);
		medianCutPanel.add(medianCutStep);

		cardsPanel = new JPanel(new CardLayout());
		cardsPanel.add(KMEANS, kmeansPanel);
		cardsPanel.add(MEDIANCUT, medianCutPanel);

		JPanel comboBoxPane = new JPanel();
		comboBoxPane.setBackground(controlPanelColor);
		String comboBoxItems[] = { KMEANS, MEDIANCUT };
		JComboBox<String> cb = new JComboBox<String>(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		comboBoxPane.add(cb);

		JPanel controlPanel = new JPanel(new BorderLayout());
		controlPanel.setBackground(controlPanelColor);
		JButton showAll = new JButton("show all clusters");
		showAll.addActionListener(this);
		controlPanel.add(comboBoxPane, BorderLayout.LINE_START);
		controlPanel.add(cardsPanel, BorderLayout.CENTER);
		controlPanel.add(showAll, BorderLayout.LINE_END);
		add("North", controlPanel);

		clusterColorsPanel = new JPanel();
		clusterColorsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		clusterColorsPanel.setBackground(new Color(0, 0, 0));
		clusterColorsPanel.setPreferredSize(new Dimension(0, 100));
		add("South", clusterColorsPanel);

		try {
			histo = new ImageReader("resources/" + filename).getHistogram();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HistogramVisualization visu = new HistogramVisualization(histo);
		universe = visu.visualizeHistogram(canvas);

		this.initClusteringVisualisation();
	}

	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout) (cardsPanel.getLayout());
		String item = (String) evt.getItem();
		cl.show(cardsPanel, item);
		if (item.equals(KMEANS)) {
			activeAlgorithm = ClusteringAlgorithm.KMEANS;
		} else if (item.equals(MEDIANCUT)) {
			activeAlgorithm = ClusteringAlgorithm.MEDIANCUT;
		}
		initClusteringVisualisation();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("restart")) {
			initClusteringVisualisation();
		}
		if (e.getActionCommand().equals("step")) {
			algoVisu.hideAllClusters();
			setUpLabels(algoVisu.step(histo));
			if (showClustersMode) {
				algoVisu.showAllClusters();
			}
		}
		if (e.getActionCommand().equals("show all clusters")) {
			showClustersMode = !showClustersMode;
			if (showClustersMode) {
				algoVisu.showAllClusters();
				for (ClusterLabel l : clusterLabels) {
					l.setActive(false);
				}
			} else {
				algoVisu.hideAllClusters();
				for (ClusterLabel l : clusterLabels) {
					l.setActive(true);
				}
			}
		}

	}

	private void initClusteringVisualisation() {
		if (algoVisu != null) {
			algoVisu.destroyVisualization();
		}
		if (activeAlgorithm == ClusteringAlgorithm.KMEANS) {
			algoVisu = new KmeansVisualization(Integer.parseInt(k.getText()), universe);
		} else if (activeAlgorithm == ClusteringAlgorithm.MEDIANCUT) {
			algoVisu = new MedianCutVisualization(universe);
		}
		setUpLabels(algoVisu.init(histo));
		if (showClustersMode) {
			algoVisu.showAllClusters();
		}
	}

	private void setUpLabels(ArrayList<VisualCluster> clusters) {
		clusterColorsPanel.removeAll();

		int labelWidth = this.windowWidth / clusters.size();
		clusterLabels = new ArrayList<ClusterLabel>();
		for (VisualCluster c : clusters) {
			ClusterLabel label = new ClusterLabel(c, algoVisu, labelWidth, 100, !showClustersMode);
			clusterLabels.add(label);
			clusterColorsPanel.add(label);
		}
		clusterColorsPanel.revalidate();
		clusterColorsPanel.repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.add(new MainWindow());
		frame.setSize(windowWidth, windowHeight);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}