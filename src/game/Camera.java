package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Camera extends Rectangle
{
	
	Rectangle displayRect;
	double playerDrawPosX;
	double playerDrawPosY;	
	boolean followingPlayerX;
	boolean followingPlayerY;
	
	public Camera()
	{
		this(0,0,800,600);
	}
	
	public Camera(int x, int y, int w, int h)
	{
		super(x,y,w,h);		
		displayRect = new Rectangle(0,0,w,h);
	}
	
	void draw (Player player,ArrayList<ArrayList<GameObject>> gameObjects, Dimension scenarioDimension, Graphics g)
	{	
		g.setColor(Color.red);
		g.drawRect((int)this.getX(), (int)this.getY(),(int) this.getWidth(), (int)this.getHeight());
		
		g.setColor(Color.green);
		g.drawRect((int)displayRect.getX(), (int)displayRect.getY(),(int) displayRect.getWidth(), (int)displayRect.getHeight());
		
		
		calculatePlayerDrawPos(player, scenarioDimension);				
		
		player.draw(playerDrawPosX, playerDrawPosY, g);
		player.draw(player.getX(), player.getY(), g);
	}
	
	public void calculatePlayerDrawPos(Player player, Dimension scenarioDimension)
	{	
		if (player.getX() > (this.getWidth()/2.0) && player.getRightX() < (scenarioDimension.getWidth() - (this.getWidth()/2.0)))
		{
			followingPlayerX = true;
		}
		else if(this.getX() <= 0 || this.getX() + this.getWidth() >= scenarioDimension.getWidth())
		{
			followingPlayerX = false;
		}
				
		
		if (player.getY() > (this.getHeight()/2.0) && player.getBottomY() < (scenarioDimension.getHeight() - (this.getHeight()/2.0)))
		{
			followingPlayerY = true;
		}
		else if(this.getY() <= 0 || this.getY() + this.getHeight() >= scenarioDimension.getHeight())
		{
			followingPlayerY = false;
		}				

		if(followingPlayerX)
		{
			playerDrawPosX = displayRect.getCenterX() - (player.getWidth()/2.0);
			int diffX = (int)(player.getCenterX() - this.getCenterX());
			if (Math.abs(diffX) > 0)
			{
				this.translate(diffX,0);				
			}		
		}
		else
		{
			if (player.getX() < this.getWidth())
			{
				playerDrawPosX = player.getX();
			}
			else
			{				
				playerDrawPosX = player.getX() - this.getX();
			}						
		}
		
		if (followingPlayerY)
		{
			playerDrawPosY = displayRect.getCenterY() - (player.getHeight()/2.0);
			int diffY = (int)(player.getCenterY() - this.getCenterY());
			if (Math.abs(diffY) > 0)
			{
				this.translate(0, diffY);				
			}				
		}
		else
		{
			// The the bottonY real position of the camera is not out of canvas 
			if (this.getY() + this.getHeight() < scenarioDimension.getHeight())
			{
				playerDrawPosY = player.getY();		
			}
			else // if it is
			{	
				playerDrawPosY = player.getY() - this.getY();				
			}		
		}	
	}
}
