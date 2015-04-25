package game;

import java.awt.Rectangle;

import windows.GameManager;
import windows.GameManager.Direction;
import baseclasses.AnimatedSprite;
import baseclasses.GameObject;
import baseclasses.StaticSprite;

@SuppressWarnings("serial")
public class Player extends AnimatedSprite
{
	public static final int PLAYER_X_SPEED = 1;
	public static final int PLAYER_Y_SPEED = 1;
	private boolean climbing;	
	private Direction climbingDirection;
	private boolean falling;
	private Direction lookingDirection;
	private GameObject objectUnder;
	private GameObject objectCollidingWith;
	private final int RECT_UNDER_HEIGHT = 2;
	
	public Player()
	{
		this(0,0);
	}

	public Player(double startX, double startY)
	{
		super("player", startX, startY, GameManager.PLAYER);
		climbing  = false;	
		climbingDirection = Direction.NONE;
		lookingDirection = Direction.RIGHT;
		falling = false;
	}
	
	public Player(Block block)
	{
		this(block.getX(),block.getY() - block.getHeight());
		objectUnder = block;
		objectCollidingWith = new StaticSprite(Scenario.NONE,0.0,0.0);
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
	
	public void move(Direction direction)
	{
		lookingDirection = direction;
		
		switch (direction)
		{			
			case UP:
			{
				setSpeedY(-PLAYER_Y_SPEED);
			}break;
			case RIGHT:
			{
				setSpeedX(PLAYER_X_SPEED);
			}break;
			case DOWN:
			{
				setSpeedY(PLAYER_Y_SPEED);
			}break;
			case LEFT:
			{
				setSpeedX(-PLAYER_X_SPEED);
			}break;
			default:
			{
				setSpeed(0,0);
			}break;
		}
	}
	
	@Override
	public void move()
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
		}	
	}
	
	public void stopMoving(Direction direction)
	{
		if (direction == Direction.HORIZONTAL)
		{
			setSpeedX(0);
		}
		else
		{
			setSpeedY(0);
		}
	}

	public boolean isClimbing() 
	{	
		return climbing;
	}
	
	public void stopClimbing()
	{
		climbing = false;
		climbingDirection = Direction.NONE;
	}

	public void climbLadder(Direction direction) 
	{
		climbing = true;
		climbingDirection = direction;
	}

	public void pauseClimbing() 
	{
		climbingDirection = Direction.NONE;
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
	
	public Rectangle getRectUnder()
	{
		return (new Rectangle((int)getX(), (int)getBottomY(), (int)getWidth(), RECT_UNDER_HEIGHT));
	}
	
	public void setObjectUnder(GameObject object) 
	{
		objectUnder = object;
	}
	
	public GameObject getObjectUnder()
	{
		return objectUnder;
	}
	
	public void setObjectCollidingWith(GameObject object) 
	{
		objectCollidingWith = object;
	}
	
	public GameObject getObjectCollidingWith()
	{
		return objectCollidingWith;
	}
	
	public boolean isFalling()
	{
		return falling;
	}

	public Direction isLookingTo() 
	{		
		return lookingDirection;
	}
}
