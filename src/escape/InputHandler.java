package escape;

import java.awt.Point;
import java.awt.event.*;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {
	public boolean[] keys = new boolean[65536];
	
	Mouse mouse = new Mouse(0,0,null);
	

	public void mouseDragged(MouseEvent arg0) {
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void mouseClicked(MouseEvent mevent) {

	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent mevent) {

		mouse.pointer.x = mevent.getX(); mouse.pointer.y=mevent.getY();
		switch(mevent.getButton()){
			case 1:
				mouse.button = Mouse.Button.LEFT;
				break;
			case 2: 
				mouse.button = Mouse.Button.MIDDLE;
				break;
			case 3:
				mouse.button = Mouse.Button.RIGHT;
				break;
			default:
				mouse.button=null;
				break;
		}
				
				
	}

	public void mouseReleased(MouseEvent arg0) {
		//mouse = null;
	}

	public void focusGained(FocusEvent arg0) {
	}

	public void focusLost(FocusEvent arg0) {
		for (int i=0; i<keys.length; i++) {
			keys[i] = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); 
		if (code>0 && code<keys.length) {
			keys[code] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode(); 
		if (code>0 && code<keys.length) {
			keys[code] = false;
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}
}