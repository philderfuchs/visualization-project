package de.project.visualization.colorquantization.visu;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Node;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.PositionPathInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import de.project.visualization.colorquantization.entities.Cluster;
import de.project.visualization.colorquantization.entities.Coordinates;
import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.entities.Pixel;
import de.project.visualization.colorquantization.entities.VisualCluster;
import de.project.visualization.colorquantization.kmeans.Kmeans;

public class KmeansVisualization {

	private SimpleUniverse universe;
	private BranchGroup clusterCentersGroup;
	private BranchGroup activeValuesGroup;
	private Kmeans kmeans;
	private int k;
	private static KmeansVisualization instance = null;

	public KmeansVisualization(int k, SimpleUniverse universe) {
		this.universe = universe;

		this.k = k;
	}

	public void destroyVisualization() {
		clusterCentersGroup.detach();
	}

	public ArrayList<VisualCluster> initKmeans(Histogram histo) {

		clusterCentersGroup = new BranchGroup();
		clusterCentersGroup.setCapability(BranchGroup.ALLOW_DETACH);

		kmeans = new Kmeans(k);
		ArrayList<Cluster> clusters = kmeans.step(histo);
		ArrayList<VisualCluster> vClusters = new ArrayList<VisualCluster>();

		for (Cluster c : clusters) {
			Transform3D transform3d = new Transform3D();
			Coordinates coordinates = new Coordinates(((float) c.getCenter().getR() / 255.0f) - 0.5f,
					((float) c.getCenter().getG() / 255.0f) - 0.5f, ((float) c.getCenter().getB() / 255.0f) - 0.5f);
			transform3d.setTranslation(new Vector3f(coordinates.getX(), coordinates.getY(), coordinates.getZ()));
			TransformGroup transformGroup = new TransformGroup(transform3d);
			transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			Appearance appearance = new Appearance();
			appearance.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
			appearance.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
			appearance.setPolygonAttributes(
					new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0.0f));
			Sphere clusterCenter = new Sphere(0.05f, appearance);
			clusterCenter.setCapability(Primitive.ENABLE_APPEARANCE_MODIFY);
			transformGroup.addChild(clusterCenter);
			vClusters.add(new VisualCluster(c, clusterCenter));
			clusterCentersGroup.addChild(transformGroup);
		}
		universe.addBranchGraph(clusterCentersGroup);
		return vClusters;
	}

	public ArrayList<VisualCluster> step(Histogram histo) {

		// save original coordinates
		ArrayList<Coordinates> coordList = new ArrayList<Coordinates>();
		for (Cluster c : kmeans.getClusters()) {
			coordList.add(calculateCoordinates(c.getCenter().getR(), c.getCenter().getG(), c.getCenter().getB()));
		}

		kmeans.step(histo);
		ArrayList<Cluster> clusters = kmeans.getClusters();
		ArrayList<VisualCluster> vClusters = new ArrayList<VisualCluster>();

		Enumeration<Node> children = clusterCentersGroup.getAllChildren();
		int i = 0;

		while (children.hasMoreElements()) {
			TransformGroup transformGroup = (TransformGroup) children.nextElement();
			Transform3D transform3d = new Transform3D();
			Cluster c = clusters.get(i);
			Coordinates coordinates = calculateCoordinates(c.getCenter().getR(), c.getCenter().getG(),
					c.getCenter().getB());
			
			int count = 20;
			float stepX = (coordinates.getX() - coordList.get(i).getX()) / (float) count;
			float stepY = (coordinates.getY() - coordList.get(i).getY()) / (float) count;
			float stepZ = (coordinates.getZ() - coordList.get(i).getZ()) / (float) count;

			for (int j = 1; j <= count; j++) {
				transform3d.setTranslation(new Vector3f(coordList.get(i).getX() + j * stepX,
						coordList.get(i).getY() + j * stepY, coordList.get(i).getZ() + j * stepZ));
				transformGroup.setTransform(transform3d);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// transform3d.setTranslation(new Vector3f(coordinates.getX(),
			// coordinates.getY(), coordinates.getZ()));
			// transformGroup.setTransform(transform3d);
			vClusters.add(new VisualCluster(c, (Primitive) transformGroup.getChild(0)));

			i++;
		}

		return vClusters;
	}

	public void showCluster(VisualCluster c) {
		activeValuesGroup = new BranchGroup();
		activeValuesGroup.setCapability(BranchGroup.ALLOW_DETACH);
		if (c.getHistogram().getLength() > 0) {

			PointArray pointArray = new PointArray(c.getHistogram().getLength(), GeometryArray.COORDINATES);
			Point3f[] pointCoordinates = new Point3f[c.getHistogram().getLength()];

			int i = 0;
			for (Pixel p : c.getHistogram().getPixelList()) {
				pointCoordinates[i++] = new Point3f(((float) p.getR() / 255.0f) - 0.5f,
						((float) p.getG() / 255.0f) - 0.5f, ((float) p.getB() / 255.0f) - 0.5f);
			}
			pointArray.setCoordinates(0, pointCoordinates);

			PointAttributes pointAttributes = new PointAttributes();
			pointAttributes.setPointAntialiasingEnable(true);

			Appearance valuesAppearance = new Appearance();
			valuesAppearance.setColoringAttributes(
					new ColoringAttributes((float) c.getCenter().getR() / 255.0f, (float) c.getCenter().getG() / 255.0f,
							(float) c.getCenter().getB() / 255.0f, ColoringAttributes.SHADE_FLAT));
			valuesAppearance.setPointAttributes(pointAttributes);
			Shape3D shape = new Shape3D(pointArray, valuesAppearance);
			activeValuesGroup.addChild(shape);
		}

		// c.getPrimitive().getAppearance().setPolygonAttributes(
		// new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
		// PolygonAttributes.CULL_NONE, 0.0f));
		c.getPrimitive().getAppearance()
				.setColoringAttributes(new ColoringAttributes((float) c.getCenter().getR() / 255.0f,
						(float) c.getCenter().getG() / 255.0f, (float) c.getCenter().getB() / 255.0f,
						ColoringAttributes.SHADE_FLAT));

		universe.addBranchGraph(activeValuesGroup);
	}

	public void hideCluster(VisualCluster c) {
		activeValuesGroup.detach();
		c.getPrimitive().getAppearance().setPolygonAttributes(
				new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0.0f));
		c.getPrimitive().getAppearance().setColoringAttributes(new ColoringAttributes());
	}

	private Coordinates calculateCoordinates(int x, int y, int z) {
		return new Coordinates(((float) x / 255.0f) - 0.5f, ((float) y / 255.0f) - 0.5f, ((float) z / 255.0f) - 0.5f);
	}
}
