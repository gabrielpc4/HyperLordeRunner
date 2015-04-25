package game;

import baseclasses.Constants;

@SuppressWarnings("serial")
public class Enemy extends Player
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
		super("enemy", startX, startY, Constants.ENEMY);	
	}
}
