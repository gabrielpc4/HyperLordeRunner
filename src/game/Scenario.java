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
		blocks = new ArrayList<GameObject>();		
		goldPiles = new ArrayList<GameObject>();
		addObject(new Block(0,0));
		addObject(new GoldPile((int)blocks.get(0).getRightX(),57));
		if(this.getSize().getWidth() < 1900 || this.getSize().getHeight() < 720)
		{
			setSize(1900, 720);
		}
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
