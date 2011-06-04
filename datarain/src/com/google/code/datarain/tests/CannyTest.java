package com.google.code.datarain.tests;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.code.datarain.utils.Constants;
import com.jhlabs.image.ThresholdFilter;

public class CannyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		
		//create the detector
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(Constants.IMAGE_SOURCE + Constants.FILE_NAME_SOURCE));
		} catch (IOException e) {
		}
		

		
		ThresholdFilter th = new ThresholdFilter();
		th.setLowerThreshold(100);
		BufferedImage res = th.filter(img, null);
		
		buffImg2File(res, Constants.IMAGE_INTERMEDIATE_TARGET + Constants.FILE_NAME_INTERMEDIATE);
		
		
		CannyEdgeDetector detector = new CannyEdgeDetector();

		//adjust its parameters as desired
		detector.setLowThreshold(0.5f);
		detector.setHighThreshold(1f);

		//apply it to an image
		detector.setSourceImage(res);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		
		buffImg2File(edges, Constants.IMAGE_FINAL_TARGET + Constants.FILE_NAME_TARGET);

	}

	private static void buffImg2File(BufferedImage res, String filePath) {
		try {
		    
		    File outputfile = new File(filePath);
		    ImageIO.write(res, "png", outputfile);
		} catch (IOException e) {
		    
		}
	}

}
