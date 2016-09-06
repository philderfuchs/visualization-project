package de.project.visualization.colorquantization.view;

import com.sun.j3d.utils.picking.*;

import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.entities.Pixel;
import de.project.visualization.colorquantization.read.ImageReader;

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

public class CanvasDemo extends Applet {

	public CanvasDemo(Histogram histo) {

		setLayout(new BorderLayout());

		GraphicsConfiguration config =

				SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);

		add("North", new Label("This is the top"));

		add("Center", canvas);

		add("South", new Label("This is the bottom"));

		this.draw3dCanvas(canvas, histo);

	}

	public void draw3dCanvas(Canvas3D canvas, Histogram histo) {

		BranchGroup group = new BranchGroup();

		PointArray pointArray = new PointArray(histo.getLength(), GeometryArray.COORDINATES | GeometryArray.COLOR_3 );
		Point3f[] pointCoordinates = new Point3f[histo.getLength()];
		Color3f[] pointColors = new Color3f[histo.getLength()];

		int i = 0;
		for (Pixel p : histo.getPixelList()) {

			pointCoordinates[i] = new Point3f(((float) p.getR() / 255.0f) - 0.5f, ((float) p.getG() / 255.0f) - 0.5f,
					((float) p.getB() / 255.0f) - 0.5f);
			pointColors[i++] = new Color3f(((float) p.getR() / 255.0f), ((float) p.getG() / 255.0f),
					((float) p.getB() / 255.0f));
			

		}
		pointArray.setCoordinates(0, pointCoordinates);
		pointArray.setColors(0, pointColors);

		Shape3D shape = new Shape3D(pointArray);

		Appearance boxAppearance = new Appearance();
		boxAppearance.setPolygonAttributes(
				new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, .0f));
		group.addChild(new Box(0.5f, 0.5f, 0.5f, boxAppearance));
		group.addChild(shape);

		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.addBranchGraph(group);

		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		orbit.setSchedulingBounds(new BoundingSphere());
		universe.getViewingPlatform().setViewPlatformBehavior(orbit);
		universe.getViewingPlatform().setNominalViewingTransform();

	}

	public static void main(String[] args) {

		try {
			Histogram histo = new ImageReader("resources/kanye_small.jpg").getHistogram();
			System.out.println("read image. pixelsize: " + histo.getLength());

			CanvasDemo demo = new CanvasDemo(histo);
			new MainFrame(demo, 400, 400);
		} catch (IOException e) {
			System.out.println("Error reading Image");
			e.printStackTrace();
		}

	}

}