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
	
	public GameObject(GameObject object)
	{
		this(object.getX(),object.getY() - object.getHeight());			
	}	
	
	public GameObject(double startX, double startY)
	{
		this(startX,startY, game.Scenario.NONE);
	}
	
	public GameObject(double startX, double startY, int OBJECT_TYPE)
	{
		super(new Point((int)startX, (int)startY));
		this.type = OBJECT_TYPE;
	}
	
	public void draw(Graphics g)
	{		
		draw(this.getX(), this.getY(), g);
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
	
	public double getBottomY()
	{
		return (double) (this.getY() + this.getHeight());
	}
	
	public abstract BufferedImage getImg();

	public Point getPosition()
	{
		return (new Point((int)getX(),(int)getY()));
	}
	
	public Rectangle getRect()
	{
		return (new Rectangle((int)this.getX(), (int)this.getY(), (int)this.getWidth(), (int)this.getHeight()));
	}
	
	public double getRightX()
	{
		return (double) (this.getX() + this.getWidth());
	}
	
	public int getType() 
	{
		return type;
	}
	
	public abstract void loadImg(String file);
	
	public void setCenter(double x, double y)
	{
		setCenterX(x);
		setCenterY(y);
	}
	
	public void setCenterX(double x)
	{
		setX(x - (this.getWidth()/2.0));
	}
	
	public void setCenterY(double y)
	{
		setY(y - (this.getHeight()/2.0));
	}
	
	public void setX(double x)
	{
		this.setLocation((int)x,(int)this.getY());
	}
	
	public void setY(double y)
	{
		this.setLocation((int)this.getX(), (int)y);
	}
}
