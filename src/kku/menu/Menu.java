package kku.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;

import kku.graphics.GameEngine;

public class Menu extends GameEngine{
	private static Font font = null;
	private static Font optionsFont = null;
	
	private static BufferedImage background = null;
	
	public static void menu_item_scale() {
		choices = new String[] {"Play", "Options", "Quit", "Music On", "Music Off", "Back"};
		
		font_size = 36;
		options_font_size = 30;
		
		font_size = (int)(36 * scale_width);
		options_font_size = (int)(options_font_size * scale_width);
		font = new Font("Arial", Font.PLAIN, font_size);
		optionsFont = new Font("Arial", Font.PLAIN, options_font_size);
		
		button_x = (texture_width / 2) - 96;
		button_x_writing = (texture_width / 2) - 96;
		play_button_y = (texture_height / 6);
		options_button_y = (texture_height / 6) * 2;
		quit_button_y = (texture_height / 6) * 4;
		
		center_play_x = 60;
		center_play_y = 36;
		center_opt_x = 36;
		center_opt_y = 36;
		center_quit_x = 60;
		center_quit_y = 36;

		play_button_y = (int)(play_button_y * scale_height);
		options_button_y = (int)(options_button_y * scale_height);
		quit_button_y = (int)(quit_button_y * scale_height);
		
		center_play_x = (int)((button_x + center_play_x) * scale_width);
		center_play_y = (int)(center_play_y * scale_height) + play_button_y;
		
		center_opt_x = (int)((button_x + center_opt_x) * scale_width);
		center_opt_y = (int)(center_opt_y * scale_height) + options_button_y;
		
		center_quit_x = (int)((button_x + center_quit_x) * scale_width);
		center_quit_y = (int)(center_quit_y * scale_height) + quit_button_y;
		
		button_x = (int)(button_x_writing * scale_width);
		System.out.println(play_button_y);
		System.out.println(options_button_y);
		System.out.println(quit_button_y);
		
		button_width = 192;
		button_height = 48;
		
		button_width = (int)(button_width * scale_width);
		button_height = (int)(button_height * scale_height);
		
		play_b_end_x = button_x + button_width;
		play_b_end_y = play_button_y + button_height;
		
		opt_b_end_x = play_b_end_x;
		opt_b_end_y = options_button_y + button_height;
		
		quit_b_end_x = play_b_end_x;
		quit_b_end_y = quit_button_y + button_height;
		
		resolution_button_y = play_button_y;
		back_button_y = quit_button_y;
		
		shift_y = (int)(shift_y * scale_width);
		
		resolution_button2_y = resolution_button_y + button_height + shift_y;
		resolution_button3_y = resolution_button2_y + button_height + shift_y;
		resolution_button4_y = resolution_button3_y + button_height + shift_y;
		music_button_y = resolution_button4_y + button_height + shift_y;
		
		center_resolution_x = 18;
		center_resolution_y = 36;
		
		center_resolution_x = (int)((button_x_writing + center_resolution_x) * scale_width);
		center_resolution_y = (int)(center_resolution_y * scale_height) + resolution_button_y;
		
		center_resolution2_y = center_resolution_y + button_height + shift_y;
		center_resolution3_y = center_resolution2_y + button_height + shift_y;
		center_resolution4_y = center_resolution3_y + button_height + shift_y;
		center_resolution5_y = center_resolution4_y + button_height + shift_y;
		
		res_but_end2_y = play_b_end_y + button_height + shift_y;
		res_but_end3_y = res_but_end2_y + button_height + shift_y;
		res_but_end4_y = res_but_end3_y + button_height + shift_y;
		music_button_end_y = res_but_end4_y + button_height + shift_y;
		
		music = choices[3];
		
		try {
			background = GameEngine.loadTexture("/textureResource/mainmenu.jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void optionsMenuloop() {
		while(optionsMenuvisible.get()) {
			startTime = System.nanoTime();
			
			totalFrames++;
			
			if (System.nanoTime() > lastFPScheck + 1000000000) {
				lastFPScheck = System.nanoTime();
				currentFPS = totalFrames;
				totalFrames = 0;
			}
			
			if (vi.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
				vi = gc.createCompatibleVolatileImage(arbitrary_width, arbitrary_height);
			}
			
			g = vi.getGraphics();
			
			
			//REFRESH SCREEN
			g.drawImage(background, 0, 0, GameEngine.arbitrary_width, GameEngine.arbitrary_height, null);
			
		
			//DRAW FPS COUNTER
			g.setColor(Color.white);
			g.setFont(font_fps);
			g.drawString(String.valueOf("FPS: " + currentFPS), 10, 30);
			
			//OPTIONS BUTTONS
			g.setColor(Color.lightGray);
			g.fillRect(button_x, resolution_button_y, button_width, button_height);
			g.fillRect(button_x, resolution_button2_y, button_width, button_height);
			g.fillRect(button_x, resolution_button3_y, button_width, button_height);
			g.fillRect(button_x, resolution_button4_y, button_width, button_height);
			g.fillRect(button_x, music_button_y, button_width, button_height);
			
			g.fillRect(button_x, back_button_y, button_width, button_height);
			g.setColor(Color.black);
			g.setFont(optionsFont);
			g.drawString(screenModes[3][0] + " x " + screenModes[3][1], center_resolution_x, center_resolution_y);
			g.drawString(screenModes[2][0] + " x " + screenModes[2][1], center_resolution_x, center_resolution2_y);
			g.drawString(screenModes[1][0] + " x " + screenModes[1][1], center_resolution_x, center_resolution3_y);
			g.drawString(screenModes[0][0] + " x " + screenModes[0][1], center_resolution_x, center_resolution4_y);
			g.drawString(music, center_resolution_x, center_resolution5_y);
			g.drawString(choices[5], center_quit_x, center_quit_y);
			
			
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
	}
	
	public static void menuLoop() {
		while(menuVisible.get()) {
			startTime = System.nanoTime();
			
			totalFrames++;
			
			if (System.nanoTime() > lastFPScheck + 1000000000) {
				lastFPScheck = System.nanoTime();
				currentFPS = totalFrames;
				totalFrames = 0;
			}
			
			if (vi.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
				vi = gc.createCompatibleVolatileImage(arbitrary_width, arbitrary_height);
			}
			
			g = vi.getGraphics();
			
			//REFRESH SCREEN
			g.drawImage(background, 0, 0, GameEngine.arbitrary_width, GameEngine.arbitrary_height, null);
			
			g.setColor(Color.red);
			g.fillRect(button_x, play_button_y, button_width, button_height);
			g.fillRect(button_x, options_button_y, button_width, button_height);
			g.fillRect(button_x, quit_button_y, button_width, button_height);
			g.setColor(Color.white);
			g.setFont(font);
			g.drawString(choices[0], center_play_x, center_play_y);
			g.drawString(choices[1], center_opt_x, center_opt_y);
			g.drawString(choices[2], center_quit_x, center_quit_y);
			//REFRESH SCREEN
			
			
			//render everything
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
	}
	
}
