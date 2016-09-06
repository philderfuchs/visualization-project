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
	private Set<Cluster> clusters;

	public Kmeans(int k) {
		this.k = k;
		clusters = new HashSet<Cluster>();
		// initialize Clusters
		for (int i = 0; i < k; i++) {
			clusters.add(new Cluster(new Histogram(), new Pixel((int) (Math.random() * 255),
					(int) (Math.random() * 255), (int) (Math.random() * 255), 1)));

		}

	}
	
	
//
//	public void step() {
//
//		ArrayList<HistogramWithMean> resultHistograms = this.kMeans(histogram, this.k);
//
//		HashSet<Pixel> reducedColorPalette = new HashSet();
//
//		for (HistogramWithMean h : resultHistograms) {
//			reducedColorPalette.add(h.getMean());
//		}
//
//		return reducedColorPalette;
//	}
//
//	public ArrayList<HistogramWithMean> kMeans(Histogram histogram, int means) {
//		// initialize random means
//		ArrayList<HistogramWithMean> histogramsWithMeans = new ArrayList<>();
//
//		for (int i = 0; i < means; i++) {
//			Pixel p = histogram.getHistogram().get((int) (Math.random() * (histogram.getHistogram().size() - 1)));
//			histogramsWithMeans.add(new HistogramWithMean(new Pixel(p.getR(), p.getG(), p.getB(), 1), new Histogram()));
//		}
//
//		boolean meanChanged = true;
//		while (meanChanged) {
//
//			for (HistogramWithMean h : histogramsWithMeans) {
//				// System.out.println(h.getHistogram().getCountOfPixels());
//				h.getHistogram().getHistogram().clear();
//			}
//
//			for (Pixel p : histogram.getHistogram()) {
//
//				// first clear all old histograms
//
//				double minDistance = Integer.MAX_VALUE;
//				int indexOfMeanWithShortestDistance = 0;
//				int currentIndexOfMean = 0;
//				for (HistogramWithMean h : histogramsWithMeans) {
//					double currentDistance = getEucledianDistance(p, h.getMean());
//					if (currentDistance < minDistance) {
//						minDistance = currentDistance;
//						indexOfMeanWithShortestDistance = currentIndexOfMean;
//					}
//					currentIndexOfMean++;
//				}
//				histogramsWithMeans.get(indexOfMeanWithShortestDistance).getHistogram().add(p);
//			}
//
//			// move means
//			meanChanged = false;
//
//			for (HistogramWithMean h : histogramsWithMeans) {
//				if (h.getHistogram().getLength() == 0) {
//					// h.setMean(new Pixel((int) (Math.random() * 255),
//					// (int) (Math.random() * 255),
//					// (int) (Math.random() * 255), 1));
//					continue;
//				}
//
//				long meanR = 0;
//				long meanG = 0;
//				long meanB = 0;
//				for (Pixel p : h.getHistogram().getHistogram()) {
//					meanR += p.getR() * p.getCount();
//					meanG += p.getG() * p.getCount();
//					meanB += p.getB() * p.getCount();
//				}
//				meanR = meanR / h.getHistogram().getCountOfPixels();
//				meanG = meanG / h.getHistogram().getCountOfPixels();
//				meanB = meanB / h.getHistogram().getCountOfPixels();
//				Pixel newMean = new Pixel((int) meanR, (int) meanG, (int) meanB, 1);
//
//				if (!h.getMean().containsSameColorsAs(newMean)) {
//					h.setMean(newMean);
//					meanChanged = true;
//				}
//
//			}
//
//		}
//		return histogramsWithMeans;
//	}

	public int getK() {
		return k;
	}


	public void setK(int k) {
		this.k = k;
	}


	public Set<Cluster> getClusters() {
		return clusters;
	}


	public void setClusters(Set<Cluster> clusters) {
		this.clusters = clusters;
	}


	private double getEucledianDistance(Pixel p1, Pixel p2) {
		return Math.pow(Math.sqrt(Math.pow(p1.getR() - p2.getR(), 2) + Math.pow(p1.getG() - p2.getG(), 2)
				+ Math.pow(p1.getB() - p2.getB(), 2)), 2);

	}

}
