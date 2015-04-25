package baseclasses;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public abstract class GameObject extends Rectangle
{
	private int type;
	
	public GameObject()
	{
		this(0,0);
	}
	
	public GameObject(double startX, double startY)
	{
		this(startX,startY, game.Scenario.NONE);
	}
	
	public GameObject(double startX, double startY, int OBJECT_TYPE)
	{
		super(new Point((int)startX, (int)startY));
		this.setType(OBJECT_TYPE);
	}
	
	public void draw(double x, double y, Graphics g)
	{
		if (this.getImg() != null)
		{
			g.drawImage(this.getImg(), (int)x, (int)y, null);
		}
		else
		{			
			g.fillRect((int)x, (int)y, (int)this.getWidth(), (int) this.getHeight());
		}
	}
	
	public void draw(Graphics g)
	{		
		draw(this.getX(), this.getY(), g);
	}
	
	public GameObject(GameObject object)
	{
		this(object.getX(),object.getY() - object.getHeight());			
	}
	
	public abstract void loadImg(String file);
	public abstract BufferedImage getImg();
	
	public Rectangle getRect()
	{
		return (new Rectangle((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight()));
	}
	
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

	public int getType() 
	{
		return type;
	}

	public void setType(int type) 
	{
		this.type = type;
	}
}
