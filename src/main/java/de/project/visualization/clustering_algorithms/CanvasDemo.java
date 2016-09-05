package de.project.visualization.clustering_algorithms;

import com.sun.j3d.utils.picking.*;

import com.sun.j3d.utils.universe.SimpleUniverse;

import com.sun.j3d.utils.geometry.*;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import java.awt.GraphicsConfiguration;

import java.awt.BorderLayout;

import java.awt.Label;

import java.applet.Applet;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;

public class CanvasDemo extends Applet {

	public CanvasDemo() {

		setLayout(new BorderLayout());

		GraphicsConfiguration config =

				SimpleUniverse.getPreferredConfiguration();

		Canvas3D canvas = new Canvas3D(config);

		add("North", new Label("This is the top"));

		add("Center", canvas);

		add("South", new Label("This is the bottom"));

		this.draw3dCanvas(canvas);

	}
	
	public void draw3dCanvas(Canvas3D canvas) {
		
		BranchGroup group = new BranchGroup();
		
//		TransformGroup tg = new TransformGroup();
//		Transform3D transform = new Transform3D();
//		Vector3f vector = new Vector3f(0.2f, .0f, .0f);
//		transform.setTranslation(vector);
//		tg.setTransform(transform);
//		tg.addChild(new ColorCube(0.3));
		
		Appearance appearance = new Appearance();
		appearance.setPolygonAttributes(
				new PolygonAttributes(PolygonAttributes.POLYGON_LINE,
						PolygonAttributes.CULL_BACK, 0.0f));
		
		Box box = new Box(250, 250, 250, appearance);
		
		TransformGroup transformGroup = new TransformGroup();
		Transform3D transform3d = new Transform3D();
		transform3d.setScale(1.0f/250.0f);
		transformGroup.setTransform(transform3d);
		transformGroup.addChild(box);
		
		group.addChild(transformGroup);

		SimpleUniverse universe = new SimpleUniverse(canvas);
		universe.addBranchGraph(group);

		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		orbit.setSchedulingBounds(new BoundingSphere());
		universe.getViewingPlatform().setViewPlatformBehavior(orbit);
		universe.getViewingPlatform().setNominalViewingTransform();

		
	}

	public static void main(String[] args) {

		CanvasDemo demo = new CanvasDemo();

		new MainFrame(demo, 400, 400);

	}

}