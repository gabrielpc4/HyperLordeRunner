package game;

import baseclasses.AnimatedSprite;

@SuppressWarnings("serial")
public class Block extends AnimatedSprite
{
	private boolean destructible;
	public Block()
	{
		this(0,0);
	}
	
	public Block(double startX, double startY)
	{
		this(startX, startY, false);			
	}	

	public Block(double startX, double startY, boolean destructible)
	{
		super("block", startX, startY);	
		this.destructible = destructible;
		if (destructible == false)
		{
			setAnimationFrame(1);
			freezeAnimation();
		}
	}
	
	public void setDestructible(boolean state)
	{
		destructible = state;
	}
	// destructable
	public boolean isDestructible()
	{
		return destructible;
	}
}
