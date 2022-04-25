package com.github.npaetzel.MineSearcher;

public abstract class Position {
	private int x;
	private int y;
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean setX(int x) {
		if(x > 0) {
			this.x = x;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean setY(int y) {
		if(y > 0) {
			this.y = y;
			return true;
		} else {
			return false;
		}
		
	}
	
	public void setPosition(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
}
