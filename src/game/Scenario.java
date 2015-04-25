package game;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import baseclasses.GameObject;
import baseclasses.StaticSprite;
import windows.MainWindowApplet;
import windows.GameManager.Direction;

@SuppressWarnings("serial")
public class Scenario extends Dimension
{
	ArrayList<ArrayList<GameObject>> scenarioObjects;
	public static final int NONE		= -1;
	public static final int BLOCKS 		= 0;
	public static final int GOLD_PILES	= 1;
	public static final int LADDERS		= 2;
	public static final int POLE		= 3;
		
	public ArrayList<GameObject> debug = new ArrayList<GameObject>();
	
	public Scenario()
	{
		super(MainWindowApplet.WINDOW_WIDTH, MainWindowApplet.WINDOW_HEIGHT);
	
		scenarioObjects = new ArrayList<ArrayList<GameObject>>();
		for( int i = BLOCKS; i <= POLE; i++)
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
		addGoldPile(getBlock(82));
		addGoldPile(getBlock(89));
		addGoldPile(getBlock(111));
		addGoldPile(getBlock(113),4, Direction.LEFT, 0 , 1);
		addGoldPile(getBlock(121));
		addGoldPile(getBlock(121));
		addGoldPile(getBlock(124));
		addGoldPile(getBlock(128));
		addGoldPile(getBlock(142));
		
		// LADDERS
		addLadder(getBlock(61), 2);
		addLadder(getBlock(100), 4);
		addLadder(getBlock(91), 4);
		addLadder(getBlock(112), 2);
		addLadder(getBlock(58), 3);
		
		addPole(getLadder(getBlock(4), Direction.RIGHT),16,Direction.RIGHT);
		
		updateScenarioDimensions();		
	}
	
	private void addBlock(boolean destructible, GameObject referenceObject,  int howMany, Direction direction, int firstGap, int nextGaps)
	{
		addObject(BLOCKS, referenceObject, howMany, direction, firstGap, nextGaps);
		
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
	
	private void addGoldPile(GameObject referenceObject, int howMany, Direction direction, int firstGap, int nextGap)
	{
		addObject(GOLD_PILES, referenceObject, 1, Direction.UP, firstGap, nextGap);
		referenceObject = getLastAddedGoldPile();
		addObject(GOLD_PILES, referenceObject, howMany - 1, direction, nextGap, nextGap);
	}		
	
	private void addLadder(Block referenceBlock, int height)
	{
		addObject(LADDERS, referenceBlock, height, Direction.UP);
	}	
	
	private void addPole(GameObject referenceObject, int length, Direction direction)
	{
		addObject(POLE, referenceObject, length, direction);
	}
	
	private GameObject addObject(int OBJECT_TYPE, double startX, double startY)
	{
		GameObject newObject = null;
		if (OBJECT_TYPE == BLOCKS)
		{
			newObject = new Block(startX,startY); 
			getBlocks().add(newObject);
		}
		else 
		{
			newObject = new StaticSprite(OBJECT_TYPE, startX, startY);
			if (OBJECT_TYPE == GOLD_PILES)			
			{				
				getGoldPiles().add(newObject);
			}
			else if (OBJECT_TYPE == LADDERS)
			{			
				getLadders().add(newObject);
			}
			else if (OBJECT_TYPE == POLE)
			{
				getPoles().add(newObject);
			}
			else
			{
				System.err.println("Trying to add a object of type " + OBJECT_TYPE + " but it's not included in the method: addObject(int OBJECT_TYPE, double startX, double startY) of the class: " + this.getClass().getName() );
			}
		}
		
		return newObject;
	}		
		
	@SuppressWarnings("unused")
	private void addObject(int OBJECT_TYPE, double startX, double startY, int howMany, Direction direction)
	{
		GameObject firstObject = addObject(OBJECT_TYPE, startX, startY);
		addObject(OBJECT_TYPE, firstObject, howMany, direction);
	}
	
	private void addObject(int OBJECT_TYPE, GameObject referenceObject, int howMany, Direction direction)
	{
		addObject(OBJECT_TYPE, referenceObject, howMany, direction, 0, 0);
	}

	private void addObject(int OBJECT_TYPE, GameObject referenceObject, int howMany, Direction direction, int firstGap, int nextGaps)
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
				for (ArrayList<GameObject> arrayOfObjects :  getAllScenarioObjects())
				{
					for (GameObject scenarioObject :  arrayOfObjects)
					{
						scenarioObject.translate(0, -y);
					}					
				}				
				y = 0;
			}
			
			if (x < 0)
			{
				for (ArrayList<GameObject> arrayOfObjects :  getAllScenarioObjects())
				{
					for (GameObject scenarioObject :  arrayOfObjects)
					{
						scenarioObject.translate(-x, 0);
					}					
				}	
				x = 0;
			}
			
			newObject = addObject(OBJECT_TYPE, x, y);			
			referenceObject = newObject;
		}			
	}
	
	public ArrayList<ArrayList<GameObject>> getAllScenarioObjects()
	{
		return scenarioObjects;		
	}				
			
	public Block getBlock(int blockNumber)
	{
		if((getBlocks().size() - 1) < 0)
		{
			System.err.println("Error: The blocks array at " + this.getClass().getName() + " is empty");
			System.exit(0);
		}
		else if (blockNumber > getBlocks().size() - 1)
		{
			System.err.println("Warning: Attempt to get block number: " + blockNumber +  " at class "+ this.getClass().getName() 
					+ ", while there is only " + getBlocks().size() 
					+ " blocks(s) avaliable (max num allowed: " + (getBlocks().size() - 1) + ")." );
			blockNumber = getBlocks().size() - 1;			
		}
		return (Block) getBlocks().get(blockNumber);
	}
	
	public ArrayList<GameObject> getBlocks()
	{
		return scenarioObjects.get(BLOCKS);
	}
	
	public GameObject getGoldPile(int goldPileNumber)
	{
		if((getGoldPiles().size() - 1) < 0)
		{
			System.err.println("Error: The gold pile array at " + this.getClass().getName() + " is empty");
			System.exit(0);
		}
		else if (goldPileNumber > getGoldPiles().size() - 1)
		{
			System.err.println("Warning: Attempt to get gold pile number: " + goldPileNumber +  " at class "+ this.getClass().getName() 
					+ ", while there is only " + getBlocks().size() 
					+ " gold pile(s) avaliable (max num allowed: " + (getGoldPiles().size() - 1) + ")." );
			goldPileNumber = getGoldPiles().size() - 1;		
		}
		return  getGoldPiles().get(goldPileNumber);
	}
	
	public ArrayList<GameObject> getGoldPiles()
	{
		return scenarioObjects.get(GOLD_PILES);
	}
	
	public GameObject getLadder(int ladderNumber)
	{
		if((getLadders().size() - 1) < 0)
		{
			System.err.println("Error: The ladders array at " + this.getClass().getName() + " is empty");
			System.exit(0);
		}
		if (ladderNumber > getLadders().size() - 1)
		{
			System.err.println("Warning: Attempt to get ladder number: " + ladderNumber +  " at class "+ this.getClass().getName() 
					+ ", while there is only " + getBlocks().size() 
					+ " gold pile(s) avaliable (max num allowed: " + (getLadders().size() - 1) + ")." );
			ladderNumber = getLadders().size() - 1;	
		}
		return  getGoldPiles().get(ladderNumber);
	}
	
	public GameObject getLadder(GameObject referenceObject, Direction direction)
	{
		Rectangle collisionRect = new Rectangle(referenceObject.getRect());
		
		String directionString = "";
						
		switch (direction)
		{
		case UP:
		{
			collisionRect.translate(0,(int) -referenceObject.getHeight());				
			
			for (GameObject currentLadder : getLadders())
			{
				if (currentLadder.intersects(collisionRect))
				{
					return currentLadder;
				}
			}
			directionString = "top";
		}break;
		case RIGHT:
		{
			collisionRect.translate((int)referenceObject.getWidth(),0);
			for (GameObject currentLadder : getLadders())
			{
				if (currentLadder.intersects(collisionRect))
				{
					return currentLadder;
				}
			}
			directionString = "right";
		}break;
		case DOWN:
		{
			collisionRect.translate(0,(int) referenceObject.getHeight());
			for (GameObject currentLadder : getLadders())
			{
				if (currentLadder.intersects(collisionRect))
				{
					return currentLadder;
				}
			}
			directionString = "down";
		}break;
		case LEFT:
		{
			collisionRect.translate(-(int)referenceObject.getWidth(),0);
			for (GameObject currentLadder : getLadders())
			{
				if (currentLadder.intersects(collisionRect))
				{
					return currentLadder;
				}
			}
			directionString = "left";
		}break;		
		default:
		{
			System.err.println("Warning: Incorrect direction infomed when looking for ladders");
		}break;
		}
		System.err.print("Warning: No ladders are on the " + directionString + " of the refered object");
		return (new StaticSprite());		
	}
	
	public ArrayList<GameObject> getLadders()
	{
		return scenarioObjects.get(LADDERS);
	}
	
	
	public ArrayList<GameObject> getPoles()
	{
		return scenarioObjects.get(POLE);
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
	
	public GameObject getLastAddedGoldPile()
	{
		if (getGoldPiles().size() - 1 < 0)
		{
			System.err.println("Error: The gold pile array is empty");
			System.exit(0);
		}
		return getGoldPiles().get(getGoldPiles().size() - 1);
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
