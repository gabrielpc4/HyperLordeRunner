package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import baseclasses.AnimatedSprite;

@SuppressWarnings("serial")
public class Block extends AnimatedSprite
{
	private boolean destructible;
	private int number;
	public static int instanceCounter  = 0;
	private final boolean SHOW_NUMBERS = true;
	public Block()
	{
		this(0,0);
	}
	
	public Block(double startX, double startY)
	{
		this(startX, startY, true);			
	}	

	public Block(double startX, double startY, boolean destructible)
	{		
		super("block", startX, startY, Scenario.BLOCK);
				
		setDestructible(destructible);		
		freezeAnimation();
		
		number = instanceCounter;	
		instanceCounter++;				
	}
		
	@Override
	public void draw(double x, double y, Graphics g)
	{
		super.draw(x, y, g);
		if (SHOW_NUMBERS)
		{
			int fontSize = 14;
			g.setFont(new Font("Arial", Font.PLAIN, fontSize)); 
			g.setColor(Color.white);
			g.drawString(String.valueOf(number), (int)x + 5, (int)y + 15);
		}	
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public void setDestructible(boolean state)
	{
		destructible = state;
		if (destructible == false)
		{
			setAnimationFrame(1);
			freezeAnimation();
		}
		else
		{			
			setAnimationFrame(0);			
		}
	}
	
	public boolean isDestructible()
	{
		return destructible;
	}	
}
