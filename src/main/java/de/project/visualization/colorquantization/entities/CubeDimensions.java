package de.project.visualization.colorquantization.entities;

public class CubeDimensions {

	private int rMin;
	private int rMax;

	private int gMin;
	private int gMax;

	private int bMin;
	private int bMax;

	public CubeDimensions(int rMin, int rMax, int gMin, int gMax, int bMin, int bMax) {
		super();
		this.rMin = rMin;
		this.rMax = rMax;
		this.gMin = gMin;
		this.gMax = gMax;
		this.bMin = bMin;
		this.bMax = bMax;
	}

	public Pixel getCenter() {
		return new Pixel((getrMin() + getrMax() - getrMin()) / 2, (getgMin() + getgMax() - getgMin()) / 2,
				(getbMin() + getbMax() - getbMin()) / 2, 1);
	}

	public int getrMin() {
		return rMin;
	}

	public void setrMin(int rMin) {
		this.rMin = rMin;
	}

	public int getrMax() {
		return rMax;
	}

	public void setrMax(int rMax) {
		this.rMax = rMax;
	}

	public int getgMin() {
		return gMin;
	}

	public void setgMin(int gMin) {
		this.gMin = gMin;
	}

	public int getgMax() {
		return gMax;
	}

	public void setgMax(int gMax) {
		this.gMax = gMax;
	}

	public int getbMin() {
		return bMin;
	}

	public void setbMin(int bMin) {
		this.bMin = bMin;
	}

	public int getbMax() {
		return bMax;
	}

	public void setbMax(int bMax) {
		this.bMax = bMax;
	}

}
