package de.project.visualization.colorquantization.entities;

import javax.media.j3d.TransformGroup;

public class VisualCluster extends Cluster {
	
	private TransformGroup transformGroup;
	
	public VisualCluster(Histogram histo, Pixel center, TransformGroup transformGroup) {
		super(histo, center);
		this.transformGroup = transformGroup;
	}
	
	public VisualCluster(Cluster c, TransformGroup transformGroup) {
		super(c.getHistogram(), c.getCenter());
		this.transformGroup = transformGroup;
	}

	public TransformGroup getTransformGroup() {
		return transformGroup;
	}

	public void setTransformGroup(TransformGroup transformGroup) {
		this.transformGroup = transformGroup;
	}

}
