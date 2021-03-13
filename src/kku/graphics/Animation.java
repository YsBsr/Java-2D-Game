package kku.graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

	public ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	public int currentImage = 0;
	
	public boolean isGround = false;
	
	public int fps = 2;
	
	public void playAnimation() {
		if (System.nanoTime() > (1000000000 / fps)) { //merminin yarým saniyede bir renk deiþtirmerisini saðlýyoruz.
			currentImage++;
			
			if (currentImage >= images.size()) {
				currentImage = 0;
			}
		}
	}
	
	public BufferedImage getImage() {
		if (images.size() > currentImage) {
			return images.get(currentImage);
		}
		return null;
	}
	
}
