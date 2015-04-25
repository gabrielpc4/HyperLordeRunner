package game;



import java.awt.Rectangle;

import baseclasses.AnimatedSprite;
import baseclasses.Constants;
import baseclasses.Constants.Direction;
import baseclasses.GameObject;
import baseclasses.StaticSprite;

@SuppressWarnings("serial")
public class Player extends AnimatedSprite
{
	private boolean climbing;	
	private Direction climbingDirection;
	private boolean falling;
	private boolean grabbingPole;
	private boolean makingHole;
	private Direction lookingDirection;
	private GameObject objectCollidingWith;
	private GameObject objectUnder;
	public static final int PLAYER_X_SPEED = 1;
	public static final int PLAYER_Y_SPEED = 1;
	private final int RECT_UNDER_HEIGHT = 2;
	private boolean releasePole;
	
	public Player()
	{
		this(0,0);
	}

	public Player(double startX, double startY)
	{
		this("player", startX, startY, Constants.PLAYER);		
	}
	
	public Player(String imgsFolderName, double startX, double startY, int OBJECT_TYPE)
	{
		super(imgsFolderName, startX, startY, OBJECT_TYPE);
		climbing  			= false;	
		climbingDirection 	= Direction.NONE;
		falling 			= false;
		grabbingPole 		= false;
		makingHole			= false;
		lookingDirection 	= Direction.RIGHT;					
		objectCollidingWith = new StaticSprite(Scenario.NONE,0.0,0.0);
		objectUnder 		= new StaticSprite(Scenario.NONE,0.0,0.0);
		releasePole 		= true;
	}
	
	public Player(Block block)
	{
		this(block.getX(),block.getY() - block.getHeight());
		objectUnder = block;
	}
	
	public void climbLadder(Direction direction) 
	{			
		climbing = true;
		climbingDirection = direction;
	}
	
	public void fall(boolean state) 
	{				
		if (climbing == false)
		{
			falling = state;
			if (falling)
			{
				setSpeedY(PLAYER_Y_SPEED);
			}
			else
			{
				setSpeedY(0);
			}
		}		
	}
		
	public GameObject getObjectUnder()
	{
		return objectUnder;
	}

	public GameObject getObjectCollidingWith()
	{
		return objectCollidingWith;
	}
	
	public Rectangle getRectUnder()
	{
		return (new Rectangle((int)getX(), (int)getBottomY(), (int)getWidth(), RECT_UNDER_HEIGHT));
	}
	
	public void grabPole(boolean state) 
	{
		grabbingPole = state;			
	}
	
	public boolean isClimbing() 
	{	
		return climbing;
	}
		
	public boolean isGrabbingPole()
	{
		return grabbingPole;
	}
	
	public boolean isFalling()
	{
		return falling;
	}

	public Direction isLookingTo() 
	{		
		return lookingDirection;
	}
	
	public boolean isMovingDirection(Direction direction)
	{
		switch (direction)
		{
			case UP:
			{
				if (getSpeedY() < 0)
				{
					return true;
				}
			}break;
			case RIGHT:
			{
				if (getSpeedX() > 0)
				{
					return true;
				}
			}break;
			case DOWN:
			{
				if (getSpeedY() > 0)
				{
					return true;
				}
			}break;
			case LEFT:
			{
				if (getSpeedX() < 0)
				{
					return true;
				}
			}break;
			default:
			{
				return false;
			}
		}
		return false;
	}
	
	public void makeHole(Direction direction)
	{
		makingHole = true;
		
		if (direction == Direction.LEFT)
		{

		}
		else if (direction == Direction.RIGHT)
		{
			
		}
	}
	
	
	@Override
	protected void move()
	{
		super.move();
		if (isClimbing())
		{
			if (climbingDirection == Direction.UP)
			{
				setSpeedY(-PLAYER_Y_SPEED);
			}
			else if (climbingDirection == Direction.DOWN)
			{
				setSpeedY(PLAYER_Y_SPEED);
			}
			else
			{
				setSpeedY(0);
			}	
		}
		else
		{
			if (isFalling())
			{
				setSpeedX(0);
				setSpeedY(PLAYER_Y_SPEED);
			}
			else
			{
				setSpeedY(0);			
			}
			if (releasePole && getObjectCollidingWith().getType() == Scenario.POLE)
			{
				grabbingPole = false;
			}
			else
			{
				releasePole = false;
			}
			
			if (isGrabbingPole())
			{
				if (getCenterY() > objectCollidingWith.getCenterY() + 1)
				{
					setCenterY(objectCollidingWith.getCenterY());
				}
			}
		}	
	}
	
	public void move(Direction direction)
	{
		lookingDirection = direction;
		
		switch (direction)
		{			
			case UP:
			{													
				if (isClimbing())
				{				
					climbLadder(Direction.UP);
					
				}
				else
				{
					if (getObjectCollidingWith().getType() == Scenario.LADDER)
					{					
						climbLadder(Direction.UP);					
					}
				}							
			}break;
			case RIGHT:
			{
				
				if (getSpeedY() == 0) // Prevents the player from pressing right while holding up or down
				{
					setSpeedX(PLAYER_X_SPEED);
				}	
				
			}break;
			case DOWN:
			{
				if (isClimbing())
				{
					climbLadder(Direction.DOWN);
				}
				else
				{					
					if (getObjectUnder().getType() == Scenario.LADDER)
					{					
						if (getX() > getObjectUnder().getX())
						{							
							climbLadder(Direction.DOWN);							
						}
					}
					if (isGrabbingPole())
					{
						grabPole(false);
						releasePole = true;
					}
				}			
			}break;
			case LEFT:
			{
				if (getSpeedY() == 0)  // Prevents the player from pressing right while holding up or down
				{
					setSpeedX(-PLAYER_X_SPEED);
				}	
				
			}break;
			default:
			{
				setSpeed(0,0);
			}break;
		}
	}

	public void pauseClimbing() 
	{
		climbingDirection = Direction.NONE;
	}

	public void setObjectCollidingWith(GameObject object) 
	{
		objectCollidingWith = object;
	}
	
	public void setObjectUnder(GameObject object) 
	{
		objectUnder = object;
	}

	public void stopClimbing()
	{
		climbing = false;
		climbingDirection = Direction.NONE;
	}
	
	public void stopMoving(Direction direction)
	{
		if (direction == Direction.HORIZONTAL)
		{
			setSpeedX(0);
		}
		else
		{
			if (isClimbing())
			{
				pauseClimbing();
			}			
		}
	}	
}

