package com.zapedudas.chip;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class ImageCache {
	private PApplet parentApplet;
	private HashMap<String, PImage> imageStore;
	
	public ImageCache(PApplet parentApplet) {
		this.parentApplet = parentApplet;
		this.imageStore = new HashMap<String, PImage>();
	}
	
	public PImage getPImage(String imageName) {
		PImage currentImage = imageStore.get(imageName);
		
		if (currentImage == null) {
			currentImage = this.parentApplet.loadImage(imageName);
		}
		
		return currentImage;
	}
}
