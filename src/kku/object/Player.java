package kku.object;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import kku.gameworld.Gameworld;
import kku.graphics.Animation;
import kku.graphics.GameEngine;
import kku.keyboard_and_mouse_events.Listener;
import kku.sound.Sound;

public class Player extends GameoWorldObjectHandler{

	private float velocityY = 0;
	private float gravity = 150.0f * (float)scale_height;
	private float jump = 200 * (float)scale_height;
	private int direction = 1;
	
	public Player(float posX, float posY) {
		super(posX, posY);
		
		object_width = Gameworld.world.player_width;
		object_height = Gameworld.world.player_height;
		movementSpeed = (int)(250 * scale_width);
		isSolid = true;
		
		Animation anim = new Animation();
		try {
			anim.images.add(GameEngine.loadTexture("/textureResource/mainchara.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		animations = new Animation[] {
				anim
		};
	}
		
	public void update (float deltaTime) {
		float moveX = 0;
		
		if (Listener.getKey(KeyEvent.VK_A)) {
			moveX -= movementSpeed;
		}
		
		if (Listener.getKey(KeyEvent.VK_D)) {
			moveX += movementSpeed;
		}
		
		if (moveX > 0) {
			direction = 1;
		} 
		
		if (moveX < 0) {
			direction = -1;
		}
		
		velocityY += gravity * deltaTime;
		
		if (doesCollide(posX, posY + 1)) { //HAVADA ikinci bir sýçrayýþ olmasýný engeller
			if (Listener.getKeydown(KeyEvent.VK_SPACE)) {
				velocityY = (float) - Math.sqrt(2 * jump * gravity);
			}
		}
		
		//COLLISION
		if (doesCollide(posX + moveX * deltaTime, posY)) moveX -= moveX;
		
		if (doesCollide(posX, posY + velocityY * deltaTime)) velocityY -= velocityY;
		//COLLISION
		
		posX += moveX * deltaTime;
		posY += velocityY * deltaTime;
		
		if (Listener.getKeydown(KeyEvent.VK_T)) {
			Beam beam = new Beam(posX, posY, direction);
			try {
				Sound.playBeamEffect();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				e.printStackTrace();
			}
			beamCount++;
			Gameworld.world.addSprite(beam);
		}
		
		camX = posX;
		camY = (int)(360 * scale_width);
	}
	
}
