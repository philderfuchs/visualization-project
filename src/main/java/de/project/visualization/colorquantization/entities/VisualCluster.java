package de.project.visualization.colorquantization.entities;

import java.util.ArrayList;

import javax.media.j3d.TransformGroup;

import com.sun.j3d.utils.geometry.Primitive;

public class VisualCluster extends Cluster {
	
	private ArrayList<Primitive> primitives;
	
	public VisualCluster(Histogram histo, Pixel center, ArrayList<Primitive> primitives, Coordinates coordinates) {
		super(histo, center);
		this.primitives = primitives;
	}
	
	public VisualCluster(Cluster c, ArrayList<Primitive> primitives) {
		super(c.getHistogram(), c.getCenter());
		this.primitives = primitives;
	}

	public ArrayList<Primitive> getPrimitives() {
		return primitives;
	}

	public void setPrimitives(ArrayList<Primitive> primitives) {
		this.primitives = primitives;
	}

}
