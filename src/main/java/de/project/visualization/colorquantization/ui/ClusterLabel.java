package de.project.visualization.colorquantization.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import de.project.visualization.colorquantization.entities.Cluster;
import de.project.visualization.colorquantization.entities.VisualCluster;
import de.project.visualization.colorquantization.visu.ClusteringAlgorithmVisualization;
import de.project.visualization.colorquantization.visu.KmeansVisualization;

public class ClusterLabel extends JLabel {

	private VisualCluster c;
	private ClusteringAlgorithmVisualization visu;
	private boolean active;

	public ClusterLabel(VisualCluster c, ClusteringAlgorithmVisualization visu) {
		this.visu = visu;
		this.c = c;
		this.active = true;
		setBackground(new Color(c.getCenter().getR(), c.getCenter().getG(), c.getCenter().getB()));
		setOpaque(true);
		setPreferredSize(new Dimension(100, 100));
		addMouseListener(new LabelAdapter());
	}

	public ClusterLabel(VisualCluster c, ClusteringAlgorithmVisualization visu, boolean active) {
		this(c, visu);
		this.active = active;
	}

	class LabelAdapter extends MouseAdapter {

		public void mouseEntered(MouseEvent e) {
			if (active) {
				visu.hideAllClusters();
				visu.showCluster(c);
				System.out.println("Count: " + c.getHistogram().getCountOfPixels());
				System.out.println("Length : " + c.getHistogram().getLength());

			}
		}

		public void mouseExited(MouseEvent e) {
			if (active) {
				visu.hideCluster(c);
			}
		}

	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
