package escape.gui;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import escape.gui.Bitmap;
import escape.EscapeComponent;
import escape.Game;

import java.util.Random;

import level.Level;
import escape.Art;

public class Screen extends Bitmap{
	
	ArrayList<Menu> menus = new ArrayList<>();
	
	
	public Screen(int width, int height) {

		super(width, height);
		this.fill(0, 0, width, height, 0);
	}
	
	int time = 0;
	
	public void render(Game game){
		for (int i=0;i<game.menus.size();i++){
			Menu current_menu = game.menus.get(i);
			current_menu.render(game);
			draw(current_menu,current_menu.rect.x,current_menu.rect.y);
		}
	}


}
