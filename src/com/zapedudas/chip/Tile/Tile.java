package com.zapedudas.chip.Tile;

public abstract class Tile {
	protected Directions direction;
	private int x;
	private int y;
	
	public enum Directions {
		UP,
		DOWN, 
		LEFT,
		RIGHT
	};
	
	public Tile(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}
	
	public Directions getDirection() {
		return direction;
	}

	protected void setDirection(Directions direction) {
		this.direction = direction;
	}

	public abstract String getCurrentImagePath();

	public abstract String parseTileProperty(String property);
}
