package com.zapedudas.chip.Tile;

public abstract class Tile {
	protected Directions direction;
	private int x;
	private int y;
	
	public enum Directions {
		NONE,
		UP,
		DOWN, 
		LEFT,
		RIGHT
	};
	
	public Tile(int x, int y, Directions direction) {
		this.setX(x);
		this.setY(y);
		this.direction = direction;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public abstract String getCurrentImagePath();

}
