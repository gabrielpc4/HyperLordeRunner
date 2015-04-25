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
		this(0,0);
		
		double startX = block.getX();
		double startY = block.getY() - block.getHeight();	
		
		this.translate((int)startX, (int)startY);				
	}

	public Enemy(int startX, int startY)
	{
		super("enemy", startX, startY);	
	}
}
