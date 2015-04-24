package game;

@SuppressWarnings("serial")
public class Block extends AnimatedSprite
{

	public Block()
	{
		this(0,0);
	}

	public Block(int startX, int startY)
	{
		super("block", startX, startY);		
	}	
}
