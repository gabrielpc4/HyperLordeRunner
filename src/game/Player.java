package game;

import windows.GameManager.Direction;
import baseclasses.AnimatedSprite;

@SuppressWarnings("serial")
public class Player extends AnimatedSprite
{
	public static final int PLAYER_X_SPEED = 5;
	public static final int PLAYER_Y_SPEED = 5;
	public Player()
	{
		this(0,0);
	}

	public Player(int startX, int startY)
	{
		super("player", startX, startY);		
	}
	
	public Player(Block block)
	{
		this(0,0);
		
		double startX = block.getX();
		double startY = block.getY() - block.getHeight();	
		
		this.translate((int)startX, (int)startY);				
	}
	
	public void move(Direction direction)
	{
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
}
