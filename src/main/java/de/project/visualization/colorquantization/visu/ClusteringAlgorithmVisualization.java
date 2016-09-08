package de.project.visualization.colorquantization.visu;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Node;
import javax.media.j3d.PointArray;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;

import de.project.visualization.colorquantization.clustering.Kmeans;
import de.project.visualization.colorquantization.entities.Cluster;
import de.project.visualization.colorquantization.entities.Coordinates;
import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.entities.Pixel;
import de.project.visualization.colorquantization.entities.VisualCluster;

public interface ClusteringAlgorithmVisualization {

	public void destroyVisualization();

	public ArrayList<VisualCluster> init(Histogram histo);

	public ArrayList<VisualCluster> step(Histogram histo);

	public void showCluster(VisualCluster c);

	/*
	 * right now: strange behaviour hides only the cluster center of given
	 * visualcluster, but hides ALL values.
	 */
	public void hideCluster(VisualCluster c);

	public void showAllClusters();

	public void hideAllClusters();

	public ArrayList<VisualCluster> getVisualClusters();

}
