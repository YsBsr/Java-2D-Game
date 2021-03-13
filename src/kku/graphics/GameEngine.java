package kku.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import kku.gameworld.Gameworld;
import kku.keyboard_and_mouse_events.Listener;
import kku.menu.Menu;
import kku.object.Enemy;
import kku.object.Platform;
import kku.object.Player;
import kku.sound.Sound;

public class GameEngine implements Runnable{
	public static Thread inGame_thread;
	
	//public static boolean gameVisible = true;
	public static AtomicBoolean gameVisible = new AtomicBoolean(true);
	
	public static Frame frame;
	public static Canvas canvas;
	
	public static DisplayMode dm;
	public static GraphicsEnvironment ge;
	public static GraphicsDevice[] gd;
	public static DisplayMode old;
	public static GraphicsConfiguration gc;
	public static VolatileImage vi;
	public static Graphics g;
	
	public static int screenSizewidth = 0;
	public static int screenSizeheight = 0;
	
	public static int arbitrary_width = 0;
	public static int arbitrary_height = 0;
	public static int texture_width = 1280;
	public static int texture_height = 720;
	
	public static float camX = 0;
	public static float camY = 0;
	
	public static double scale_width = 0;
	public static double scale_height = 0;
	
	public static Font font_fps = null;
	public static int fps_font_size = 16;
	public static long startTime = 0;
	public static long totalTime = 0;
	public static int targetFPS = 60;
	public static int targetTime = 1000000000 / targetFPS;
	public static long lastFPScheck = 0;
	public static long currentFPS = 0;
	public static long totalFrames = 0;
	
	//MENU
	public static int button_x = 0;
	public static int button_x_writing = 0;
	
	public static int play_button_y = 0;
	public static int play_b_end_x = 0;
	public static int play_b_end_y = 0;
	
	public static int options_button_y = 0;
	public static int opt_b_end_x = 0;
	public static int opt_b_end_y = 0;
	
	public static int quit_button_y = 0;
	public static int quit_b_end_x = 0;
	public static int quit_b_end_y = 0;
	
	public static int center_play_x = 0;
	public static int center_play_y = 0;
	public static int center_opt_x = 0;
	public static int center_opt_y = 0;
	public static int center_quit_x = 0;
	public static int center_quit_y = 0;
	
	public static int button_width = 0;
	public static int button_height = 0;
	
	public static int shift_y = 10;
	public static int resolution_button2_y = 0;
	public static int resolution_button3_y = 0;
	public static int resolution_button4_y = 0;
	
	public static int center_resolution2_y = 0;
	public static int center_resolution3_y = 0;
	public static int center_resolution4_y = 0;
	public static int center_resolution5_y = 0;
	
	public static int res_but_end1_y = 0;
	public static int res_but_end2_y = 0;
	public static int res_but_end3_y = 0;
	public static int res_but_end4_y = 0;
	public static int music_button_end_y = 0;
	
	public static int font_size = 0;
	
	//public static boolean menuVisible = true;
	public static AtomicBoolean menuVisible = new AtomicBoolean(true);
	
	//OPTIONS MENU VARIABLE
	//public static boolean optionsMenuvisible = false;
	public static AtomicBoolean optionsMenuvisible = new AtomicBoolean(false);
	public static int[][] screenModes;
	
	public static int resolution_button_y = 0;
	public static int music_button_y = 0;
	public static int back_button_y = 0;
	
	public static int center_resolution_x = 0;
	public static int center_resolution_y = 0;
	
	public static int center_vsynch_x = 0;
	public static int center_vsynch_y = 0;
	
	public static int center_back_x = 0;
	public static int center_back_y = 0;
	
	public static int i = 0;
	public static int j = 0;
	
	public static int options_font_size = 0;
	
	public static String[] choices = null;
	public static String music = null;
	//OPTIONS MENU VARIABLE
	//MENU
	
//CREATING GAME SCREEN FOR EVERY RESOLUTION MODES THAT COMPATIBLE WITH THE HARDWARE
	private static void setDefaultresolution() {
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getScreenDevices();
		
		old = gd[1].getDisplayMode();
		
		screenSizewidth = gd[1].getDisplayMode().getWidth();
		screenSizeheight = gd[1].getDisplayMode().getHeight();
		
		arbitrary_width = screenSizewidth;
		arbitrary_height = screenSizeheight;
		
		scale_width = (double)arbitrary_width / texture_width;
		scale_height = (double)arbitrary_height / texture_height;
		
		fps_font_size = (int)(fps_font_size * scale_width);
	}
	
	public static void fullScreenforWindowedMods() {
		frame = new Frame();
		canvas = new Canvas();
		
		canvas.setPreferredSize(new Dimension(arbitrary_width, arbitrary_height));
		
		frame.add(canvas); //oluþturduðumuz canvas'ý frame'in içine koyar.
		
		if (gd[1].isFullScreenSupported()) { //burada donanýmýmýz fullscreen destekliyor mu kontrol ediyoruz.
			frame.setUndecorated(true);
			
			dm = new DisplayMode(arbitrary_width, arbitrary_height, old.getBitDepth(), old.getRefreshRate());
			
			gd[1].setFullScreenWindow(frame);
			gd[1].setDisplayMode(dm);
		}
		else {
			JOptionPane.showMessageDialog(frame, "Graphic Device does not support fullscreen mode!");
		}
		
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		frame.pack(); //oluþturduðumuz frame'i içindekilerle beraber paketler, bir kalýba koyar.
		frame.setResizable(false); 
		
		frame.setVisible(true); //pencere ekranda gözükür.
		gc = canvas.getGraphicsConfiguration();
		vi = gc.createCompatibleVolatileImage(arbitrary_width, arbitrary_height);
		
		Gameworld.world = new Gameworld();
		Gameworld.world.addSprite(new Player(arbitrary_width / 2, (int)(200 * scale_width)));
		Gameworld.world.addSprite(new Platform(arbitrary_width / 2, (int)(360 * scale_width), Gameworld.world.platform_width, Gameworld.world.platform_height));
		Gameworld.world.addSprite(new Enemy(arbitrary_width / 2 , (int)(0 * scale_width)));
	}
	
	public static void resizeScreen() throws InterruptedException {
		Thread.sleep(250);
		
		frame.setVisible(false);
		scale_width = (double)arbitrary_width / texture_width;
		scale_height = (double)arbitrary_height / texture_height;
			
		fps_font_size = (int)(fps_font_size * scale_width);
			
		frame.setVisible(false);
		frame.dispose();
		frame = null; //Burada heap'te bulunun eski frame objesesini garbage collector'ýn silmesini istiyoruz.
		Gameworld.world.cleanSprites();
		Gameworld.world = null;
		frame = new Frame();
		Gameworld.world = new Gameworld();
		Gameworld.world.addSprite(new Player(arbitrary_width / 2, (int)(200 * scale_width)));
		Gameworld.world.addSprite(new Platform(arbitrary_width / 2, (int)(360 * scale_width), Gameworld.world.platform_width, Gameworld.world.platform_height));
		Gameworld.world.addSprite(new Enemy(arbitrary_width / 2, (int)(0 * scale_width)));
		
		System.gc();
		
		Thread.sleep(250);
		
		canvas.setPreferredSize(new Dimension(arbitrary_width, arbitrary_height));
		
		frame.add(canvas);
			
		frame.setUndecorated(true);
			
		dm = new DisplayMode(arbitrary_width, arbitrary_height, old.getBitDepth(), old.getRefreshRate());
			
		gd[1].setFullScreenWindow(frame);
		gd[1].setDisplayMode(dm);
			
		frame.pack(); //oluþturduðumuz frame'i içindekilerle beraber paketler, bir kalýba koyar.
			
		frame.setVisible(true); //pencere ekranda gözükür.
		gc = canvas.getGraphicsConfiguration();
		vi = gc.createCompatibleVolatileImage(arbitrary_width, arbitrary_height);
			
		Menu.menu_item_scale();
			
		Listener.closeableWindow();
		
		renderLoop();
	}
	
	public void run() {
		//RENDERIN GAME SCREEN FOR EVERY RESOLUTION MODES THAT COMPATIBLE WITH THE HARDWARE VIA ONE THREAD
		System.out.println("arbitrary_size: " + arbitrary_width + " x " + arbitrary_height);
		System.out.println("scale_x: " + scale_width + " scale_y: " + scale_height + " " + (int)(150 * scale_width));
					
		while (true) {
			if (vi.validate(gc) == VolatileImage.IMAGE_INCOMPATIBLE) {
				vi = gc.createCompatibleVolatileImage(arbitrary_width, arbitrary_height);
			}
			
			if (gameVisible.get() == false) {
				return;
			}
					
			g = vi.getGraphics();
							
			//REFRESH SCREEN
			g.setColor(Color.black);
			g.fillRect(0, 0, arbitrary_width, arbitrary_height);
			//REFRESH SCREEN
								
			//RENDERGAME
			Gameworld.update();
			Listener.finishKeyinput();
			Gameworld.render(g);
			//RENDERGAME
		}
	}
	
	private static void renderLoop() {
		inGame_thread = new Thread(new GameEngine());
			
		inGame_thread.setName("In-Game Thread");
		inGame_thread.start();
	}
	
	public static BufferedImage loadTexture(String path) throws IOException {
		BufferedImage sourceImage = ImageIO.read(GameEngine.class.getResource(path));
		BufferedImage texturedImage = canvas.getGraphicsConfiguration().
				createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getTransparency());
		
		texturedImage.getGraphics().drawImage(sourceImage, 0, 0, sourceImage.getWidth(), sourceImage.getHeight(), null);
		
		return texturedImage;
	}
	
	public static void init() {
		setDefaultresolution();
		
		screenModes = new int[][] {{800, 600}, {1024, 768}, {1280, 720}, {1920, 1080}};
	
		font_fps = new Font("Arial", Font.BOLD, fps_font_size);
		
		fullScreenforWindowedMods();

		Menu.menu_item_scale();
		
		Listener.closeableWindow();
		
		Listener.keyEvents();
		
		Listener.mouseEvents();
		
		try {
			Sound.playMusic(0);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		
		renderLoop();
	}

}
