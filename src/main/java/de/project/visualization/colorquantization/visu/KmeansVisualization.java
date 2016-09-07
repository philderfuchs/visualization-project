package de.project.visualization.colorquantization.visu;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Node;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.entities.Cluster;
import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.entities.Pixel;
import de.project.visualization.colorquantization.kmeans.Kmeans;

public class KmeansVisualization {
	
	private SimpleUniverse universe;
	private BranchGroup clusterGroup;
	private Kmeans kmeans;
	private int k;

	public KmeansVisualization(int k, SimpleUniverse universe) {
		this.universe = universe;
//		clusterGroup = new BranchGroup();
//		clusterGroup.setCapability(BranchGroup.ALLOW_DETACH);
		this.k = k;

	}
	
	public void destroyVisualization(){
		clusterGroup.detach();
	}

	public ArrayList<Cluster> initKmeans(Histogram histo) {

		clusterGroup = new BranchGroup();
		clusterGroup.setCapability(BranchGroup.ALLOW_DETACH);

		kmeans = new Kmeans(k);
		ArrayList<Cluster> clusters = kmeans.step(histo);
		for (Cluster c : clusters) {
			Transform3D transform3d = new Transform3D();
			transform3d.setTranslation(new Vector3f(((float) c.getCenter().getR() / 255.0f) - 0.5f,
					((float) c.getCenter().getG() / 255.0f) - 0.5f, ((float) c.getCenter().getB() / 255.0f) - 0.5f));
			TransformGroup transformGroup = new TransformGroup(transform3d);
			transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			Appearance appearance = new Appearance();
			appearance.setPolygonAttributes(
					new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0.0f));

			transformGroup.addChild(new Sphere(0.05f, appearance));
			clusterGroup.addChild(transformGroup);
		}
		universe.addBranchGraph(clusterGroup);
		return clusters;
	}

	public ArrayList<Cluster> step(Histogram histo) {
	
		kmeans.step(histo);
		ArrayList<Cluster> clusters = kmeans.getClusters();
		Enumeration<Node> children = clusterGroup.getAllChildren();
		int i = 0;
		while (children.hasMoreElements()) {
			TransformGroup transformGroup = (TransformGroup) children.nextElement();
			Transform3D transform3d = new Transform3D();
			Pixel p = clusters.get(i++).getCenter();
			transform3d.setTranslation(new Vector3f(((float) p.getR() / 255.0f) - 0.5f,
					((float) p.getG() / 255.0f) - 0.5f, ((float) p.getB() / 255.0f) - 0.5f));
			transformGroup.setTransform(transform3d);
		}
		return clusters;
	}
}
