package de.project.visualization.colorquantization.entities;

import java.awt.Color;

public class Pixel {
	private int r;
	private int g;
	private int b;
	private int count;
	private int rgb;

	public Pixel(int r, int g, int b, int count) {

		this.r = r;
		this.g = g;
		this.b = b;
		this.count = count;
		this.rgb = ((255&0x0ff)<<24)|((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);

//		rgb = r;
//		rgb = (rgb << 8) + g;
//		rgb = (rgb << 8) + b;
	}

	public int get(Channels c) {
		switch (c) {
		case R:
			return this.r;
		case G:
			return this.g;
		case B:
			return this.b;
		default:
			break;
		}
		return 0;
	}

	public boolean sameAs(Pixel p) {
		return p.getR() == this.r && p.getG() == this.g && p.getB() == this.b;
	}

//	public boolean sameAs(int rgb) {
//		int alpha = (rgb >> 24) & 0xFF;
//		int red =   (rgb >> 16) & 0xFF;
//		int green = (rgb >>  8) & 0xFF;
//		int blue =  (rgb      ) & 0xFF;
//		return red == this.r && green == this.g && blue == this.b;
//	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getRgb() {
		return rgb;
	}

	public void setRgb(int rgb) {
		this.rgb = rgb;
	}

}
