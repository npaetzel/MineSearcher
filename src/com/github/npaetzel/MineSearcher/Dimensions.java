package com.github.npaetzel.MineSearcher;

public abstract class Dimensions {
	private int length;
	private int height;
	
	
	public int getLength() {
		return length;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean setLength(int length) {
		if(length > 0) {
			this.length = length;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setHeight(int height) {
		if(height > 0) {
			this.height = height;
			return true;
		} else {
			return false;
		}
		
	}
	
	public void setDimensions(int length, int height) {
		this.setLength(length);
		this.setHeight(height);
	}
	
}
