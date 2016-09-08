package de.project.visualization.colorquantization.entities;

public class Cube extends Cluster {

	private CubeDimensions cubeDimensions;

	public Cube(Histogram histo, CubeDimensions cubeDimensions) {
		super(histo, cubeDimensions.getCenter());
		this.cubeDimensions = cubeDimensions;
	}

	public CubeDimensions getCubeDimensions() {
		return cubeDimensions;
	}

	public void setCubeDimensions(CubeDimensions cubeDimensions) {
		this.cubeDimensions = cubeDimensions;
	}

}
