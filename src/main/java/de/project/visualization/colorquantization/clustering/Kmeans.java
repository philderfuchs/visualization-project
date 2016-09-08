package de.project.visualization.colorquantization.clustering;

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
	}

	public ArrayList<Cluster> step(Histogram histogram) {

		if (clusters.size() == 0) {
			// clusters not yet initialized
			// initialize random clusters
			System.out.println("initializ clusters");
			for (int i = 0; i < k; i++) {
				clusters.add(new Cluster(new Histogram(), new Pixel((int) (Math.random() * 255),
						(int) (Math.random() * 255), (int) (Math.random() * 255), 1)));
			}
		} else {
			// clusters already initialized
			// move of clusters
			System.out.println("move clusters");
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
		
		for (Cluster c : clusters) {
			c.getHistogram().getPixelList().clear();
		}

		System.out.println("calculate closest values");
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
		
		return clusters;

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
