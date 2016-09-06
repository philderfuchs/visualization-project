package de.project.visualization.colorquantization.ui;

import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.read.ImageReader;
import de.project.visualization.colorquantization.visu.HistogramVisualization;
import de.project.visualization.colorquantization.visu.KmeansVisualization;

import javax.media.j3d.Canvas3D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GraphicsConfiguration;

import java.awt.BorderLayout;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Canvas extends JPanel implements ActionListener {

	private KmeansVisualization kmeansVisu;
	private Histogram histo;

	
	public Canvas() {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);
		add("North", new Label("This is the top"));
		add("Center", canvas);
		
		JButton button = new JButton("move");
		button.addActionListener(this);
		add("South", button);
		try {
			histo = new ImageReader("resources/kanye_small.jpg").getHistogram();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HistogramVisualization visu = new HistogramVisualization(histo);
		SimpleUniverse universe = visu.visualizeHistogram(canvas);
		
		kmeansVisu = new KmeansVisualization(universe);
		kmeansVisu.initKmeans();
	}

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		frame.add(new JScrollPane(new Canvas()));
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("move")) {
//			System.out.println("clickediclick");
			kmeansVisu.step(histo);
		}
		
	}

}