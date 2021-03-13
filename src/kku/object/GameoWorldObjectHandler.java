package kku.object;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import kku.gameworld.Gameworld;
import kku.graphics.Animation;
import kku.graphics.GameEngine;

public class GameoWorldObjectHandler extends GameEngine{
	
	public float posX = 0;
	public float posY = 0;
	
	public int realX = 0;
	public int realY = 0;
	
	public Animation[] animations;
	public int currentAnimation = 0;
	
	public float object_width = 0;
	public float object_height = 0;
	
	public boolean isSolid = true;
	public boolean itsEnemy = false;
	public static int beamCount = -1;
	
	public static int player_direction = 1;
	protected float movementSpeed = 200.0f;
	
	BufferedImage image = null;
	
	float left = 0;
	float right = 0;
	float up = 0;
	float down = 0;
	
	float otherleft = 0;
	float otherright = 0;
	float otherup = 0;
	float otherdown = 0;
	
	public static Enemy shooted = null;
	
	public GameoWorldObjectHandler(float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void update(float deltaTime) {
		
	}
	
	
	public void render(Graphics g) {
		if (animations == null || currentAnimation >= animations.length) return;
				
		animations[currentAnimation].playAnimation();
		image = animations[currentAnimation].getImage();
				
		if (image == null) return;
				
		realX = (int) posX - (int)object_width / 2;
		realY = (int) posY - (int)object_height / 2;
			
		realX = realX - (int)GameEngine.camX + GameEngine.arbitrary_width / 2;
		realY = realY - (int)GameEngine.camY + GameEngine.arbitrary_height / 2;
		
		if (animations[currentAnimation].isGround) {
		g.drawImage(image, (int)(posX - object_width / 2) - (int) GameEngine.camX + GameEngine.arbitrary_width / 2, 
			(int)(posY - object_height / 2) - (int) GameEngine.camY + GameEngine.arbitrary_height / 2,
			(int)object_width, (int)object_height, null);
		} else {
			g.drawImage(image, realX, realY, (int)object_width, (int)object_height, null);
		}
	}
	
	protected boolean doesCollide(float x, float y) {
		left = x - object_width / 2;
		right = x + object_width / 2;
		up = y - object_height / 2;
		down = y + object_height / 2;
		
		for (GameoWorldObjectHandler sprite : Gameworld.world.sprites) {
			
			if (sprite == this || sprite.isSolid == false) continue;
			
			otherleft = sprite.posX - sprite.object_width / 2;
			otherright = sprite.posX + sprite.object_width / 2;
			otherup = sprite.posY - sprite.object_height / 2;
			otherdown = sprite.posY + sprite.object_height / 2;
			
			if (left < otherright && right > otherleft && down > otherup && up < otherdown) {
				if (sprite instanceof Enemy) {
					 itsEnemy = true;
					 shooted = (Enemy) sprite;
				} else itsEnemy = false;
				return true;
			}
		}
		return false;
	}
}
