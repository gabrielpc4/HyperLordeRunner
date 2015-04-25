package game;

import baseclasses.StaticSprite;

@SuppressWarnings("serial")
public class Pole extends StaticSprite
{
	public Pole() 
	{
		this(0, 0);
	}

	public Pole(double startX, double startY) 
	{
		super("ladder.png", startX, startY);		
	}
	
	public Pole(Block block)
	{
		this(block.getX(),block.getY() - block.getHeight());				
	}
}
