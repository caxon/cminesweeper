package escape.gui;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import escape.Art;
import escape.Game;
import escape.Mouse;

public class ResetButton extends Menu{

	public Rectangle rect;
	
	public ResetButton(Rectangle rect) {
		super(rect);
	}

	@Override
	public void render(Game game){
		draw(Art.pressed_tile,0,0);
		draw(Art.smile, 0, 0);
	}
	
	@Override
	public void click(Game game, Point pointer, Mouse.Button mouse_button){
		game.reset();
	}
}
