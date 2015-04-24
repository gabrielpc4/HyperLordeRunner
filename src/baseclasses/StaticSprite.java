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
		this(startX, startY);		
		loadImg(imgFileName);
	}
	
	public StaticSprite(double startX, double startY)
	{		
		super(startX,startY);
		img = null;
	}
	
	@Override
	public BufferedImage getImg()
	{
		return img;	
	}
	
	@Override
	public void loadImg(String imgFileName)
	{
		String imgWithPath = "Game/images/" + imgFileName;		
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
