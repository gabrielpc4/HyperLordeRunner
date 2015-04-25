package baseclasses;


import game.Scenario;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class StaticSprite extends GameObject
{
	private BufferedImage img;
		
	public StaticSprite()
	{
		this(0,0);		
	}
	
	public StaticSprite(Rectangle rect)
	{		
		this(rect.getX(), rect.getY());
		this.setBounds(rect);
	}
	
	public StaticSprite(double startX, double startY)
	{		
		super(startX,startY, game.Scenario.NONE);
		
	}
	
	public StaticSprite(double startX, double startY, int OBJECT_TYPE)
	{		
		super(startX,startY, OBJECT_TYPE);
		img = null;
	}
	
	public StaticSprite(int OBJECT_TYPE, double startX, double startY)
	{				
		this(startX, startY, OBJECT_TYPE);
		
		switch (OBJECT_TYPE)
		{
			case (Scenario.GOLD_PILE):
			{				
				loadImg("gold.png");
			}break;
			case (Scenario.LADDER):
			{
				loadImg("ladder.png");
			}break;
			case (game.Scenario.POLE):
			{
				loadImg("pole.png");
			}break;
			default:
			{
				//System.err.println("Error: Informed to load image of the class " + this.getClass().getName() + " of object type: " + OBJECT_TYPE + " and it does not exists");				//
			}break;
		}
				
	}
	
	@Override
	public BufferedImage getImg()
	{
		return img;	
	}
	
	@Override
	public void loadImg(String imgFileName)
	{
		String imgWithPath = "images/" + imgFileName;		
		try
		{
			img = ImageIO.read(new File(imgWithPath));
		}
		catch (IOException e)
		{
			System.err.println("Failed to load image: " + imgFileName);
			System.exit(0);
		}
		this.setSize(img.getWidth(), img.getHeight());
	}	
}
