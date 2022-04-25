package com.github.npaetzel.MineSearcher;

public enum Difficulty {
	EASY	( 9,  9, 10),
	NORMAL	(16, 16, 40),
	HARD	(30, 16, 99);
	
	
	private final int length;
	private final int height;
	private final int bombs;
	
	public int getLength() {
		return length;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getBombs() {
		return bombs;
	}
	
	Difficulty(int length, int height, int bombs) {
		this.length = length;
		this.height = height;
		this.bombs = bombs;
	}
	
	
}
