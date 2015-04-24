package game;

@SuppressWarnings("serial")
public class Block extends AnimatedSprite
{
	public Block()
	{
		this(0,0);
	}

	public Block(double startX, double startY)
	{
		super("block", startX, startY);		
	}	
}
