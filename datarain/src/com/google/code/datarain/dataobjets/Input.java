package com.google.code.datarain.dataobjets;

public class Input {
	
	private String inputFilePath;
	private int X;
	private int Y;
	private int YE;
	private int W;
	private String U;
	private int H;
	private int YT;
	public String getInputFilePath() {
		return inputFilePath;
	}
	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public int getYE() {
		return YE;
	}
	public void setYE(int yE) {
		YE = yE;
	}
	public int getW() {
		return W;
	}
	public void setW(int w) {
		W = w;
	}
	public String getU() {
		return U;
	}
	public void setU(String u) {
		U = u;
	}
	public int getH() {
		return H;
	}
	public void setH(int h) {
		H = h;
	}
	public int getYT() {
		return YT;
	}
	public void setYT(int yT) {
		YT = yT;
	}
	public Input(String inputFilePath, int x, int y, int yE, int w, String u,
			int h, int yT) {
		super();
		this.inputFilePath = inputFilePath;
		X = x;
		Y = y;
		YE = yE;
		W = w;
		U = u;
		H = h;
		YT = yT;
	}
	public Input() {
		super();
	}
	
	

}
