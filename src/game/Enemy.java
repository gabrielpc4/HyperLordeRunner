package game;

@SuppressWarnings("serial")
public class Enemy extends AnimatedSprite
{
	public Enemy()
	{		
		this(0,0);
	}

	public Enemy(int startX, int startY)
	{
		super("enemy", startX, startY);	
	}
}
