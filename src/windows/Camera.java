package windows;

import game.GameManager;
import game.Player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

import baseclasses.Constants;
import baseclasses.GameObject;

@SuppressWarnings("serial")
public class Camera extends JPanel implements Runnable
{
	Point cameraPositon;
	double playerDrawPosX;
	double playerDrawPosY;	
	boolean followingPlayerX;
	boolean followingPlayerY;
	private Player player;
	private ArrayList<GameObject> gameObjects;
	private Dimension scenarioDimension;	
	private Thread thread;
	
	public Camera()
	{
		super();
		setSize(800, 600);
	}
	
	public Camera(int w, int h, Player player,ArrayList<GameObject> gameObjects, Dimension scenarioDimension)
	{
		super();
		setSize(w, h);
		this.player = player;
		this.gameObjects = gameObjects;
		this.scenarioDimension = scenarioDimension;
		cameraPositon = new Point(0,0);							
		updateCameraPosition();				
		thread = new Thread(this);		
		thread.start();		
	}
				
	private void calculatePlayerDrawPos()
	{			
		if (player.getCenterX() > (this.getWidth()/2.0) && player.getCenterX() < (scenarioDimension.getWidth() - (this.getWidth()/2.0)))
		{
			followingPlayerX = true;
		}
		else if(cameraPositon.getX() <= 0 || cameraPositon.getX() + this.getWidth() >= scenarioDimension.getWidth())
		{
			followingPlayerX = false;
		}
						
		if (player.getCenterY() > (this.getHeight()/2.0) && player.getCenterY() < (scenarioDimension.getHeight() - (this.getHeight()/2.0)))
		{
			followingPlayerY = true;
		}
		else if(cameraPositon.getY() <= 0 || cameraPositon.getY() + this.getHeight() >= scenarioDimension.getHeight())
		{
			followingPlayerY = false;
		}						

		if(followingPlayerX)
		{
			playerDrawPosX = this.getPanelCenterX() - (player.getWidth()/2.0);			
		}
		else
		{
			if (cameraPositon.getX() + this.getWidth() < scenarioDimension.getWidth())
			{
				playerDrawPosX = player.getX();
			}
			else
			{				
				playerDrawPosX = player.getX() - cameraPositon.getX();
			}						
		}
		
		if (followingPlayerY)
		{
			playerDrawPosY = this.getPanelCenterY() - (player.getHeight()/2.0);					
		}
		else
		{
			// The the bottonY real position of the camera is not out of canvas 
			if (cameraPositon.getY() + this.getHeight() < scenarioDimension.getHeight())
			{
				playerDrawPosY = player.getY();		
			}
			else // if it is
			{	
				playerDrawPosY = player.getY() - cameraPositon.getY();				
			}		
		}	
	}
	
	private void drawObjects(Graphics g)
	{			
		for (GameObject currentObject : gameObjects)
		{	
			double diffX = cameraPositon.getX();
			double diffY = cameraPositon.getY();				
			currentObject.draw(currentObject.getX() - diffX, currentObject.getY() - diffY, g);
		}			
	}

	
	public Point getCameraPositon()
	{
		return cameraPositon;
	}
		
	private int getCenterX()
	{
		return (int) (cameraPositon.getX() + (this.getWidth()/2.0));
	}
	
	private int getCenterY()
	{
		return (int) (cameraPositon.getY() + (this.getHeight()/2.0));
	}
	
	private int getPanelCenterX()
	{
		return (int) (this.getX() + (this.getWidth()/2.0));
	}
	
	private int getPanelCenterY()
	{
		return (int) (this.getY() + (this.getHeight()/2.0));
	}	
	
	@Override
	public void paintComponent(Graphics g) 
	{		
		super.paintComponent(g);
		setBackground(Color.white);
		
		drawObjects(g);
		
		calculatePlayerDrawPos();	
		player.draw(playerDrawPosX, playerDrawPosY, g);
		
		updateCameraPosition();				
	}

	public void run()
	{		
		while(true)
		{					
			try
			{
				Thread.sleep(Constants.THREAD_SLEEP_TIME);
				repaint();
			}
			catch (InterruptedException e)
			{				
				e.printStackTrace();
			}
		}		
	}
	
	private void updateCameraPosition() 
	{
		int diffX = (int)(this.getCenterX() - player.getCenterX());
		
		if (cameraPositon.getX() - diffX < 0)
		{
			cameraPositon.setLocation(0,cameraPositon.getY());
		}
		else if ((cameraPositon.getX() + this.getWidth()) - diffX > scenarioDimension.getWidth())
		{
			cameraPositon.setLocation(scenarioDimension.getWidth() - this.getWidth(),cameraPositon.getY());
		}
		else
		{
			if (Math.abs(diffX) > 0)
			{
				cameraPositon.translate(-diffX,0);
			}
		}
			
				
		int diffY = (int)(this.getCenterY() - player.getCenterY());
		
		if (cameraPositon.getY() - diffY < 0)
		{
			cameraPositon.setLocation(cameraPositon.getX(), 0);
		}
		else if ((cameraPositon.getY() + this.getHeight()) - diffY > scenarioDimension.getHeight())
		{
			cameraPositon.setLocation(cameraPositon.getX(), scenarioDimension.getHeight() - this.getHeight());
		}
		else
		{
			if (Math.abs(diffY) > 0)
			{
				cameraPositon.translate(0, -diffY);				
			}	
		}		
	}
}
