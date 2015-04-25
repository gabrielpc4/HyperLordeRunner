package baseclasses;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class StaticSprite extends GameObject
{
	BufferedImage img;
	
	public StaticSprite()
	{
		this(0,0);		
	}
	
	public StaticSprite(String imgFileName, double startX, double startY)
	{		
		
	}
	
	public StaticSprite(double startX, double startY)
	{		
		super(startX,startY);
		img = null;
	}
	
	public StaticSprite(int OBJECT_TYPE, double startX, double startY)
	{		
		
		this(startX, startY);
		switch (OBJECT_TYPE)
		{
			case (game.Scenario.GOLD_PILES):
			{
				loadImg("gold.png");
			}break;
			case (game.Scenario.LADDERS):
			{
				loadImg("ladder.png");
			}break;
			case (game.Scenario.POLE):
			{
				loadImg("pole.png");
			}break;
			default:
			{
				System.err.println("Error: Informed to load image of the class " + this.getClass().getName() + " of object type: " + OBJECT_TYPE + " and it does not exists");
				System.exit(0);
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
