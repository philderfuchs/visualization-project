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
	
	public CubeDimensions(Histogram histogram) {
		rMin = 256;
    	rMax = 0;

    	gMin = 256;
    	gMax = 0;
    	
    	bMin = 256;
    	bMax = 0;
    	
		
    	for(Pixel p : histogram.getPixelList()) {
    		if(p.getR() < rMin) {
    			rMin = p.getR();
    		}
    		
    		if(p.getR() > rMax) {
    			rMax = p.getR();
    		}
    		
    		if(p.getG() < gMin) {
    			gMin = p.getG();
    		}
    		
    		if(p.getG() > gMax) {
    			gMax = p.getG();
    		}
    		
    		if(p.getB() < bMin) {
    			bMin = p.getB();
    		}

    		if(p.getB() > bMax) {
    			bMax = p.getB();
    		}    		
    	}
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
