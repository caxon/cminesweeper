package escape;

import java.awt.Point;

public class Mouse {

	public Point pointer;
	public Button button;
	
	public enum Button{
		LEFT, RIGHT, MIDDLE
	}
	
	public Mouse(int x, int y, Button button){
		pointer = new Point(x,y);
		this.button=button;
	}
}
