package game;

import baseclasses.StaticSprite;

@SuppressWarnings("serial")
public class GoldPile extends StaticSprite
{

	public GoldPile()
	{
		this(0,0);
	}

	public GoldPile(double startX, double startY)
	{
		super("gold.png", startX, startY);	
	}	
}
