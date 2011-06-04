package com.google.code.datarain.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jhlabs.image.ThresholdFilter;

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
		

		
		ThresholdFilter th = new ThresholdFilter();
		th.setLowerThreshold(100);
		BufferedImage res = th.filter(img, null);
		
		buffImg2File(res, "/tmp/a.png");
		
		
		CannyEdgeDetector detector = new CannyEdgeDetector();

		//adjust its parameters as desired
		detector.setLowThreshold(0.5f);
		detector.setHighThreshold(1f);

		//apply it to an image
		detector.setSourceImage(res);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		
		buffImg2File(edges, "/tmp/b.png");

	}

	private static void buffImg2File(BufferedImage res, String filePath) {
		try {
		    
		    File outputfile = new File(filePath);
		    ImageIO.write(res, "png", outputfile);
		} catch (IOException e) {
		    
		}
	}

}
