package game;

import baseclasses.StaticSprite;

@SuppressWarnings("serial")
public class Ladder extends StaticSprite
{
	public Ladder() 
	{
		this(0, 0);
	}

	public Ladder(double startX, double startY) 
	{
		super("ladder.png", startX, startY);		
	}
	
	public Ladder(Block block)
	{
		this(block.getX(),block.getY() - block.getHeight());				
	}
}
