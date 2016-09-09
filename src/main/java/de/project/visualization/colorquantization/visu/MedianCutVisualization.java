package de.project.visualization.colorquantization.visu;

import java.util.ArrayList;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Box;

import de.project.visualization.colorquantization.clustering.Kmeans;
import de.project.visualization.colorquantization.clustering.MedianCut;
import de.project.visualization.colorquantization.entities.Cluster;
import de.project.visualization.colorquantization.entities.Coordinates;
import de.project.visualization.colorquantization.entities.Cube;
import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.entities.Pixel;
import de.project.visualization.colorquantization.entities.VisualCluster;

public class MedianCutVisualization implements ClusteringAlgorithmVisualization {

	private SimpleUniverse universe;
	private BranchGroup cubesGroup;
	private BranchGroup activeValues;
	private MedianCut medianCut;
	private static KmeansVisualization instance = null;
	private ArrayList<VisualCluster> vClusters;

	public MedianCutVisualization(SimpleUniverse universe) {
		this.activeValues = new BranchGroup();
		activeValues.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		activeValues.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		this.universe = universe;
		this.universe.addBranchGraph(activeValues);
	}

	public void destroyVisualization() {
		if (cubesGroup != null) {
			cubesGroup.detach();
			this.hideAllClusters();
		}
	}

	public ArrayList<VisualCluster> init(Histogram histo) {
		medianCut = new MedianCut();

		return this.step(histo);
	}

	public ArrayList<VisualCluster> step(Histogram histo) {
		this.destroyVisualization();
		cubesGroup = new BranchGroup();
		cubesGroup.setCapability(BranchGroup.ALLOW_DETACH);

		vClusters = new ArrayList<VisualCluster>();
		ArrayList<Cube> cubes = medianCut.step(histo);

		for (Cube c : cubes) {
			Transform3D transform3d = new Transform3D();
			Coordinates coordinates = new Coordinates(((float) c.getCenter().getR() / 255.0f) - 0.5f,
					((float) c.getCenter().getG() / 255.0f) - 0.5f, ((float) c.getCenter().getB() / 255.0f) - 0.5f);
			transform3d.setTranslation(new Vector3f(coordinates.getX(), coordinates.getY(), coordinates.getZ()));
			TransformGroup transformGroup = new TransformGroup(transform3d);
			transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			Box coloredBox = createCubeModel(c, true);
			Box edgedBox = createCubeModel(c, false);
			transformGroup.addChild(coloredBox);
			transformGroup.addChild(edgedBox);
			vClusters.add(new VisualCluster(c, coloredBox));
			cubesGroup.addChild(transformGroup);
		}
		universe.addBranchGraph(cubesGroup);

		return vClusters;
	}

	private Box createCubeModel(Cube c, boolean colored) {
		Appearance appearance = new Appearance();
		appearance.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
		appearance.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		if (colored) {
			appearance.setPolygonAttributes(
					new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0.0f));
			appearance.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST, 0.8f));
			appearance.setColoringAttributes(
					new ColoringAttributes((float) c.getCenter().getR() / 255.0f, (float) c.getCenter().getG() / 255.0f,
							(float) c.getCenter().getB() / 255.0f, ColoringAttributes.SHADE_FLAT));
		} else {
			appearance.setPolygonAttributes(
					new PolygonAttributes(PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_NONE, 0.0f));
			appearance.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST, 0.6f));
		}

		// Sphere clusterCenter = new Sphere(0.04f, appearance);

		Box box = new Box((float) c.getRdiff() / (255.0f * 2.0f), (float) c.getGdiff() / (255.0f * 2.0f),
				(float) c.getBdiff() / (255.0f * 2.0f), appearance);
		box.setCapability(Primitive.ENABLE_APPEARANCE_MODIFY);
		return box;
	}

	public void showCluster(VisualCluster c) {
		BranchGroup values = new BranchGroup();
		values.setCapability(BranchGroup.ALLOW_DETACH);
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
			values.addChild(shape);
		}

		activeValues.addChild(values);
	}

	public void hideCluster(VisualCluster c) {
		activeValues.removeAllChildren();
	}

	public void showAllClusters() {
		for(VisualCluster c : vClusters){
			this.showCluster(c);
		}
	}
	
	public void hideAllClusters() {
		for(VisualCluster c : vClusters){
			this.hideCluster(c);
		}
	}
	
	public ArrayList<VisualCluster> getVisualClusters() {
		// TODO Auto-generated method stub
		return null;
	}

	private Coordinates calculateCoordinates(int x, int y, int z) {
		return new Coordinates(((float) x / 255.0f) - 0.5f, ((float) y / 255.0f) - 0.5f, ((float) z / 255.0f) - 0.5f);
	}

}
