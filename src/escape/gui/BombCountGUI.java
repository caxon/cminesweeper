package escape.gui;

import java.awt.Point;
import java.awt.Rectangle;

import escape.Game;
import escape.Mouse.Button;

public class BombCountGUI extends Menu{

	public BombCountGUI(Rectangle rect) {
		super(rect);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Game game){
		int count = game.bomb_count;
		if (game.level!=null)
			for (int i =0;i<game.level.flags.length;i++){
				if (game.level.flags[i]==1)
					count--;
		}
		else
			count=999;
		if (count<0)
			count=0;
		fill(0);

		drawInts("888", 0, 0, 0x114411);
		drawInts(String.format("%03d", count), 0, 0, 0x00ff00);
		
	}

	@Override
	public void click(Game game, Point pointer, Button button) {
		// TODO Auto-generated method stub
		
	}

}
