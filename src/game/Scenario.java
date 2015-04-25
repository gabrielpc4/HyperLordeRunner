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
	ArrayList<ArrayList<GameObject>> scenarioObjects;
	public static final int BLOCKS 		= 0;
	public static final int GOLD_PILES	= 1;
	public static final int LADDERS		= 2;
	
	public Scenario()
	{
		super(MainWindowApplet.WINDOW_WIDTH, MainWindowApplet.WINDOW_HEIGHT);
	
		scenarioObjects = new ArrayList<ArrayList<GameObject>>();
		for( int i = BLOCKS; i <= LADDERS; i++)
		{
			scenarioObjects.add(new ArrayList<GameObject>());
		}		
		
		// Borders
		addBlock(false, new Point(0,0), 17, Direction.DOWN);
		addBlock(false, getLastAddedBlock(), 19, Direction.RIGHT);
		addBlock(false, getLastAddedBlock(), 17, Direction.UP);
		
		// Ground
		addBlock(true, getBlock(16), 18, Direction.RIGHT);
		
		// Bottom right blocks
		addBlock(true, getLastAddedBlock(), 3, Direction.UP);
		addBlock(true, getLastAddedBlock(), 4, Direction.LEFT);
		addBlock(true, getLastAddedBlock(), 1, Direction.DOWN);
		addBlock(true, getLastAddedBlock(), 1, Direction.RIGHT);
		addBlock(true, getBlock(72), 3, Direction.LEFT);
		
		// Bottom left blocks
		addBlock(true, getBlock(54), 6, Direction.UP);
		addBlock(true, getLastAddedBlock(), 5, Direction.RIGHT);
		addBlock(true, getLastAddedBlock(), 4, Direction.DOWN);
		addBlock(true, getBlock(60), 2, Direction.UP);
		addBlock(true, getBlock(85), 3, Direction.RIGHT);
		addBlock(true, getBlock(86), 3, Direction.RIGHT);
		
		// Upper left blocks
		addBlock(true, getBlock(90), 4, Direction.UP);
		addBlock(true, getBlock(108), 1, Direction.LEFT);
		addBlock(true, getBlock(110), 1, Direction.LEFT);
		
		
		// Middle right blocks
		addBlock(false, getBlock(42), 4, Direction.LEFT, 0, 1);		
		addBlock(true, getBlock(42), 4, Direction.LEFT, 1, 1);
		addBlock(true, getBlock(94), 1, Direction.RIGHT, 2, 0);
		addBlock(true, getLastAddedBlock(), 1, Direction.RIGHT);
		
		// Upper right blocks
		addBlock(true, getBlock(45), 8, Direction.LEFT);
		addBlock(true, getBlock(46), 1, Direction.LEFT);
		addBlock(true, getLastAddedBlock(), 3, Direction.LEFT, 1, 0);
		addBlock(true, getLastAddedBlock(), 1, Direction.LEFT, 1, 0);
		addBlock(true, getBlock(47), 4, Direction.LEFT);
		addBlock(true, getLastAddedBlock(), 1, Direction.LEFT, 1, 0);
		
		// Floating top center blocks
		addBlock(true, getBlock(135), 3, Direction.LEFT, 2, 0);
		
		
		// GOLD PILES		
		addGoldPile(getBlock(55),3, Direction.RIGHT);
		addGoldPile(getBlock(59));
		addGoldPile(getBlock(104),2, Direction.RIGHT);
		addGoldPile(getBlock(89));
		addGoldPile(getBlock(111));
		addGoldPile(getBlock(113),4, Direction.LEFT, 0 , 1);
		addGoldPile(getBlock(121));
		addGoldPile(getBlock(121));
		addGoldPile(getBlock(124));
		addGoldPile(getBlock(128));
		addGoldPile(getBlock(142));
		updateScenarioDimensions();		
	}
	
	private void addBlock(boolean destructible, GameObject referenceObject,  int howMany, Direction direction, int firstGap, int nextGaps)
	{
		addObject(Block.class, referenceObject, howMany, direction, firstGap, nextGaps);
		
		for (int i = 0; i < howMany ; i++)
		{
			getBlock(getLastAddedBlock().getNumber() - i).setDestructible(destructible);
		}		
	}
	
	private void addBlock(boolean destructible, Point startPoint, int howMany, Direction direction)
	{
		addBlock(destructible, startPoint.getX(), startPoint.getY(), howMany, direction);
	}
	
	private void addBlock(boolean destructible, double startX, double startY, int howMany, Direction direction)
	{
		scenarioObjects.get(BLOCKS).add(new Block(startX, startY, false));
		if (howMany > 1)
		{
			addBlock(destructible, getLastAddedBlock(), howMany, direction);
		}
	}	
	
	private void addBlock(boolean destructible, GameObject referenceObject,  int howMany, Direction direction)
	{
		addBlock(destructible, referenceObject, howMany, direction, 0, 0);
	}	
		
	private void addGoldPile(Block referenceBlock)
	{
		addGoldPile(referenceBlock, 1, Direction.UP);
	}
	
	private void addGoldPile(Block referenceBlock, int howMany, Direction direction)
	{
		addGoldPile(referenceBlock, howMany, direction, 0, 0);
	}
	
	private void addGoldPile(GameObject referenceBlock, int howMany, Direction direction, int firstGap, int nextGap)
	{
		addObject(GoldPile.class, referenceBlock, 1, Direction.UP, firstGap, nextGap);
		referenceBlock = getLastAddedGoldPile();
		addObject(GoldPile.class, referenceBlock, howMany - 1, direction, nextGap, nextGap);
	}
	
	private GameObject addObject(Class<? extends GameObject> objectClass, double startX, double startY)
	{
		GameObject newObject = null;
		if (objectClass == Block.class)
		{
			newObject = new Block(startX,startY); 
			getBlocks().add(newObject);
		}
		else
		{
			newObject = new GoldPile(startX, startY);
			getGoldPiles().add(newObject);
		}
		return newObject;
	}		
		
	@SuppressWarnings("unused")
	private void addObject(Class<? extends GameObject> objectClass, double startX, double startY, int howMany, Direction direction)
	{
		GameObject firstObject = addObject(objectClass, startX, startY);
		addObject(objectClass, firstObject, howMany, direction);
	}
	
	private void addObject(Class<? extends GameObject> objectClass, GameObject referenceObject, int howMany, Direction direction)
	{
		addObject(objectClass, referenceObject, howMany, direction, 0, 0);
	}

	private void addObject(Class<? extends GameObject> objectClass, GameObject referenceObject, int howMany, Direction direction, int firstGap, int nextGaps)
	{	
		int x = (int)referenceObject.getX();
		int y = (int)referenceObject.getY();
		
		GameObject newObject = null;
		
		for (int i = 0; i < howMany; i++)
		{
			int gap = 0;
			if (i == 0)
			{
				gap = firstGap;
			}
			else
			{
				gap = nextGaps;
			}
			switch (direction)
			{
				case UP:
				{
					y -= (1 + gap) * referenceObject.getHeight();
				}break;
				case RIGHT:
				{
					x += (1 + gap) * referenceObject.getWidth();
				}break;
				case DOWN:
				{
					y += (1 + gap) * referenceObject.getHeight();
				}break;
				case LEFT:
				{
					x -= (1 + gap) * referenceObject.getWidth();
				}break;
				default:
				{
					
				}break;
			}
			
			if (y < 0)
			{
				for (GameObject block :  getBlocks())
				{
					block.translate(0, -y);
				}
				for (GameObject goldPile : getGoldPiles())
				{
					goldPile.translate(0, -y);
				}
				y = 0;
			}
			
			if (x < 0)
			{
				for (GameObject block : getBlocks())
				{			
					block.translate(-x, 0);
				}
				for (GameObject goldPile : getGoldPiles())
				{
					goldPile.translate(-x, 0);
				}
				x = 0;
			}
			
			newObject = addObject(objectClass, x, y);			
			referenceObject = newObject;
		}			
	}
	
	public ArrayList<ArrayList<GameObject>> getAllScenarioObjects()
	{
		return scenarioObjects;		
	}		
	
	public ArrayList<GameObject> getScenarioObject(int OBJECT_TYPE)
	{
		if (OBJECT_TYPE > scenarioObjects.size())
		{
			System.err.println("Warning: Attempt to get scenario object type: " + OBJECT_TYPE +  " at class "+ this.getClass().getName() 
					+ ", while there is only " + getBlocks().size() 
					+ " types(s) avaliable (max num allowed: " + (scenarioObjects.size() - 1) + ")." );
			OBJECT_TYPE = scenarioObjects.size() - 1;		
		}
		return scenarioObjects.get(OBJECT_TYPE);		
	}
	
	
			
	public Block getBlock(int blockNumber)
	{
		if (blockNumber > getBlocks().size() - 1)
		{
			System.err.println("Warning: Attempt to get block number: " + blockNumber +  " at class "+ this.getClass().getName() 
					+ ", while there is only " + getBlocks().size() 
					+ " blocks(s) avaliable (max num allowed: " + (getBlocks().size() - 1) + ")." );
			blockNumber = getBlocks().size() - 1;
			if(blockNumber < 0)
			{
				System.exit(0);
			}
		}
		return (Block) getBlocks().get(blockNumber);
	}
	
	public ArrayList<GameObject> getBlocks()
	{
		return scenarioObjects.get(BLOCKS);
	}
	
	public Block getGoldPile(int goldPileNumber)
	{
		if (goldPileNumber > getGoldPiles().size() - 1)
		{
			System.err.println("Warning: Attempt to get gold pile number: " + goldPileNumber +  " at class "+ this.getClass().getName() 
					+ ", while there is only " + getBlocks().size() 
					+ " gold pile(s) avaliable (max num allowed: " + (getGoldPiles().size() - 1) + ")." );
			goldPileNumber = getGoldPiles().size() - 1;
			if(goldPileNumber < 0)
			{
				System.exit(0);
			}
		}
		return (Block) getGoldPiles().get(goldPileNumber);
	}
	
	public Block getLastAddedBlock()
	{
		if (getBlocks().size() - 1 < 0)
		{
			System.err.println("Error: The blocks array is empty");
			System.exit(0);
		}
		return (Block) getBlocks().get(getBlocks().size() - 1);
	}
	
	public GoldPile getLastAddedGoldPile()
	{
		if (getGoldPiles().size() - 1 < 0)
		{
			System.err.println("Error: The gold pile array is empty");
			System.exit(0);
		}
		return (GoldPile) getGoldPiles().get(getGoldPiles().size() - 1);
	}
	
	public ArrayList<GameObject> getGoldPiles()
	{
		return scenarioObjects.get(GOLD_PILES);
	}
	
	private void updateScenarioDimensions() 
	{
		if (getBlocks().size() > 0)
		{
			double smallestX = java.lang.Double.POSITIVE_INFINITY;
			double smallestY = java.lang.Double.POSITIVE_INFINITY;
			double biggestX = java.lang.Double.NEGATIVE_INFINITY;
			double biggestY = java.lang.Double.NEGATIVE_INFINITY;
			for (GameObject currentBlock : getBlocks())
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
			double width = ((biggestX + getBlocks().get(0).getWidth())- smallestX) ;
			double height = ((biggestY + getBlocks().get(0).getHeight()) - smallestY) ;
			this.setSize((int)width , (int)height);			
		
		}
	}		
}
