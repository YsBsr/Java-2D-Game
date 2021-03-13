package kku.object;

import java.io.IOException;

import kku.graphics.Animation;
import kku.graphics.GameEngine;

public class Platform extends GameoWorldObjectHandler{

	public Platform(float posX, float posY, float width, float height) {
		super(posX, posY);
		
		object_width = width;
		object_height = height;
		isSolid = true;
		
		Animation anim = new Animation();
		try {
			anim.images.add(GameEngine.loadTexture("/textureResource/ground2.png"));
			anim.isGround = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		animations = new Animation[] {
				anim
		};
	}
}
