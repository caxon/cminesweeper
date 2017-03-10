package escape.gui;

import java.awt.Point;
import java.awt.Rectangle;

import escape.Art;
import escape.EscapeComponent;
import escape.Game;
import escape.Mouse;
import escape.Game.Gamestate;
import level.Level;

public class Tiles extends Menu{

	public Tiles(Rectangle rect) {
		super(rect);
		// TODO Auto-generated constructor stub
	}
	
	public Tiles(int game_width, int game_height, int offy){
//		super(new Rectangle(0, EscapeComponent.SCALE* offy, game_width*EscapeComponent.SCALE * 16, game_height*EscapeComponent.SCALE * 16));
		super(new Rectangle(0,offy,game_width*16,game_height*16));
	}
	
	public void render(Game game){
		setupGrid(game.width,game.height);
		if (game.level!=null){
			for (int i=0;i<game.level.bombs.length;i++){
	
				if (game.level.bombs[i]==1 && game.gamestate==Game.Gamestate.LOSS)
					if (game.level.flags[i]==1)
						;
					else 
						draw(Art.mine, (i%game.width) * 16, (i/game.width) * 16);
				
				if (game.level.bombs[i]==2 )
					draw(Art.pressed_tile, (i%game.width) * 16, (i/game.width) * 16);
	
				if ((game.level.numbers[i]!=0) && (game.level.bombs[i]==2))
					draw(Art.getNumber(game.level.numbers[i]), (i%game.width) * 16, (i/game.width)*  16);
				
	
				if (game.level.bombs[i]==3){
					draw(Art.red_tile, (i%game.width) * 16, (i/game.width) * 16);
					draw(Art.mine, (i%game.width) * 16, (i/game.width) * 16);
				}
				
				if (game.level.flags[i]==1){
					if (game.gamestate==Game.Gamestate.DEFAULT || 
							((game.gamestate==Game.Gamestate.LOSS || game.gamestate==Gamestate.WIN)&& game.level.flags[i] ==1 && game.level.bombs[i]==1))
						draw(Art.flag, (i%game.width) * 16, (i/game.width) * 16);
					else 
						draw(Art.flag_error, (i%game.width) * 16, (i/game.width) * 16);
				}
			}
			
			if (game.gamestate==Game.Gamestate.LOSS){
				draw(Art.game_over_2x,width/2-Art.game_over_2x.width/2,height/2-Art.game_over_2x.height/2);
			}
			else if (game.gamestate==Game.Gamestate.WIN){
				draw(Art.you_win_2x,width/2-Art.you_win_2x.width/2,height/2-Art.you_win_2x.height/2);
			}
		}
		
	}
	
	public void setupGrid(int width, int height){
		for (int iy = 0; iy<height; iy++)
			for (int ix=0; ix<width; ix++){
				draw(Art.tile,16*ix,16*iy);
			}
	}

	@Override
	public void click(Game game, Point pointer, Mouse.Button button) {
		int mouse_index = pointer.x/16  + pointer.y/16*game.width;
		
		if (game.level==null){
			game.level = new Level(game, mouse_index);
		}
		
		if (game.gamestate==Game.Gamestate.DEFAULT){
			if (button==Mouse.Button.LEFT)
				game.click(mouse_index);
			else if (button == Mouse.Button.RIGHT)
				game.alt_click(mouse_index);
		}
	}

}
