package escape;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import escape.gui.ResetButton;
import escape.gui.BombCountGUI;
import escape.gui.Menu;
import escape.gui.Tiles;
import escape.gui.TimerGUI;
import util.util;

import level.Level;

public class Game {

	public Level level;
	
	public int height = 16;
	public int width = 16;
	public int bomb_count = 50;
	public ArrayList<Menu> menus;

	public Gamestate gamestate = Gamestate.DEFAULT;
	
	public enum Gamestate{
		DEFAULT, WIN, LOSS;
	}
	
	
	public Game(){
		if (bomb_count>height*width)
			bomb_count = height*width-9;
		gamestate=Gamestate.DEFAULT;
		level = null;

		menus = new ArrayList<>();
		menus.add(new Tiles(width, height, 16));
		menus.add(new ResetButton(new Rectangle((width*16-16)/2, 0, 16, 16)));
		menus.add(new BombCountGUI(new Rectangle(2,1,24,12)));
		menus.add(new TimerGUI(new Rectangle(width*16-24,1,24,12)));
		
	}
	
	public void tickf(boolean[] keys, Mouse mouse) {
		if (keys[KeyEvent.VK_R]){
			keys[KeyEvent.VK_R]=false;
			mouse.button =null;
			reset();
		}
			if (mouse.button!=null && gamestate==Gamestate.DEFAULT){
				int mouse_index = (int)mouse.pointer.x/16/EscapeComponent.SCALE  + (int)mouse.pointer.y/16/EscapeComponent.SCALE *width;
				if (level==null){
					level = new Level(this, mouse_index);
					click(mouse_index);
				}
				
				else if (mouse.button==mouse.button.LEFT)
					click(mouse_index);
				else if (mouse.button==mouse.button.RIGHT)
					alt_click(mouse_index);
				mouse.button=null;
			}
	}
	
	public void tick(boolean[] keys, Mouse mouse){
		if (keys[KeyEvent.VK_R]){
			keys[KeyEvent.VK_R]=false;
			mouse.button =null;
			reset();
		}
		if (keys[KeyEvent.VK_SHIFT]){
			keys[KeyEvent.VK_SHIFT]=false;
			clickAroundFlagsAll();
		}
		
		Point pointer = mouse.pointer;
		if (mouse.button!=null){
			for (int i=0;i<menus.size();i++){
				Menu cmenu = menus.get(i);
				if (cmenu.rect.contains(new Point(pointer.x/EscapeComponent.SCALE, pointer.y/EscapeComponent.SCALE))){
					cmenu.click(this, new Point(pointer.x/EscapeComponent.SCALE-cmenu.rect.x, 
							pointer.y/EscapeComponent.SCALE-cmenu.rect.y),mouse.button);
				}
			}
			mouse.button=null;
		}
		
		if (keys[KeyEvent.VK_P]){
			keys[KeyEvent.VK_P]=false;
		}
		
		if (keys[KeyEvent.VK_S]){
			level.saveLevel();
			keys[KeyEvent.VK_S]=false;
			
		}
		if (keys[KeyEvent.VK_D]){
			level.loadLevel();
			keys[KeyEvent.VK_D]=false;
			mouse.button =null;
		}
	}

	public void saveFile(){
		
	}
	public void loadFile(){
		
	}

	public void reset(){
		level=null;
		gamestate=Gamestate.DEFAULT;
	}

	public void click(int index){
		if (level.bombs[index]==2){
			clickAroundFlags(index);
		}
		else dig(index);
	}
	
	public void alt_click(int index){
		if (level.flags[index]==0 && !(level.bombs[index]==2))
			level.flags[index]=1;
		else level.flags[index]=0;
		if (level.bombs[index]==2){
			clickAroundFlags(index);
		}
		
		gamestate=checkWin();
	}
	
	
	public void dig(int index){
		if (level.flags[index]==1){}
		
		else if(level.bombs[index] == 0){
			level.bombs[index]=2;
			if (level.numbers[index]==0)
				reveal(index);
		}
		else if (level.bombs[index]==1 || level.bombs[index]==3){
			level.bombs[index]=3;
			gamestate=Gamestate.LOSS;
		}	
	}
	

	public Gamestate checkWin(){
		for (int i=0;i<level.bombs.length;i++){
			if ((level.bombs[i]==1)^(level.flags[i]==1)||level.bombs[i]==0){
				return gamestate;
			}
		}
		return Gamestate.WIN;
	}
	
	public void reveal(int index){
		testBounds(index, 1, 1);
		testBounds(index, 1, 0);
		testBounds(index, 1, -1);
		
		testBounds(index, 0, 1);
		testBounds(index, 0, -1);
		
		testBounds(index, -1, 1);
		testBounds(index, -1, 0);
		testBounds(index, -1, -1);
	}


	public void testBounds(int index, int dx, int dy){

		int n = util.offsetIndex(index, dx, dy, width);
		int nx = util.toPoint(index, width).x+dx;
		int ny = util.toPoint(index, width).y+dy;
		
		if (nx>=0 && nx<width && ny>=0 && ny<height)
			dig(n);
	}
	
	public int getFlag(int index, int dx, int dy){
		
		int n = util.offsetIndex(index, dx, dy, width);
		int nx = util.toPoint(index, width).x+dx;
		int ny = util.toPoint(index, width).y+dy;
		
		if (nx>=0 && nx<width && ny>=0 && ny<height)
			if (level.flags[n]==1)
				return 1;
		return 0;
	}
	
	public boolean clickAroundFlags(int index){
		int flag_count = 0;
		flag_count+=getFlag(index, 1, 1);
		flag_count+=getFlag(index, 1, 0);
		flag_count+=getFlag(index, 1, -1);
		
		flag_count+=getFlag(index, 0, 1);
		flag_count+=getFlag(index, 0, -1);
		
		flag_count+=getFlag(index, -1, 1);
		flag_count+=getFlag(index, -1, 0);
		flag_count+=getFlag(index, -1, -1);
		if (flag_count == level.numbers[index]){
			reveal(index);
			return true;
		}
		else return false;
	}
	
	public void clickAroundFlagsAll(){
		for (int i=0;i<level.bombs.length;i++)
			if (level.bombs[i]==2){
				clickAroundFlags(i);
			}
	}
	
}





/*
if (level.numbers[util.toIndex(util.toPoint(index, width).x+1, util.toPoint(index, width).y+1, width)]==0 &&
		level.bombs[util.toIndex(util.toPoint(index, width).x+1, util.toPoint(index, width).y+1, width)]==0)
	reveal(util.toIndex(util.toPoint(index, width).x+1, util.toPoint(index, width).y+1, width));

if (level.numbers[util.toIndex(util.toPoint(index, width).x+1, util.toPoint(index, width).y-1, width)]==0 &&
		level.bombs[util.toIndex(util.toPoint(index, width).x+1, util.toPoint(index, width).y-1, width)]==0)
	reveal(util.toIndex(util.toPoint(index, width).x+1, util.toPoint(index, width).y-1, width));

if (level.numbers[util.toIndex(util.toPoint(index, width).x-1, util.toPoint(index, width).y+1, width)]==0 &&
		level.bombs[util.toIndex(util.toPoint(index, width).x-1, util.toPoint(index, width).y+1, width)]==0)
	reveal(util.toIndex(util.toPoint(index, width).x-1, util.toPoint(index, width).y+1, width));

if (level.numbers[util.toIndex(util.toPoint(index, width).x-1, util.toPoint(index, width).y-1, width)]==0 &&
		level.bombs[util.toIndex(util.toPoint(index, width).x-1, util.toPoint(index, width).y-1, width)]==0)
	reveal(util.toIndex(util.toPoint(index, width).x-1, util.toPoint(index, width).y-1, width));


click(util.toIndex(util.toPoint(index, width).x+1, util.toPoint(index, width).y+1, width));
click(util.toIndex(util.toPoint(index, width).x+1, util.toPoint(index, width).y-1, width));
click(util.toIndex(util.toPoint(index, width).x-1, util.toPoint(index, width).y+1, width));
click(util.toIndex(util.toPoint(index, width).x-1, util.toPoint(index, width).y-1, width));*/