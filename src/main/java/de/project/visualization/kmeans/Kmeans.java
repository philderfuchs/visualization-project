package de.project.visualization.kmeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.project.visualization.colorquantization.entities.*;

public class Kmeans {

	private int k;
	private ArrayList<Cluster> clusters;

	public Kmeans(int k) {
		this.k = k;
		clusters = new ArrayList<Cluster>();
		// initialize Clusters
		for (int i = 0; i < k; i++) {
			clusters.add(new Cluster(new Histogram(), new Pixel((int) (Math.random() * 255),
					(int) (Math.random() * 255), (int) (Math.random() * 255), 1)));

		}

	}

	public void step(Histogram histogram) {

		for (Cluster c : clusters) {
			// System.out.println(h.getHistogram().getCountOfPixels());
			c.getHistogram().getPixelList().clear();
		}

		// put each pixel in the histogram in the closest cluster
		for (Pixel p : histogram.getPixelList()) {
			double minDistance = Double.MAX_VALUE;
			Cluster closestCluster = null;
			for (Cluster c : clusters) {
				double currentDistance = getEucledianDistance(p, c.getCenter());
				if (currentDistance < minDistance) {
					minDistance = currentDistance;
					closestCluster = c;
				}
			}
			closestCluster.getHistogram().add(p);
		}

		// move of clusters
		for (Cluster c : clusters) {
			if (c.getHistogram().getLength() == 0) {
				continue;
			}

			long meanR = 0;
			long meanG = 0;
			long meanB = 0;
			for (Pixel p : c.getHistogram().getPixelList()) {
				meanR += p.getR() * p.getCount();
				meanG += p.getG() * p.getCount();
				meanB += p.getB() * p.getCount();
			}
			meanR = meanR / c.getHistogram().getCountOfPixels();
			meanG = meanG / c.getHistogram().getCountOfPixels();
			meanB = meanB / c.getHistogram().getCountOfPixels();
			Pixel newMean = new Pixel((int) meanR, (int) meanG, (int) meanB, 1);
			c.setCenter(newMean);
		}

	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public ArrayList<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(ArrayList<Cluster> clusters) {
		this.clusters = clusters;
	}

	private double getEucledianDistance(Pixel p1, Pixel p2) {
		return Math.pow(Math.sqrt(Math.pow(p1.getR() - p2.getR(), 2) + Math.pow(p1.getG() - p2.getG(), 2)
				+ Math.pow(p1.getB() - p2.getB(), 2)), 2);

	}

}
