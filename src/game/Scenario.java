package game;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import baseclasses.GameObject;
import windows.MainWindowApplet;
import windows.GameManager.Direction;




@SuppressWarnings("serial")
public class Scenario extends Dimension
{
	ArrayList<GameObject> blocks;
	ArrayList<GameObject> goldPiles;	
	
	public Scenario()
	{
		super(MainWindowApplet.WINDOW_WIDTH, MainWindowApplet.WINDOW_HEIGHT);
	
		blocks = new ArrayList<GameObject>();		
		goldPiles = new ArrayList<GameObject>();
		
		addObject(Block.class, 0, 0, 33, Direction.RIGHT);
		addObject(Block.class, 15, getLastAddedBlock(), Direction.DOWN);
		addObject(Block.class, 33, getLastAddedBlock(), Direction.LEFT);
		addObject(Block.class, 14, getLastAddedBlock(), Direction.UP);
		
		addObject(GoldPile.class, 1, blocks.get(2), Direction.DOWN, 1);		
		updateScenarioDimensions();		
	}
		
	
	@SuppressWarnings("unused")
	private void addObject(GameObject object)
	{
		if (object.getClass() == Block.class)
		{
			blocks.add(object);
		}
		else
		{
			goldPiles.add(object);
		}
	}
	
	@SuppressWarnings("unused")
	private void addObject(Class<? extends GameObject> objectClass, Point startPosition)
	{
		addObject(objectClass, startPosition.getX(), startPosition.getY());
	}
	
	private GameObject addObject(Class<? extends GameObject> objectClass, double startX, double startY)
	{
		GameObject newObject = null;
		if (objectClass == Block.class)
		{
			newObject = new Block(startX,startY); 
			blocks.add(newObject);
		}
		else
		{
			newObject = new GoldPile(startX, startY);
			goldPiles.add(newObject);
		}
		return newObject;
	}		
	
	private void addObject(Class<? extends GameObject> objectClass, double startX, double startY, int howMany, Direction direction)
	{
		GameObject firstObject = addObject(objectClass, startX, startY);
		addObject(objectClass, howMany, firstObject, direction);
	}
	
	public void addObject(Class<? extends GameObject> objectClass, int howMany, GameObject referenceObject, Direction direction)
	{
		addObject(objectClass, howMany, referenceObject, direction, 0);
	}

	private void addObject(Class<? extends GameObject> objectClass, int howMany, GameObject referenceObject, Direction direction, int gaps)
	{	
		int x = (int)referenceObject.getX();
		int y = (int)referenceObject.getY();
		
		GameObject newObject = null;
		
		for (int i = 0; i < howMany; i++)
		{
			switch (direction)
			{
				case UP:
				{
					y -= (1 + gaps) * referenceObject.getHeight();
				}break;
				case RIGHT:
				{
					x += (1 + gaps) * referenceObject.getWidth();
				}break;
				case DOWN:
				{
					y += (1 + gaps) * referenceObject.getHeight();
				}break;
				case LEFT:
				{
					x -= (1 + gaps) * referenceObject.getWidth();
				}break;
				default:
				{
					
				}break;
			}
			
			newObject = addObject(objectClass, x, y);
			
			referenceObject = newObject;
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
	
	public GameObject getLastAddedBlock()
	{
		return blocks.get(blocks.size() - 1);
	}
	
	public GameObject getLastAddedGoldPile()
	{
		return goldPiles.get(blocks.size() - 1);
	}
	
	public ArrayList<GameObject> getGoldPiles()
	{
		return goldPiles;
	}
	
	private void updateScenarioDimensions() 
	{
		double smallestX = java.lang.Double.POSITIVE_INFINITY;
		double smallestY = java.lang.Double.POSITIVE_INFINITY;
		double biggestX = java.lang.Double.NEGATIVE_INFINITY;
		double biggestY = java.lang.Double.NEGATIVE_INFINITY;
		for (GameObject currentBlock : blocks)
		{
			if (currentBlock.getX() < smallestX)
			{
				smallestX = currentBlock.getX();
			}
			
			if (currentBlock.getX() >= biggestX)
			{
				biggestX = currentBlock.getX();
			}
			
			if (currentBlock.getY() < smallestY)
			{
				smallestY = currentBlock.getY();
			}
			
			if (currentBlock.getY() >= biggestY)
			{
				biggestY = currentBlock.getY();
			}
		}		
		double width = biggestX - smallestX;
		double height = biggestY - smallestY;
		this.setSize((int)width , (int)height);
	}
}
