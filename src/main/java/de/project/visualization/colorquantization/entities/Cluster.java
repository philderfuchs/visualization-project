package de.project.visualization.colorquantization.entities;

public class Cluster {

	private Histogram histo;
	private Pixel center;
	
	public Cluster(Histogram histo, Pixel center) {
		super();
		this.histo = histo;
		this.center = center;
	}
	
	public Histogram getHisto() {
		return histo;
	}
	public void setHisto(Histogram histo) {
		this.histo = histo;
	}
	public Pixel getCenter() {
		return center;
	}
	public void setCenter(Pixel center) {
		this.center = center;
	}
	
	
	
}
