package de.project.visualization.colorquantization.read;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashMap;

import de.project.visualization.colorquantization.entities.Pixel;
import de.project.visualization.colorquantization.entities.VisualCluster;

public class ImageQuantizer {

	public Image quantize(BufferedImage img, ArrayList<VisualCluster> vClusters) {
		System.out.println("quantizing image");

		int width = img.getWidth();
		int height = img.getHeight();
		
        BufferedImage quantizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		int currentPixel;
		for(int y = 0; y < height; y++) {
			loop: for(int x = 0; x < width; x++) {
				currentPixel = img.getRGB(x, y);
				for(VisualCluster c : vClusters) {
//					for(Pixel p : c.getHistogram().getPixelList()) {
//						if(p.getRgb() == currentPixel) {
//							quantizedImage.setRGB(x, y, c.getCenter().getRgb());
//							continue loop;
//						}
//					}
					if(c.getHistogram().contains(currentPixel)) {
						quantizedImage.setRGB(x, y, c.getCenter().getRgb());
						continue loop;
					}
				}

			}
		}
		return quantizedImage;
	}
	
}
