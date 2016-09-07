package de.project.visualization.colorquantization.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class ClusterLabel extends JLabel {
	
	public ClusterLabel() {
		setBackground(Color.blue);
		setOpaque(true);
		setPreferredSize(new Dimension(100, 100));
		addMouseListener(new LabelAdapter());
	}
	
	class LabelAdapter extends MouseAdapter {

        public void mouseEntered(MouseEvent e) {
            System.out.println("over");

        }

    }

}
