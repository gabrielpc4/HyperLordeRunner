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
		
		addBlock(false, new Point(0,0), 17, Direction.DOWN);
		addBlock(false, getLastAddedBlock(), 19, Direction.RIGHT);
		addBlock(false, getLastAddedBlock(), 17, Direction.UP);
		updateScenarioDimensions();		
	}
	
	private void addBlock(boolean destructible, GameObject referenceObject,  int howMany, Direction direction)
	{
		addObject(Block.class, referenceObject, howMany, direction);
		getLastAddedBlock().setDestructible(destructible);
	}
	
	@SuppressWarnings("unused")
	private void addBlock(boolean destructible, Point startPoint)
	{
		addBlock(destructible, startPoint.getX(), startPoint.getY());
	}
	
	private void addBlock(boolean destructible, double startX, double startY)
	{
		addBlock(destructible, startX, startY, 1, Direction.RIGHT);
	}
	
	private void addBlock(boolean destructible, Point startPoint, int howMany, Direction direction)
	{
		addBlock(destructible, startPoint.getX(), startPoint.getY(), howMany, direction);
	}
	
	private void addBlock(boolean destructible, double startX, double startY, int howMany, Direction direction)
	{
		addObject(Block.class,startX, startY, howMany, direction);
		getLastAddedBlock().setDestructible(destructible);
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
		addObject(objectClass, firstObject, howMany, direction);
	}
	
	private void addObject(Class<? extends GameObject> objectClass, GameObject referenceObject, int howMany, Direction direction)
	{
		addObject(objectClass, referenceObject, howMany, direction, 0);
	}

	private void addObject(Class<? extends GameObject> objectClass, GameObject referenceObject, int howMany, Direction direction, int gaps)
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
			
			if (y < 0)
			{
				for (GameObject block : blocks)
				{
					block.translate(0, -y);
				}
				for (GameObject goldPile : goldPiles)
				{
					goldPile.translate(0, -y);
				}
				y = 0;
			}
			
			if (x < 0)
			{
				for (GameObject block : blocks)
				{			
					block.translate(-x, 0);
				}
				for (GameObject goldPile : goldPiles)
				{
					goldPile.translate(-x, 0);
				}
				x = 0;
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
	
	public Block getGoldPile(int goldPileNumber)
	{
		return (Block) goldPiles.get(goldPileNumber);
	}
	
	public Block getLastAddedBlock()
	{
		return (Block) blocks.get(blocks.size() - 1);
	}
	
	public GoldPile getLastAddedGoldPile()
	{
		return (GoldPile) goldPiles.get(blocks.size() - 1);
	}
	
	public ArrayList<GameObject> getGoldPiles()
	{
		return goldPiles;
	}
	
	private void updateScenarioDimensions() 
	{
		if (blocks.size() > 0)
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
			double width = ((biggestX + blocks.get(0).getWidth())- smallestX) ;
			double height = ((biggestY + blocks.get(0).getHeight()) - smallestY) ;
			this.setSize((int)width , (int)height);			
		
		}
	}		
}
