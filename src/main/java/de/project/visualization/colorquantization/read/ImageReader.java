package de.project.visualization.colorquantization.read;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import de.project.visualization.colorquantization.entities.*;


public class ImageReader {

	private BufferedImage img;

	public ImageReader(String fileName) throws IOException {
		img = ImageIO.read(new File(fileName));
	}
	
//	public Color[] getPixelData() {
//		int imgHeight = img.getHeight();
//		int imgWidth = img.getWidth();
//		
//		Color[] pixelData = new Color[imgHeight * imgWidth];
//		int arrayCounter = 0;
//		
//		for(int y = 0; y < imgHeight; y++) {
//			for(int x = 0; x < imgWidth; x++) {
//				pixelData[arrayCounter++] = new Color(img.getRGB(x, y));
//			}
//		}
//		
//		return pixelData;
//		
//	}
	
	public Histogram getHistogram(){
		int imgHeight = img.getHeight();
		int imgWidth = img.getWidth();
		
		HashMap<Integer, Integer> pixelMap = new HashMap();
		Integer currentPixelColor;
		
		for(int y = 0; y < imgHeight; y++) {
			for(int x = 0; x < imgWidth; x++) {
				currentPixelColor = img.getRGB(x, y);
				if (pixelMap.containsKey(currentPixelColor)){
					pixelMap.put(currentPixelColor, pixelMap.get(currentPixelColor) + 1);
				} else {
					pixelMap.put(currentPixelColor, 1);
				}
			}
		}
		
		ArrayList<Pixel> pixelList = new ArrayList<Pixel>();
		for (Integer i : pixelMap.keySet()) {
			Color c = new Color(i);
			pixelList.add(new Pixel(c.getRed(), c.getGreen(), c.getBlue(), pixelMap.get(i)));
		}
		Histogram histogram = new Histogram (pixelList);
				
		return histogram;
	}
	
}
