package com.google.code.datarain.components;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.code.datarain.dataobjets.Input;
import com.google.code.datarain.filters.CannyEdgeDetector;
import com.google.code.datarain.filters.EdgeFilter;
import com.google.code.datarain.filters.ThresholdFilter;

public class Controller {

	private static Controller INSTANCE = null;
	public static Controller getInstance(){
		if(INSTANCE == null) INSTANCE = new Controller();
		return INSTANCE;
	}

	public double process(Input input){

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(input.getInputFilePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}


		int imgType = img.getType();
		if(imgType != BufferedImage.TYPE_BYTE_GRAY) img = makeCompatible(img);



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



		BufferedImage finalImg = edges.getSubimage(input.getX(),input.getY(), input.getW() , input.getH());

		buffImg2File(finalImg, "/tmp/c.png");
		double ret = getPixelColor(finalImg, input.getH(), input.getYT(), input.getU());
		return ret;

	}

	private  void buffImg2File(BufferedImage res, String filePath) {
		try {

			File outputfile = new File(filePath);
			ImageIO.write(res, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private double getPixelColor(BufferedImage image, int H, int YT, String U){

		int r = 0;
		int g = 0;
		int b = 0;

		int linePos = 0;

		double ret = 0;

		for(int i=0; i<(H-1); i++){
			int c = image.getRGB(0,i);
			r = (c & 0x00ff0000) >> 16;
		g = (c & 0x0000ff00) >> 8;
		b = c & 0x000000ff;
		linePos = i;
		//System.out.println("Y="+i+"\tR="+r+" G="+g+" B="+b);  

		if(r==255)	break;


		ret = (((H - linePos)) * YT / H);


		}


		System.out.println("Result = "+ret+" "+U);
		System.out.println("linePos = "+linePos+" YT: "+YT+" H: "+H);
		
		return ret;

	}



	private BufferedImage makeCompatible(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();

		BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g = result.createGraphics();
		g.drawRenderedImage(image, new AffineTransform()); 
		g.dispose();

		return result;
	}

}
