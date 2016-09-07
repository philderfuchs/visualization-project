package de.project.visualization.colorquantization.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import de.project.visualization.colorquantization.entities.Cluster;
import de.project.visualization.colorquantization.entities.VisualCluster;
import de.project.visualization.colorquantization.visu.KmeansVisualization;

public class ClusterLabel extends JLabel {

	private VisualCluster c;
	private KmeansVisualization visu;
	
	public ClusterLabel(VisualCluster c, KmeansVisualization visu) {
		this.visu = visu;
		this.c = c;
		setBackground(new Color(c.getCenter().getR(), c.getCenter().getG(), c.getCenter().getB()));
		setOpaque(true);
		setPreferredSize(new Dimension(100, 100));
		addMouseListener(new LabelAdapter());
	}

	class LabelAdapter extends MouseAdapter {

		public void mouseEntered(MouseEvent e) {
			visu.showCluster(c);
		}
		
		public void mouseExited(MouseEvent e) {
			visu.hideCluster();
		}

	}

}
