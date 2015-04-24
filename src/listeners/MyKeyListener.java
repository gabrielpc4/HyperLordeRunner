package listeners;

import game.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import windows.GameManager.Direction;

public class MyKeyListener implements KeyListener {
	private Player player;
	
	public MyKeyListener(Player player) 
	{
		this.player = player;
	}

	public void keyPressed(KeyEvent e)
	{						
		if (e.getKeyCode()==(KeyEvent.VK_LEFT))
		{			
			player.move(Direction.LEFT);
		}
		if (e.getKeyCode()==(KeyEvent.VK_RIGHT))
		{		
			player.move(Direction.RIGHT);
		}
		if (e.getKeyCode()==(KeyEvent.VK_UP))
		{		
			player.move(Direction.UP);
		}
		if (e.getKeyCode()==(KeyEvent.VK_DOWN))
		{		
			player.move(Direction.DOWN);
		}		
	}

	public void keyReleased(KeyEvent e)
	{		
		if (e.getKeyCode()==(KeyEvent.VK_LEFT) || (e.getKeyCode()==(KeyEvent.VK_RIGHT)))
		{			
			player.stopMoving(Direction.HORIZONTAL);
		}
		else if (e.getKeyCode()==(KeyEvent.VK_UP) || e.getKeyCode()==(KeyEvent.VK_DOWN))
		{
			player.stopMoving(Direction.VERTICAL);
		}			
	}

	public void keyTyped(KeyEvent e)
	{
		
	}
}
