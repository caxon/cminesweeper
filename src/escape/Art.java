package escape;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import escape.gui.Bitmap;

public class Art {
	
	public static Bitmap one = loadBitmap("/tex/icons/one.png");
	public static Bitmap two = loadBitmap("/tex/icons/two.png");
	public static Bitmap three = loadBitmap("/tex/icons/three.png");
	public static Bitmap four = loadBitmap("/tex/icons/four.png");
	public static Bitmap five = loadBitmap("/tex/icons/five.png");
	public static Bitmap six = loadBitmap("/tex/icons/six.png");
	public static Bitmap seven = loadBitmap("/tex/icons/seven.png");
	public static Bitmap eight = loadBitmap("/tex/icons/eight.png");
	
	public static Bitmap mine = loadBitmap("/tex/icons/mine.png");
	public static Bitmap tile = loadBitmap("/tex/icons/tile.png");
	public static Bitmap pressed_tile = loadBitmap("/tex/icons/pressed_tile.png");
	public static Bitmap smile = loadBitmap("/tex/icons/smile.png");
	public static Bitmap flag = loadBitmap("/tex/icons/flag_2.png");
	public static Bitmap flag_error = loadBitmap("/tex/icons/flag_error.png");
	public static Bitmap red_tile = loadBitmap("/tex/icons/red_tile.png");
	public static Bitmap game_over = loadBitmap("/tex/icons/game_over.png");
	public static Bitmap game_over_2x = loadBitmap("/tex/icons/game_over_2x.png");

	public static Bitmap you_win = loadBitmap("/tex/icons/you_win.png");
	public static Bitmap you_win_2x = loadBitmap("/tex/icons/you_win_2x.png");
	
	public static Bitmap font = loadBitmap("/tex/gui/font.png");
	public static Bitmap int_font_small = loadBitmap("/tex/gui/int_font_small.png");
	public static Bitmap int_font_med = loadBitmap("/tex/gui/int_font_med.png");
	public static Bitmap int_font_large = loadBitmap("/tex/gui/int_font_large.png");
	
	public static Bitmap empty = Bitmap.emptyBitmap();
	public static Bitmap black16x16 = new Bitmap(16, 16, 0xff000000);
	
	public static Bitmap loadBitmap(String fileName) {
		try {
			BufferedImage img = ImageIO.read(Art.class.getResource(fileName));

			int w = img.getWidth();
			int h = img.getHeight();

			Bitmap result = new Bitmap(w, h);
			img.getRGB(0, 0, w, h, result.pixels, 0, w);
			for (int i = 0; i < result.pixels.length; i++) {
				int col = result.pixels[i];
				if (col>>24==0)
						col =0xffff00ff;
				result.pixels[i] = col;
			}
			
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Bitmap getNumber(int num){
		if (num == 1) return Art.one;
		if (num == 2) return Art.two;
		if (num == 3) return Art.three;
		if (num == 4) return Art.four;
		if (num == 5) return Art.five;
		if (num == 6) return Art.six;
		if (num == 7) return Art.seven;
		if (num == 8) return Art.eight;
		
		else return null;
	}
	
	public static int getCol(int c) {
		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = (c) & 0xff;

		r = r * 0x55 / 0xff;
		g = g * 0x55 / 0xff;
		b = b * 0x55 / 0xff;

		return r << 16 | g << 8 | b;
	}
	
	public static int getA(int c){
		return (c>>24)&0xff;
	}
	public static int getR(int c){
		return (c>>16)&0xff;
	}
	public static int getG(int c){
		return (c>>8)&0xff;
	}
	public static int getB(int c){
		return c&0xff;
	}
	public static int getCol(int a, int r, int g, int b){
		return a<<24|r << 16 | g << 8 | b; 
	}
	
	public static Bitmap tint(Bitmap original, double tint){
		Bitmap result = new Bitmap(original.width,original.height);
		for (int i=0;i<original.pixels.length;i++){
			int col=original.pixels[i];
			int r=(int)(Art.getR(col)*tint);
			int g=(int)(Art.getG(col)*tint);
			int b=(int)(Art.getB(col)*tint);
			result.pixels[i]=Art.getCol(0xff,r,g,b);
		}
		return result;
	}
	
	public static String printHex(int n) {
	    // call toUpperCase() if that's required
	    return String.format("0x%8s", Integer.toHexString(n)).replace(' ', '0');
	}
}