package de.project.visualization.colorquantization.ui;

import com.sun.j3d.utils.picking.*;

import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.entities.Pixel;
import de.project.visualization.colorquantization.read.ImageReader;
import de.project.visualization.colorquantization.visu.ClusteringVisualization;

import com.sun.j3d.utils.geometry.*;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PointArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import java.awt.GraphicsConfiguration;

import java.awt.BorderLayout;

import java.awt.Label;
import java.io.IOException;
import java.applet.Applet;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;

public class Canvas extends JPanel {

	public Canvas(Histogram histo) {

		setLayout(new BorderLayout());

		GraphicsConfiguration config =

				SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);

		add("North", new Label("This is the top"));

		add("Center", canvas);

		add("South", new Label("This is the bottom"));

		new ClusteringVisualization(histo).Visualize(canvas);

	}


	public static void main(String[] args) {

		try {
			Histogram histo = new ImageReader("resources/kanye_small.jpg").getHistogram();
			System.out.println("read image. pixelsize: " + histo.getLength());

	        JFrame frame = new JFrame();
	        frame.add(new JScrollPane(new Canvas(histo)));
	        frame.setSize(300, 300);
	        frame.setVisible(true);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (IOException e) {
			System.out.println("Error reading Image");
			e.printStackTrace();
		}

	}

}