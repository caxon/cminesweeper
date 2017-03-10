package util;

import java.awt.Point;

public class util {
	static public Point toPoint(int index, int width){
		return new Point(index%width,index/width );
	}
	static public int toIndex(int x, int y, int width){
		return y*width + x;
	}
	
	static public int offsetIndex(int index, int dx, int dy, int width){
		return toIndex(toPoint(index, width).x + dx, toPoint(index, width).y + dy, width);
	}
	
	static public Integer safeOffsetIndex(int index, int dx, int dy, int width, int height){
		if (toPoint(index, width).x + dx < width && toPoint(index, width).x + dx >= 0 && 
				toPoint(index, width).y + dy < height && toPoint(index, width).y >= 0)
			return toIndex(toPoint(index, width).x + dx, toPoint(index, width).y + dy, width);
		else return null;
	}
	
	public static void log(Object obj){
		System.out.println(obj);
	}
	public double distance(Point a, Point b){
		return distance(a.x,a.y,b.x,b.y);
	}
	public double distance(int x1,int y1,int x2,int y2){
		return Math.hypot(x2-x1,y2-y1);
	}
}
