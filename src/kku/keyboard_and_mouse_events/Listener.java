package kku.keyboard_and_mouse_events;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import kku.game.Game;
import kku.graphics.GameEngine;
import kku.sound.Sound;

public class Listener extends GameEngine{
	
	public static int mouse_x = 0;
	public static int mouse_y = 0;
	
	private static boolean[] keys = new boolean[196];
	private static boolean[] lastkeys = new boolean[196];
	
	public static void closeableWindow() {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Game.quit();
			}
		});
	}
	
	public static boolean getKey(int keycode) {
		return keys[keycode];
	}
	
	public static boolean getKeydown (int keycode) {
		return keys[keycode] && !lastkeys[keycode];
	}
	
	public static boolean getKeyup (int keycode) {
		return !keys[keycode] && lastkeys[keycode];
	}
	
	public static void finishKeyinput() {
		lastkeys = keys.clone();
	}
	
	public static void keyEvents() {
		canvas.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (e != null) {
				if (e.getKeyCode() == 27) {
					if (menuVisible.get() == false) {
						menuVisible.set(true);
					} else {
						menuVisible.set(false);
					}
				
				if (optionsMenuvisible.get() == true) {
					optionsMenuvisible.set(false);
					menuVisible.set(true);
				} 
			} else {
					if (menuVisible.get() == false && optionsMenuvisible.get() == false) {
						keys[e.getKeyCode()] = true;
					}
				}
				e = null;
				}
			}
			
		public void keyReleased(KeyEvent e) {
			if (menuVisible.get() == false && optionsMenuvisible.get() == false) {
				keys[e.getKeyCode()] = false;
				}
			}
				
		});
	}
	
	public static void mouseEvents() {
		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mouse_x = e.getX();
				mouse_y = e.getY();
				
				if (menuVisible.get() && optionsMenuvisible.get() == false){
					if (mouse_x > button_x && mouse_y > play_button_y && mouse_x < play_b_end_x && mouse_y < play_b_end_y) {
						System.out.println(e.getX());
						menuVisible.set(false);
					} else if (mouse_x > button_x && mouse_y > options_button_y && mouse_x < opt_b_end_x && mouse_y < opt_b_end_y) {
						if (optionsMenuvisible.get() == false) {
							menuVisible.set(false);
							optionsMenuvisible.set(true);
						} 
					} else if (mouse_x > button_x && mouse_y > quit_button_y && mouse_x < quit_b_end_x && mouse_y < quit_b_end_y) {
						Game.quit();
					}
				} else if (menuVisible.get() == false && optionsMenuvisible.get()) {
					if (mouse_x > button_x && mouse_y > resolution_button_y && mouse_x < play_b_end_x && mouse_y < play_b_end_y) {
						
						arbitrary_width = screenModes[3][0];
						arbitrary_height = screenModes[3][1];
						System.out.println("screen size: " + arbitrary_width + " x " + arbitrary_height);
						try {
							optionsMenuvisible.set(false);
							gameVisible.set(false);
							Thread.sleep(250);
							inGame_thread.join();
							inGame_thread = null;
							Thread.sleep(250);
							resizeScreen();
							optionsMenuvisible.set(true);
							gameVisible.set(true);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else if (mouse_x > button_x && mouse_y > resolution_button2_y && mouse_x < play_b_end_x && mouse_y < res_but_end2_y) {
						arbitrary_width = screenModes[2][0];
						arbitrary_height = screenModes[2][1];
						System.out.println("screen size: " + arbitrary_width + " x " + arbitrary_height);
						try {
							optionsMenuvisible.set(false);
							gameVisible.set(false);
							Thread.sleep(250);
							inGame_thread.join();
							inGame_thread = null;
							Thread.sleep(250);
							resizeScreen();
							optionsMenuvisible.set(true);
							gameVisible.set(true);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else if (mouse_x > button_x && mouse_y > resolution_button3_y && mouse_x < play_b_end_x && mouse_y < res_but_end3_y) {
						arbitrary_width = screenModes[1][0];
						arbitrary_height = screenModes[1][1];
						System.out.println("screen size: " + arbitrary_width + " x " + arbitrary_height);
						try {
							optionsMenuvisible.set(false);
							gameVisible.set(false);
							Thread.sleep(250);
							inGame_thread.join();
							inGame_thread = null;
							Thread.sleep(250);
							resizeScreen();
							optionsMenuvisible.set(true);
							gameVisible.set(true);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else if (mouse_x > button_x && mouse_y > resolution_button4_y && mouse_x < play_b_end_x && mouse_y < res_but_end4_y) {
						arbitrary_width = screenModes[0][0];
						arbitrary_height = screenModes[0][1];
						System.out.println("screen size: " + arbitrary_width + " x " + arbitrary_height);
						try {
							optionsMenuvisible.set(false);
							gameVisible.set(false);
							Thread.sleep(250);
							inGame_thread.join();
							inGame_thread = null;
							Thread.sleep(250);
							resizeScreen();
							optionsMenuvisible.set(true);
							gameVisible.set(true);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} else if (mouse_x > button_x && mouse_y > music_button_y && mouse_x < play_b_end_x && mouse_y < music_button_end_y) {
						if (i == 0) {
							music = choices[4];
							try {
								Sound.playMusic(1);
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
								e1.printStackTrace();
							}
							i = 1;
						} else {
							music = choices[3];
							try {
								Sound.playMusic(2);
							} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
								e1.printStackTrace();
							}
							i = 0;
						}
					} else  if (mouse_x > button_x && mouse_y > quit_button_y && mouse_x < play_b_end_x && mouse_y < quit_b_end_y) {
						if (optionsMenuvisible.get() == true) {
							menuVisible.set(true);
							optionsMenuvisible.set(false);
						} 
					}
				}
			}
		});
	}
	
	public static void mouseReleased() {
		
	}
}
