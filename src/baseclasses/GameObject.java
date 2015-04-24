package baseclasses;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public abstract class GameObject extends Rectangle
{
	public GameObject()
	{
		this(0,0);
	}
	
	public GameObject(double startX, double startY)
	{
		super(new Point((int)startX, (int)startY));
	}
	
	public void draw(double x, double y, Graphics g)
	{
		g.drawImage(this.getImg(), (int)x, (int)y, null);
	}
	public abstract void loadImg(String file);
	public abstract BufferedImage getImg();
	
	public double getRightX()
	{
		return (double) (this.getX() + this.getWidth());
	}
	
	public double getBottomY()
	{
		return (double) (this.getY() + this.getHeight());
	}
	
	public Point getPosition()
	{
		return (new Point((int)getX(),(int)getY()));
	}
}
