package game;

import java.awt.Dimension;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Scenario extends Dimension
{
	ArrayList<GameObject> blocks;
	ArrayList<GameObject> goldPiles;
	
	public Scenario()
	{
		if(this.getSize().getWidth() < 1900 || this.getSize().getHeight() < 720)
		{
			setSize(1900, 720);
		}
		blocks = new ArrayList<GameObject>();		
		goldPiles = new ArrayList<GameObject>();
		addObject(new Block(0,getSize().height - 56));
		for (int i = 0; i < 10; i++)
		{
			addObject(new Block(getBlock(i).getRightX(), getBlock(i).getY()));
		}
		
		double x = getBlock(4).getX();
		double y = getBlock(4).getY() - getBlock(4).getHeight();		

		for (int j = 0; j < 10; j++)
		{			
			addObject(new Block(x,y));
			y -= getBlock(0).getHeight();
		}
		addObject(new GoldPile((int)blocks.get(0).getRightX(),57));
		
	}
	
	public void addObject(GameObject object)
	{	
		if (object.getClass() == Block.class)
		{
			blocks.add(object);
		}
		else
		{
			goldPiles.add(object);
		}
		
		if (object.getRightX() > this.getWidth())
		{
			this.setSize(object.getRightX(), this.getHeight());
		}
		
		if (object.getBottomY() > this.getHeight())
		{
			this.setSize(this.getWidth(), object.getBottomY());
		}
	}
			
	public GameObject getBlock(int blockNumber)
	{
		return blocks.get(blockNumber);
	}
	
	public ArrayList<GameObject> getBlocks()
	{
		return blocks;
	}
	
	public GameObject getGoldPile(int goldPileNumber)
	{
		return goldPiles.get(goldPileNumber);
	}
	
	public ArrayList<GameObject> getGoldPiles()
	{
		return goldPiles;
	}
}
