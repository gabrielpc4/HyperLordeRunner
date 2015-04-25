package baseclasses;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class AnimatedSprite extends GameObject
{
	private boolean animate;
	private TimerTask animateTask;
	private int animationFrame;
	private static final int ANIMATION_REFRESH_TIME = 100;	
	private ArrayList<BufferedImage> imgs;
	private Point speed;
	private Timer timer;
		
	public AnimatedSprite()
	{
		this(0,0, game.Scenario.NONE);
	}
	
	public AnimatedSprite(String imgsFolderName, double startX, double startY, int OBJECT_TYPE)
	{
		this(startX, startY, OBJECT_TYPE);
		loadImg(imgsFolderName);		
	}

	public AnimatedSprite(double startX, double startY, int OBJECT_TYPE)
	{
		super(startX, startY, OBJECT_TYPE);		
		animate			= true;
		animationFrame 	= 0;
		imgs 			= null;
		speed 			= new Point(0,0);
		timer 			= new Timer();
		animateTask 	= new TimerTask()
		{			
			@Override
			public void run()
			{		
				animate();
			}
		};
		timer.schedule(animateTask, 0, ANIMATION_REFRESH_TIME);
	}
	
	public void animate()
	{				
		if (imgs != null)
		{
			if (animate)
			{
				if (animationFrame < imgs.size() - 1)
				{
					animationFrame++;
				}
				else
				{
					animationFrame = 0;
				}
			}			
		}		
	}
	
	public boolean canAnimate()
	{
		return animate;
	}
	
	public void freezeAnimation()
	{
		animate = false;
	}
	
	public int getAnimationFrame()
	{
		return animationFrame;
	}
			
	@Override
	public BufferedImage getImg()
	{
		if (animationFrame > imgs.size() - 1)	
		{		
			System.err.println("Warning: Attempt to get animation frame " + animationFrame +  " of class "+ this.getClass().getName() 
					+ ", while there is only " + imgs.size() 
					+ " image(s) avaliable (max num allowed: " + (imgs.size() - 1) + ")." );
			animationFrame = imgs.size() - 1;
		}
		return imgs.get(animationFrame);
	}
	
	public Point getSpeed()
	{
		return speed;
	}
	
	public int getSpeedX()
	{
		return (int) speed.getX();
	}
	
	public int getSpeedY()
	{
		return (int) speed.getY();
	}
	
	@Override
	public void loadImg(String imgsFolderName)
	{
		imgs = new ArrayList<BufferedImage>();
		
		if (imgsFolderName.endsWith("/") == false)
		{
			imgsFolderName += "/";
		}
		
		String imgsFolderPath = "images/" + imgsFolderName;
		
		File folder = new File(imgsFolderPath);
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles == null)
		{
			System.err.println("No such folder exists: " + imgsFolderPath);
			System.exit(0);
		}
		else if (listOfFiles.length == 0)
		{
			System.err.println("No Files in: " + imgsFolderPath);
			System.exit(0);
		}
		else
		{			
			for (File file : listOfFiles) 
			{				
			    if (file.isFile()) 
			    {
			    	BufferedImage bufferImg = null;
			    	boolean loaded = true;
					try
					{
						bufferImg = ImageIO.read(file);
					}
					catch (IOException e)
					{
						loaded = false;								
						e.printStackTrace();
					}
					finally
					{
						if (loaded && bufferImg != null)
						{							
							imgs.add(bufferImg);
							this.setSize(imgs.get(animationFrame).getWidth(), imgs.get(animationFrame).getHeight());		
						}
						else
						{
							System.err.println("Failed to load image: " + file.getName());
							System.exit(0);
						}								
					}		        
			    }
			    else
			    {
			    	System.err.println(file.getName() + " at " +  imgsFolderPath + " is not a file.");
					System.exit(0);
			    }
			}		
		}		
	}
			
	protected void move()
	{
		this.translate(this.getSpeedX(), this.getSpeedY());
	}
	
	public void setAnimationFrame(int number)
	{
		if (number <= imgs.size() - 1)
		{
			animationFrame = number;
		}
		else
		{
			animationFrame = imgs.size() - 1;
			System.err.println("Warning: Attempt to set animation frame of class "+ this.getClass().getName() + " to " + number 
					+ ", while there is only " + imgs.size() 
					+ " image(s) avaliable (max num allowed: " + (imgs.size() - 1) + ")." );
		}		
	}
	
	public void setSpeed(double x, double y)
	{
		speed.setLocation(x, y);
	}
	
	public void setSpeedX(double x)
	{
		speed.setLocation(x, speed.getY());
	}
	
	public void setSpeedY(double y)
	{
		speed.setLocation(speed.getX(), y);
	}
	
	public void unfreezeAnimation()
	{
		animate = true;
	}
}
