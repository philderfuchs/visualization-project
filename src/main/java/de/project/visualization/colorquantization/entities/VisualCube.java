package de.project.visualization.colorquantization.entities;

import com.sun.j3d.utils.geometry.Primitive;

public class VisualCube extends Cube {

	private Primitive primitive;

	public VisualCube(Cube cube, Primitive primitive) {
		super(cube.getHistogram(), cube.getCubeDimensions());
		this.primitive = primitive;
	}

	public Primitive getPrimitive() {
		return primitive;
	}

	public void setPrimitive(Primitive primitive) {
		this.primitive = primitive;
	}

}
