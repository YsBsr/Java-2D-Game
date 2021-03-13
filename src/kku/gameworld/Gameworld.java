package kku.gameworld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import kku.graphics.GameEngine;
import kku.menu.Menu;
import kku.object.GameoWorldObjectHandler;

public class Gameworld extends GameEngine{

	public static Gameworld world = null;
	
	public int platform_width = 720;
	public int platform_height = 50;
	
	public int player_width = 60;
	public int player_height = 80;
	
	public int enemy_width = 60;
	public int enemy_height = 80;
	
	public int beam_width = 28;
	public int beam_height = 28;
	
	
	
	public ArrayList<GameoWorldObjectHandler> sprites = new ArrayList<GameoWorldObjectHandler>();
	public ArrayList<GameoWorldObjectHandler> addSprites = new ArrayList<GameoWorldObjectHandler>();
	public ArrayList<GameoWorldObjectHandler> removeSprites = new ArrayList<GameoWorldObjectHandler>();
	
	private static BufferedImage backdrop = null;
	private static int backdrop_x = 0;
	
	public Gameworld() {
		platform_width = (int)(platform_width * scale_width);
		platform_height = (int)(platform_height * scale_height);
		
		player_width = (int)(player_width * scale_width);
		player_height = (int)(player_height * scale_height);
		
		enemy_width = (int)(enemy_width * scale_width);
		enemy_height = (int)(enemy_height * scale_height);
		
		beam_width = (int)(beam_width * scale_width);
		beam_height = (int)(beam_height * scale_height);
	}
	
	public static void update() {
		if (menuVisible.get() && optionsMenuvisible.get() == false) {	
			Menu.menuLoop();
		} else if (menuVisible.get() == false && optionsMenuvisible.get()) {
			Menu.optionsMenuloop();
		} else if (menuVisible.get() == false && optionsMenuvisible.get() == false) {
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			
			
			for (GameoWorldObjectHandler sprite : world.sprites) {
				sprite.update(deltaTime);
			}
			
			for (GameoWorldObjectHandler sprite : world.addSprites) {
				if (!world.sprites.contains(sprite)) {
					world.sprites.add(sprite);
				}
			}
			world.addSprites.clear();
			
			for (GameoWorldObjectHandler sprite : world.removeSprites) {
				if (world.sprites.contains(sprite)) {
					world.sprites.remove(sprite);
				}
			}
			world.removeSprites.clear();
		}
		
		
	}
	
	public static void render(Graphics g) {
		//FPS COUNTER
		
		startTime = System.nanoTime();
		
		totalFrames++;
					
		if (System.nanoTime() > lastFPScheck + 1000000000) {
			lastFPScheck = System.nanoTime();
			currentFPS = totalFrames;
			totalFrames = 0;
		}
		//FPS COUNTER
		
		if (backdrop != null) {
			if (backdrop_x < GameEngine.camX / 2 - GameEngine.arbitrary_width) {
				backdrop_x +=  GameEngine.arbitrary_width;
			}
			
			if (backdrop_x > GameEngine.camX / 2 + GameEngine.arbitrary_width) {
				backdrop_x -=  GameEngine.arbitrary_width;
			}
			
			int x = backdrop_x - (int) (GameEngine.camX / 2);
			int buffer_x = 0;
			
			if (backdrop_x > (GameEngine.camX / 2)) {
				buffer_x = backdrop_x - GameEngine.arbitrary_width - (int) (GameEngine.camX / 2);
			} else {
				buffer_x = backdrop_x + GameEngine.arbitrary_width - (int) (GameEngine.camX / 2);
			}
			
			g.drawImage(backdrop, x, 0, GameEngine.arbitrary_width, GameEngine.arbitrary_height, null);
			g.drawImage(backdrop, buffer_x, 0, GameEngine.arbitrary_width, GameEngine.arbitrary_height, null);
			
		} else {
			try {
				backdrop = GameEngine.loadTexture("/textureResource/c.jpg");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for (GameoWorldObjectHandler sprite : world.sprites) {
			sprite.render(g);
		}
		
		//DRAW FPS COUNTER
		g.setColor(Color.white);
		g.setFont(font_fps);
		g.drawString(String.valueOf("FPS: " + currentFPS), 10, 30);
							
		g.dispose();

		g = canvas.getGraphics();
		g.drawImage(vi, 0, 0, arbitrary_width, arbitrary_height, null);
							
		g.dispose();
		
		totalTime = System.nanoTime() - startTime;
		
		if (totalTime < targetTime) {
			try {
				Thread.sleep((targetTime - totalTime) / 1000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addSprite (GameoWorldObjectHandler sprite) {
		if (!addSprites.contains(sprite)) {
			addSprites.add(sprite);
		}
	}
	
	public void removeSprite (GameoWorldObjectHandler sprite) {
		if (!removeSprites.contains(sprite)) {
			removeSprites.add(sprite);
		}
	}
	
	public void cleanSprites() {
		for (GameoWorldObjectHandler c_sprite : world.sprites) {
				Gameworld.world.removeSprite(c_sprite);
		}
	}
	
}
