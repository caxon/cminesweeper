package escape.gui;

import escape.gui.Bitmap;

import java.awt.Color;

import escape.Art;

public class Bitmap {

	public final int width;
	public final int height;
	public final int[] pixels;
	private static final String chars = "" + //
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}" + //
			"abcdefghijklmnopqrstuvwxyz_               " + //
			"0123456789+-=*:;ÖÅÄå                      " + //
			"";
	
	private static final String ints = "0123456789.";

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	public static Bitmap emptyBitmap(){
		Bitmap empty =  new Bitmap(0,0);
		return empty;
	}
	public Bitmap(int width, int height, int color){
		this(width,height);
		for (int i=0;i<pixels.length;i++)
			pixels[i]=color;
	}

	public void draw(Bitmap bitmap, int xOffs, int yOffs) {
		for (int y = 0; y < bitmap.height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < bitmap.width; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;

				int src = bitmap.pixels[x + y * bitmap.width];
				if ((src & 0xffffff) == 0xff00ff)
					continue;
				pixels[xPix + yPix * width] = src;
			}
		}
	}

	public void flipDraw(Bitmap bitmap, int xOffs, int yOffs) {
		for (int y = 0; y < bitmap.height; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < bitmap.width; x++) {
				int xPix = xOffs + bitmap.width - x - 1;
				if (xPix < 0 || xPix >= width) continue;

				int src = bitmap.pixels[x + y * bitmap.width];
				if (src>>6 == 0)
					continue;
				pixels[xPix + yPix * width] = src;
			}
		}
	}

	public void draw(Bitmap bitmap, int xOffs, int yOffs, int xo, int yo, int w, int h, int col) {
		for (int y = 0; y < h; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < w; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;

				int src = bitmap.pixels[(x + xo) + (y + yo) * bitmap.width];
				if (src!=0xffff00ff){//if (src >= 0) {
					pixels[xPix + yPix * width] = col;//src * col;
				}
			}
		}
	}

	public void scaleDraw(Bitmap bitmap, int scale, int xOffs, int yOffs, int xo, int yo, int w, int h, int col) {
		for (int y = 0; y < h * scale; y++) {
			int yPix = y + yOffs;
			if (yPix < 0 || yPix >= height) continue;

			for (int x = 0; x < w * scale; x++) {
				int xPix = x + xOffs;
				if (xPix < 0 || xPix >= width) continue;

				int src = bitmap.pixels[(x / scale + xo) + (y / scale + yo) * bitmap.width];
				if (src >= 0) {
					pixels[xPix + yPix * width] = src * col;
				}
			}
		}
	}

	public void draw(String string, int x, int y, int col) {
		for (int i = 0; i < string.length(); i++) {
			int ch = chars.indexOf(string.charAt(i));
			if (ch < 0) continue;

			int xx = ch % 42;
			int yy = ch / 42;
			draw(Art.font, x + i * 6, y, xx * 6, yy * 8, 5, 8, col);
		}
	}
	
	public void drawInts(String string, int x, int y, int col){
		for (int i =0; i< string.length();i++){
			int ch = ints.indexOf(string.charAt(i));
			if (ch<0) continue;
			
//			draw(Art.int_font,i*4,0);
			draw(Art.int_font_large,x+i*8,y, ch*8,0,8,12,col);
		}
	}
	
	public void drawSmallInt(String string, int x, int y, int col){
		for (int i =0; i< string.length();i++){
			int ch = ints.indexOf(string.charAt(i));
			if (ch<0) continue;
			
//			draw(Art.int_font,i*4,0);
			draw(Art.int_font_small,x+i*4,y, ch*4,0,4,5,col);
		}
	}
	
	public void fill(int x0, int y0, int x1, int y1, int color) {
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				pixels[x + y * width] = color;
			}
		}
	}
	
	public void fill(int color){
		for (int i=0;i<pixels.length;i++)
			pixels[i]=color;
	}
	
}
