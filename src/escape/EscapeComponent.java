package escape;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import util.util;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;




import escape.Game;
import escape.InputHandler;
//import escape.gui.Screen;
import escape.gui.Screen;

public class EscapeComponent extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static Object mouselock = new Object();

	public static int WIDTH;
	public static int HEIGHT;
	public static final int SCALE = 3;
	public static final String GAME_TITLE = "CMinesweeper V2";

	private boolean running;
	private Thread thread;

	private Game game;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;
	private InputHandler inputHandler;
	private Cursor emptyCursor, defaultCursor;
	private boolean hadFocus = false;
	
	public EscapeComponent() {
		

		game = new Game();
		HEIGHT = game.height*16 + 16;
		WIDTH = game.width*16;
		
		
		Dimension size = new Dimension(WIDTH * SCALE-10, HEIGHT * SCALE-10);
		
	/**
	 * If you are on a mac (looking at you adam), change the above line to the following:
	 * 
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	 */
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		
		screen = new Screen(WIDTH, HEIGHT);

		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		inputHandler = new InputHandler();

		addKeyListener(inputHandler);
		addFocusListener(inputHandler);
		addMouseListener(inputHandler);
		addMouseMotionListener(inputHandler);
		//emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "empty");
		/*try {
			emptyCursor = Toolkit.getDefaultToolkit().createCustomCursor (ImageIO.read(new URL("http://icons.iconarchive.com/icons/hopstarter/plastic-mini/16/Cursor-icon.png")), new Point(0, 0), "empty");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		defaultCursor = getCursor();
		//url = http://icons.iconarchive.com/icons/hopstarter/plastic-mini/16/Cursor-icon.png
	}
	
	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		int frames = 0;

		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;

		requestFocus();

		while (running) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0) passedTime = 0;
			if (passedTime > 100000000) passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
				if (tickCount % 60 == 0) {
					if (frames <50) System.out.println(frames + " fps");
					lastTime += 1000;
					frames = 0;
				}
			}

			if (ticked) {
				render();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	private void tick() {
		if (hasFocus()) {
			game.tick(inputHandler.keys,inputHandler.mouse);
		}
	}
	
	private void render(){
		if (hadFocus != hasFocus()) {
			hadFocus = !hadFocus;
			setCursor(hadFocus ? emptyCursor : defaultCursor);
		}
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.render(game);
		
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		//pixels[0]=0xffffff;
		//pixels[pixels.length-1]=0xaabbccc;
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		EscapeComponent game = new EscapeComponent();

		JFrame frame = new JFrame(GAME_TITLE);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(game, BorderLayout.CENTER);

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);

		game.start();
		//Sound.altar.play();
		
	}
	
}


