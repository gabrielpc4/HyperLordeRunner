package game;

@SuppressWarnings("serial")
public class Enemy extends AnimatedSprite
{

	public Enemy()
	{
	
	}

	public Enemy(String imgsFolderPath, int startX, int startY)
	{
		super(imgsFolderPath, startX, startY);
	
	}

	public Enemy(int startX, int startY)
	{
		super(startX, startY);
	
	}

}
