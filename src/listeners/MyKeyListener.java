package listeners;

import game.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import windows.GameManager;
import windows.GameManager.Direction;

public class MyKeyListener implements KeyListener {
	private Player player;
	private GameManager gameManager;
	
	public MyKeyListener(Player player, GameManager gameManager) 
	{
		this.player = player;
		this.gameManager = gameManager;
	}

	public void keyPressed(KeyEvent e)
	{						
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{			
			if (player.isFalling() == false)
			{
				player.move(Direction.LEFT);
			}		
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{		
			// Prevents the player from pressing right while holding up or down
			if (player.isFalling() == false)
			{
				player.move(Direction.RIGHT);
			}				
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{				
			if (player.isClimbing())
			{				
				player.climbLadder(Direction.UP);							
			}
			else
			{
				if (player.getObjectCollidingWith().getType() == game.Scenario.LADDERS)
				{					
					player.climbLadder(Direction.UP);					
				}
			}					
		}		
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{	
			if (player.isClimbing())
			{
				player.climbLadder(Direction.DOWN);
			}
			else
			{					
				if (player.getObjectUnder().getType() == game.Scenario.LADDERS)
				{					
					if (player.getX() > player.getObjectUnder().getX())
					{							
						player.climbLadder(Direction.DOWN);							
					}
				}								
			}					
		}		
	}

	public void keyReleased(KeyEvent e)
	{		
		if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_RIGHT))
		{			
			player.stopMoving(Direction.HORIZONTAL);
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP || (e.getKeyCode() == KeyEvent.VK_DOWN))
		{
			player.stopMoving(Direction.VERTICAL);
			if (player.isClimbing())
			{
				player.pauseClimbing();
			}
		}			
	}

	public void keyTyped(KeyEvent e)
	{
		
	}
}
