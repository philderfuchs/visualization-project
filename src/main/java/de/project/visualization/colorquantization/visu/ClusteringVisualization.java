package de.project.visualization.colorquantization.visu;

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
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.entities.*;
import de.project.visualization.kmeans.Kmeans;

public class ClusteringVisualization {

	private Histogram histo;
	private SimpleUniverse universe;

	public ClusteringVisualization(Histogram histo) {
		super();
		this.histo = histo;
	}

	public void visualizeHistogram(Canvas3D canvas) {
		BranchGroup group = new BranchGroup();

		PointArray pointArray = new PointArray(histo.getLength(), GeometryArray.COORDINATES | GeometryArray.COLOR_3);
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
		
		universe = new SimpleUniverse(canvas);
		universe.addBranchGraph(group);

		OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		orbit.setSchedulingBounds(new BoundingSphere());
		universe.getViewingPlatform().setViewPlatformBehavior(orbit);
		universe.getViewingPlatform().setNominalViewingTransform();

	}

	public void showClusters() {
		Kmeans kmeans = new Kmeans(5);
		BranchGroup group = new BranchGroup();

		for (Cluster c : kmeans.getClusters()) {
			Transform3D transform3d = new Transform3D();
			transform3d.setTranslation(new Vector3f(((float) c.getCenter().getR() / 255.0f) - 0.5f,
					((float) c.getCenter().getG() / 255.0f) - 0.5f, ((float) c.getCenter().getB() / 255.0f) - 0.5f));
			TransformGroup transformGroup = new TransformGroup(transform3d);
			transformGroup.addChild(new ColorCube(0.01f));
			group.addChild(transformGroup);
		}
		universe.addBranchGraph(group);

		// Appearance appearanceSphere = new Appearance();
		// appearanceSphere.setColoringAttributes(new ColoringAttributes(1.0f,
		// 0, 0, ColoringAttributes.FASTEST));

//		group.addChild(clusterGroup);
	}

	public Histogram getHisto() {
		return histo;
	}

	public void setHisto(Histogram histo) {
		this.histo = histo;
	}

}
