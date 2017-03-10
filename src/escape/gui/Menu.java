package escape.gui;

import java.awt.Point;
import java.awt.Rectangle;

import escape.Game;
import escape.Mouse;

public abstract class Menu extends Bitmap{
	
	public int offx=0;
	public int offy=0;
	public Rectangle rect;
	

	public Menu(Rectangle rect) {
		super(rect.width, rect.height);
		this.rect = rect;
	}

	public abstract void render(Game game);
	public abstract void click(Game game, Point pointer, Mouse.Button button);
	
}
