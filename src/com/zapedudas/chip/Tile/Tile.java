package com.zapedudas.chip.Tile;

public abstract class Tile {
	private final int ANIMATION_FRAMES_TOTAL = 10;

	protected Directions direction;
	private int x;
	private int y;

	private int animation_counter = 0;
	
	private boolean animation_movement_active = false;
	private Directions animation_movement_direction = null;
	private int animation_steps = 0;
//	private int animation_movement_offset_x_percent = 0;
//	private int animation_movement_offset_y_percent = 0;
	
	public enum AnimationMode {
		NONE,
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
		this.animation_movement_active = true;
		this.animation_movement_direction = direction;
		this.animation_steps = 1;
		
//		switch (direction) {
//		case UP:
//			animation_movement_offset_y_percent = 90;
//			break;
//		case RIGHT:
//			animation_movement_offset_x_percent = -90;
//			break;
//		case DOWN:
//			animation_movement_offset_y_percent = -90;
//			break;
//		case LEFT:
//			animation_movement_offset_x_percent = 90;
//			break;
//		}
	}
	
	/**
	 * Increase the animation counter by 1, resulting in possible changes to tile's appearances when redrawn
	 */
	public void tickAnimation() {
		animation_counter += 1;
		
		if (getAnimationMode() == AnimationMode.MOVEMENT && animation_movement_active) {
			if (animation_steps >= ANIMATION_FRAMES_TOTAL) {
				animation_movement_active = false;
				animation_movement_direction = null;
//				animation_movement_offset_x_percent = 0;
//				animation_movement_offset_y_percent = 0;
				animation_steps = 0;
			}
			else {
				animation_steps += 1;
//				switch (animation_movement_direction) {
//					case UP:
//						animation_movement_offset_y_percent += 10;
//						break;
//					case RIGHT:
//						animation_movement_offset_x_percent -= 10;
//						break;
//					case DOWN:
//						animation_movement_offset_y_percent -= 10;
//						break;
//					case LEFT:
//						animation_movement_offset_x_percent += 10;
//						break;
//				}
//				
//				animation_counter = animation_counter % ANIMATION_FRAMES_TOTAL;
			}
		}		
	}
	
	protected AnimationMode getAnimationMode() {
		return AnimationMode.INPLACE;
	}
	
	public float getOffsetPercentX() {
		float animationPercent = (float)animation_steps / (float)ANIMATION_FRAMES_TOTAL;
		
		if (animation_movement_direction == Directions.RIGHT) {
			return -1 * (1 - animationPercent);
		}
		else if (animation_movement_direction == Directions.LEFT) {
			return 1 - animationPercent;
		}
		else {
			return 0;
		}
//		return animation_movement_offset_x_percent;
	}
	
	public float getOffsetPercentY() {
		float animationPercent = (float)animation_steps / (float)ANIMATION_FRAMES_TOTAL;
		
		if (animation_movement_direction == Directions.DOWN) {
			return -1 * (1 - animationPercent);
		}
		else if (animation_movement_direction == Directions.UP) {
			return 1 - animationPercent;
		}
		else {
			return 0;
		}
	}

	public abstract String getCurrentImagePath();

	public abstract void sendTileProperty(String property);
}
