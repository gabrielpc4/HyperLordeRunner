package debug;

import java.awt.Point;

public class Utils
{
	public Utils()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static void printPoint(double x, double y)
	{
		printPoint(new Point((int)x, (int)y));
	}
	
	public static void printPointln(double x, double y)
	{
		printPointln(new Point((int)x, (int)y));
	}
	
	public static void printPoint(Point p)
	{
		System.out.print("(" + (int)p.getX() + "," + (int)p.getY() + ") ");
	}
	
	public static void printPointln(Point p)
	{
		printPoint(p);
		System.out.print("\n");
	}
}

