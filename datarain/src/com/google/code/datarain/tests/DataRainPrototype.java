package com.google.code.datarain.tests;

import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jhlabs.image.EdgeFilter;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.ThresholdFilter;

public class DataRainPrototype {

	private static final int HEIGHT = 602; 
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		//create the detector
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("/tmp/real-test3.jpg"));
		} catch (IOException e) {
		}
		
		GrayscaleFilter gf = new GrayscaleFilter();
		gf.filter(img, null);
		buffImg2File(img, "/tmp/init.png");
		
		ThresholdFilter th = new ThresholdFilter();
		th.setLowerThreshold(100);
		BufferedImage res = th.filter(img, null);
		
		buffImg2File(res, "/tmp/a.png");
		
		EdgeFilter ef = new EdgeFilter();
		ef.filter(res, null);
		
		buffImg2File(res, "/tmp/b.png");
		
		CannyEdgeDetector detector = new CannyEdgeDetector();

		//adjust its parameters as desired
		detector.setLowThreshold(0.5f);
		detector.setHighThreshold(1f);

		//apply it to an image
		detector.setSourceImage(res);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		
		
		
		BufferedImage finalImg = edges.getSubimage(60, 100, 100, 602);
		
		buffImg2File(finalImg, "/tmp/c.png");
		getPixelColor(finalImg);

	}

	private static void buffImg2File(BufferedImage res, String filePath) {
		try {
		    
		    File outputfile = new File(filePath);
		    ImageIO.write(res, "png", outputfile);
		} catch (IOException e) {
		    
		}
	}
	
	private static void getPixelColor(BufferedImage image){
		
		int r = 0;
		int g = 0;
		int b = 0;
		
		int linePos = 0;
		
		double ret = 0;
		
		for(int i=0; i<(HEIGHT-1); i++){
			int c = image.getRGB(0,i);
			  r = (c & 0x00ff0000) >> 16;
			  g = (c & 0x0000ff00) >> 8;
			  b = c & 0x000000ff;
			  linePos = i;
			  //System.out.println("Y="+i+"\tR="+r+" G="+g+" B="+b);  
			
			  if(r==255)	break;
			
			
			 ret = (((HEIGHT - linePos)) * 100 / HEIGHT)*10;
			
			
		}
		
		
		System.out.println("Result: = "+ret+" mm");
	}

}
