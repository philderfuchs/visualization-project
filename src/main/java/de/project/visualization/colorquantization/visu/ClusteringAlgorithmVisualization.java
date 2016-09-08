package de.project.visualization.colorquantization.visu;

import java.util.ArrayList;
import de.project.visualization.colorquantization.entities.Histogram;
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
