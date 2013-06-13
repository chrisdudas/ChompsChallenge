package com.zapedudas.chip.Tile;

public abstract class Tile {
	private final int ANIMATION_FRAMES_TOTAL = 10;

	protected Directions direction;
	private int x;
	private int y;

	private Directions animation_movement_direction = null;
	private int animation_steps = 0;
	
	public enum AnimationMode {
		MOVEMENT,
		INPLACE
	}
	
	public enum Directions {
		UP,
		DOWN, 
		LEFT,
		RIGHT
	};
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
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
	
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Directions getDirection() {
		return direction;
	}

	public void setDirection(Directions direction) {
		this.direction = direction;
	}
	
	protected void beginAnimation(Directions direction) {
		this.animation_movement_direction = direction;
		this.animation_steps = 1;
	}
	
	/**
	 * Increase the animation counter by 1, resulting in possible changes to tile's appearances when redrawn
	 */
	public void tickAnimation() {
		if (isAnimatingMovement()) {
			if (animation_steps >= ANIMATION_FRAMES_TOTAL) {
				animation_movement_direction = null;
				animation_steps = 0;
			}
			else {
				animation_steps += 1;
			}
		}		
	}
	
	protected AnimationMode getAnimationMode() {
		return null;
	}
	
	public boolean isAnimatingMovement() {
		return getAnimationMode() == AnimationMode.MOVEMENT && animation_movement_direction != null;
	}
	
	protected float getAnimationPercent() {
		return (float)animation_steps / (float)ANIMATION_FRAMES_TOTAL;
	}
	
	public float getOffsetPercentX() {
		if (animation_movement_direction == null) return 0;
		
		switch (animation_movement_direction) {
			case RIGHT:
				return -1 * (1 - getAnimationPercent());
			case LEFT:
				return 1 - getAnimationPercent();
			default:
				return 0;
		}
	}
	
	public float getOffsetPercentY() {
		if (animation_movement_direction == null) return 0;
		
		switch (animation_movement_direction) {
			case DOWN:
				return -1 * (1 - getAnimationPercent());
			case UP:
				return 1 - getAnimationPercent();
			default:
					return 0;
		}
	}
	
	public Directions getMovementAnimationDirection() {
		return isAnimatingMovement() ? animation_movement_direction : null;
	}

	public abstract String getCurrentImagePath();

	public abstract void sendTileProperty(String property);
}
