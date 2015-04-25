package game;

import baseclasses.AnimatedSprite;

@SuppressWarnings("serial")
public class Enemy extends AnimatedSprite
{
	public Enemy()
	{		
		this(0,0);
	}
	
	public Enemy(Block block)
	{
		this(block.getX(),block.getY() - block.getHeight());				
	}

	public Enemy(double startX, double startY)
	{
		super("enemy", startX, startY);	
	}
}
