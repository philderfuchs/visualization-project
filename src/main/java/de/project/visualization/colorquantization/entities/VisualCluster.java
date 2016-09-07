package de.project.visualization.colorquantization.entities;

import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.Primitive;

public class VisualCluster extends Cluster {
	
	private Primitive primitive;
	private Coordinates coordinates;
	
	public VisualCluster(Histogram histo, Pixel center, Primitive primitive, Coordinates coordinates) {
		super(histo, center);
		this.primitive = primitive;
		this.coordinates = coordinates;
	}
	
	public VisualCluster(Cluster c, Primitive primitive, Coordinates coordinates) {
		super(c.getHistogram(), c.getCenter());
		this.primitive = primitive;
		this.coordinates = coordinates;
	}

	public Primitive getPrimitive() {
		return primitive;
	}

	public void setPrimitive(Primitive primitive) {
		this.primitive = primitive;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

}
