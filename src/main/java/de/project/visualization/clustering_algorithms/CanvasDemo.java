package de.project.visualization.clustering_algorithms;

import com.sun.j3d.utils.picking.*;

import com.sun.j3d.utils.universe.SimpleUniverse;

import com.sun.j3d.utils.geometry.*;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
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

		TransformGroup transformGroupSphere = new TransformGroup();
		Transform3D transform3dSphere = new Transform3D();
		transform3dSphere.setTranslation(
				new Vector3f((255.0f / 255.0f) - 0.5f, (0.0f / 255.0f) - 0.5f, (0.0f / 255.0f) - 0.5f));
		transformGroupSphere.setTransform(transform3dSphere);
		Appearance appearanceSphere = new Appearance();
		appearanceSphere.setColoringAttributes(new ColoringAttributes(1.0f, 0, 0, ColoringAttributes.FASTEST));
		transformGroupSphere.addChild(new Sphere(.2f, appearanceSphere));

		group.addChild(transformGroupSphere);
		
		Appearance boxAppearance = new Appearance();
		boxAppearance.setPolygonAttributes(
				new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, .0f));
		group.addChild(new Box(0.5f, 0.5f, 0.5f, boxAppearance));

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