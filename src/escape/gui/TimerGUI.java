package escape.gui;

import java.awt.Point;
import java.awt.Rectangle;

import escape.Game;
import escape.Mouse.Button;

public class TimerGUI extends Menu{

	private long start_time;
	private int elapsed_time_seconds;
	private boolean started=false;
	private int col = 0xff0000;
	
	public TimerGUI(Rectangle rect) {
		super(rect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Game game) {
		fill(0);
		drawInts("888", 0, 0, 0x441111);
		if (game.level==null){
			started=false;
			drawInts("999", 0, 0, col);
		}
		else if (started ==false){
			start_time = System.currentTimeMillis();
			started = true;
			drawInts("000", 0, 0, col);
			
		}
		else if (started ==true){
			if (game.gamestate==Game.Gamestate.DEFAULT)
				elapsed_time_seconds = (int)(System.currentTimeMillis()-start_time)/1000;
			drawInts(String.format("%03d", elapsed_time_seconds),0,0, col);
		}
		
	}

	@Override
	public void click(Game game, Point pointer, Button button) {
		
	}

	
}
