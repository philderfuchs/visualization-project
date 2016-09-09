package de.project.visualization.colorquantization.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.project.visualization.colorquantization.entities.Channels;
import de.project.visualization.colorquantization.entities.Cluster;
import de.project.visualization.colorquantization.entities.Cube;
import de.project.visualization.colorquantization.entities.Histogram;
import de.project.visualization.colorquantization.entities.Pixel;

public class MedianCut {

	private ArrayList<Cube> cubes;

	public MedianCut() {
		cubes = new ArrayList<Cube>();
	}

	public ArrayList<Cube> step(Histogram histogram) {

		if (cubes.size() == 0) {
			Cube initialCube = new Cube(histogram);
			cubes.add(initialCube);
			return cubes;
		} else {

			Cube cube = this.getBiggestCube(cubes);
			cubes.remove(cube);

			sortHistogram(cube, cube.getLongestDistance());

			int totalCount = cube.getHistogram().getCountOfPixels();

			Histogram histogramOfChildCube1 = new Histogram();
			Histogram histogramOfChildCube2 = new Histogram();

			int currentCount = 0;
			for (Pixel p : cube.getHistogram().getPixelList()) {
				currentCount += p.getCount();
				if (currentCount <= totalCount / 2) {
					histogramOfChildCube1.add(p);
				} else {
					histogramOfChildCube2.add(p);
				}
			}

			Cube childCube1 = new Cube(histogramOfChildCube1);
			Cube childCube2 = new Cube(histogramOfChildCube2);

			cubes.add(childCube1);
			cubes.add(childCube2);
		}
		return cubes;
	}

	private void sortHistogram(Cube cube, Channels colorWithLongestDistance) {
		switch (colorWithLongestDistance) {
		case R:
			cube.getHistogram().sort(Channels.R);
			break;
		case G:
			cube.getHistogram().sort(Channels.G);
			break;
		case B:
			cube.getHistogram().sort(Channels.B);
			break;
		default:
			break;
		}
	}

	private Cube getBiggestCube(ArrayList<Cube> cubeList) {
		Collections.sort(cubeList);
		return cubeList.get(0);
	}

	public ArrayList<Cube> getCubes() {
		return cubes;
	}

	public void setCubes(ArrayList<Cube> cubes) {
		this.cubes = cubes;
	}

}
