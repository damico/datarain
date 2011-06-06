package com.google.code.datarain.tests;

import org.junit.Test;

import com.google.code.datarain.components.Controller;
import com.google.code.datarain.dataobjets.Input;


public class DataRainPrototype {

	//Params 334,90,10,684-90 (x,y,w,h)
	
	
	
	private static final int X = 334;
	private static final int Y = 90;
	
	private static final int YE = 684;
	
	private static final int W = 10;
	
	private static final int H = YE-Y; 
	
	private static final int YT = 2000;
	private static final String UNIT = "ml";


	@Test
	public void test() {

		Input inputObj = new Input();
		inputObj .setH(H);
		inputObj.setU(UNIT);
		inputObj.setW(W);
		
		inputObj.setX(X);
		inputObj.setY(Y);
		inputObj.setYE(YE);
		inputObj.setYT(YT);
		inputObj.setInputFilePath("./res/Foto-0094.jpg");
		
		double ret = Controller.getInstance().process(inputObj);
		
		System.out.println("Result: "+ret+" "+inputObj.getU());

	}
}
