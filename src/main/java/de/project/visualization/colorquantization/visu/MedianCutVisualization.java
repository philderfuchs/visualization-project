package de.project.visualization.colorquantization.visu;

import java.util.ArrayList;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
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
import de.project.visualization.colorquantization.entities.VisualCluster;

public class MedianCutVisualization implements ClusteringAlgorithmVisualization {

	private SimpleUniverse universe;
	private BranchGroup cubesGroup;
	private BranchGroup activeValues;
	private MedianCut medianCut;
	private int k;
	private static KmeansVisualization instance = null;
	private ArrayList<VisualCluster> vClusters;

	public MedianCutVisualization(int k, SimpleUniverse universe) {
		this.activeValues = new BranchGroup();
		activeValues.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		activeValues.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		this.universe = universe;
		this.universe.addBranchGraph(activeValues);
		this.k = k;
	}

	public void destroyVisualization() {
		// TODO Auto-generated method stub

	}

	public ArrayList<VisualCluster> init(Histogram histo) {
		cubesGroup = new BranchGroup();
		cubesGroup.setCapability(BranchGroup.ALLOW_DETACH);

		medianCut = new MedianCut(k);
		ArrayList<Cube> cubes = medianCut.init(histo);
		vClusters = new ArrayList<VisualCluster>();

		for (Cube c : cubes) {
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
					new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_NONE, 0.0f));
			appearance.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST, 0.4f));
			appearance.setColoringAttributes(new ColoringAttributes((float) c.getCenter().getR() / 255.0f,
					(float) c.getCenter().getG() / 255.0f, (float) c.getCenter().getB() / 255.0f,
					ColoringAttributes.SHADE_FLAT));
			// Sphere clusterCenter = new Sphere(0.04f, appearance);

			Box box = new Box((float) c.getRdiff() / (255.0f * 2.0f),
					(float) c.getGdiff() / (255.0f * 2.0f),
					(float) c.getBdiff() / (255.0f * 2.0f),
					appearance);
			box.setCapability(Primitive.ENABLE_APPEARANCE_MODIFY);
			transformGroup.addChild(box);
			vClusters.add(new VisualCluster(c, box));
			cubesGroup.addChild(transformGroup);
		}
		universe.addBranchGraph(cubesGroup);
		return vClusters;
	}

	public ArrayList<VisualCluster> step(Histogram histo) {
		// TODO Auto-generated method stub
		return null;
	}

	public void showCluster(VisualCluster c) {
		// TODO Auto-generated method stub

	}

	public void hideCluster(VisualCluster c) {
		// TODO Auto-generated method stub

	}

	public void showAllClusters() {
		// TODO Auto-generated method stub

	}

	public void hideAllClusters() {
		// TODO Auto-generated method stub

	}

	public ArrayList<VisualCluster> getVisualClusters() {
		// TODO Auto-generated method stub
		return null;
	}

	private Coordinates calculateCoordinates(int x, int y, int z) {
		return new Coordinates(((float) x / 255.0f) - 0.5f, ((float) y / 255.0f) - 0.5f, ((float) z / 255.0f) - 0.5f);
	}

}
