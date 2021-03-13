package kku.object;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import kku.gameworld.Gameworld;
import kku.graphics.GameEngine;

public class Beam extends GameoWorldObjectHandler{

	public int direction  = 1; //0 left 1 right
	public float speed = 600.0f * (float)scale_width;
	public float damage = 15.0f;
	static float moveX = 0; 
	
	public static BufferedImage image = null;
	
	public Beam(float posX, float posY, int direction) {
		super(posX, posY);
		this.direction = direction;
		object_width = Gameworld.world.beam_width;
		object_height = Gameworld.world.beam_height;
		isSolid = false;
		
		if (beamCount == -1) {
			try {
				image =	GameEngine.loadTexture("/textureResource/beam_red.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void update (float deltaTime) {
		moveX = 0;
			
		moveX -= speed * direction;
			
		posX += moveX * deltaTime;
			
		if (doesCollide(posX, posY)) {
			if (itsEnemy) {
				shooted.takeDamage(damage);
				Gameworld.world.removeSprite(shooted);
				shooted = null;
			}
		}
		
		if (beamCount > 30) {
			for (GameoWorldObjectHandler sprite : Gameworld.world.sprites) {
				if (sprite instanceof Beam) {
					Gameworld.world.removeSprite(sprite);
				}
			}
			beamCount = 0;
		}
	}
	
	public void render(Graphics g) {
		realX = (int) posX - (int)object_width / 2;
		realY = (int) posY - (int)object_height / 2;
		
		realX = realX - (int)GameEngine.camX + GameEngine.arbitrary_width / 2;
		realY = realY - (int)GameEngine.camY + GameEngine.arbitrary_height / 2;
		
		g.drawImage(image, realX, realY, (int)object_width, (int)object_height, null);
	}
}
