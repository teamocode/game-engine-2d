package com.joss.asteroiddodger2d;

// Empty node that is given meaning when the game loop runs

public class GameObject {
	
	/* Knowing the bounds of the 'physical' game-object helped for writing
	 * collision detection along with game logic in a more a concise manner */
	public float leftBound, rightBound, topBound, bottomBound;
	
	private float x, y, width, height;
	
	private float[] color;
	
	/* Created this boolean as a way to render both triangles and quadrilaterals 
	in a similar fashion */
	private boolean isTri;
	
	GameObject(float x, float y, float width, float height, float[] color, boolean isTri) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		// If the color float array is long enough than give it to the class instance
		if(color.length == 4) {
			this.color = color;
		} else {
			this.color = null;
			System.out.println("GameObject | GameObject | color float array too long");
		}
		
		this.isTri = isTri;
		
		leftBound = x - width / 2f;
		rightBound = x + width / 2f;
		topBound = y + height / 2f;
		bottomBound = y - height /2f;
	}
	
	public float[] getRenderInfo() {
		// Return different float arrays depending on shape
		if(isTri) {
			// Return triangle data
			return new float[] 
					{leftBound, bottomBound,
					x, topBound,
					rightBound, bottomBound};
		} else {
			// Return square data
			return new float[] {
					leftBound, topBound,
					rightBound, topBound,
					rightBound, bottomBound,
					leftBound, bottomBound};
		}
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float[] getColor() {
		return color;
	}
	public boolean getIsTri() {
		return isTri;
	}
	
	public void addToX(float value) {
		/* In all game-object transformation methods, the bounds are
		 * updated to ensure the rendering and collision detection are accurate */
		x += value;
		leftBound = x - width / 2f;
		rightBound = x + width / 2f;
	}
	
	public void addToY(float value) {
		y += value;
		topBound = y + height / 2f;
		bottomBound = y - height /2f;
	}
	
	public void setX(float x) {
		this.x = x;
		leftBound = x - width / 2f;
		rightBound = x + width / 2f;
	}
	
	public void setY(float y) {
		this.y = y;
		topBound = y + height / 2f;
		bottomBound = y - height /2f;
	}
	
	public void setWidth(float width) {
		this.width = width;
		leftBound = x - width / 2f;
		rightBound = x + width / 2f;
	}
	
	public void setHeight(float height) {
		this.height = height;
		topBound = y + height / 2f;
		bottomBound = y - height /2f;
	}
	
	public void setColor(float[] color) {
		this.color = color;
	}
	
	public void setIsTri(boolean isTri) {
		this.isTri = isTri;
	}
	
}
