package kku.object;

import java.io.IOException;

import kku.gameworld.Gameworld;
import kku.graphics.Animation;
import kku.graphics.GameEngine;

public class Enemy extends GameoWorldObjectHandler{

	private float velocityY = 0;
	private float gravity = 150.0f * (float)scale_height;
	float moveX = 0;
	
	private int direction = 1; //1 right -1 left
	
	public Enemy(float posX, float posY) {
		super(posX, posY);
		
		object_width = Gameworld.world.enemy_width;
		object_height = Gameworld.world.enemy_height;
		movementSpeed = (int)(150 * scale_width);
		isSolid = true;
		
		Animation anim = new Animation();
		try {
			anim.images.add(GameEngine.loadTexture("/textureResource/enemy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		animations = new Animation[] {
				anim
		};
	}
	
	public void update (float deltaTime) {
		moveX = 0;
		
		moveX += direction * movementSpeed;
		
		velocityY += gravity * deltaTime;
		
		//COLLISION
		if (doesCollide(posX + moveX * deltaTime, posY)) {
			moveX -= moveX;
			direction = -direction;
		}
				
		if (doesCollide(posX, posY + velocityY * deltaTime)) velocityY -= velocityY;
		//COLLISION
		
		//PLATFORM BORDER DETECTION
		if (!doesCollide(posX + object_width * direction, posY + 100)) {
			direction *= -1;
		}
		//PLATFORM BORDER DETECTION
		
		posX += moveX * deltaTime;
		posY += velocityY * deltaTime;
	}
	
	public void takeDamage(float damage) {
		Gameworld.world.removeSprite(this);
	}
}
