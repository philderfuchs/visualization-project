package de.project.visualization.colorquantization.entities;

import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.Primitive;

public class VisualCluster extends Cluster {
	
	private Primitive primitive;
	
	public VisualCluster(Histogram histo, Pixel center, Primitive primitive, Coordinates coordinates) {
		super(histo, center);
		this.primitive = primitive;
	}
	
	public VisualCluster(Cluster c, Primitive primitive) {
		super(c.getHistogram(), c.getCenter());
		this.primitive = primitive;
	}

	public Primitive getPrimitive() {
		return primitive;
	}

	public void setPrimitive(Primitive primitive) {
		this.primitive = primitive;
	}

}
