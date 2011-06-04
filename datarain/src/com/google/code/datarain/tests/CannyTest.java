package com.google.code.datarain.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CannyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//create the detector
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("/home/jdamico/Downloads/test1.jpg"));
		} catch (IOException e) {
		}
		
		CannyEdgeDetector detector = new CannyEdgeDetector();

		//adjust its parameters as desired
		detector.setLowThreshold(0.5f);
		detector.setHighThreshold(1f);

		//apply it to an image
		detector.setSourceImage(img);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		
		try {
		    
		    File outputfile = new File("/tmp/saved.png");
		    ImageIO.write(edges, "png", outputfile);
		} catch (IOException e) {
		    
		}

	}

}
