/**
 * 
 */
package game;

import game.Player.Direction;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class GameManager extends JPanel implements Runnable, KeyListener
{
	private Scenario scenario;
	private Thread thread;
	private Camera camera;
	private Player player;
	private ArrayList<GameObject> enemies;
	ArrayList<ArrayList<GameObject>> gameObjects;
	
	public GameManager() 
	{
		super();
		setSize(1900, 720);
		addKeyListener(this);
		this.setFocusable(true);
		scenario = new Scenario();
		camera = new Camera(0,0, 800, 600);
		player = new Player();
		enemies = new ArrayList<GameObject>();
		enemies.add(new Enemy(100,100));
		enemies.add(new Enemy(200,200));	
		
		gameObjects = new ArrayList<ArrayList<GameObject>>();
		gameObjects.add(scenario.getBlocks());
		gameObjects.add(scenario.getGoldPiles());
		gameObjects.add(enemies);			
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
	
	private void moveAll()
	{
		player.move();		
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{		
		super.paintComponent(g);
		setBackground(Color.white);		
		camera.draw(player, gameObjects, scenario.getSize(), g);
	}	
	
	public void run()
	{		
		while(true)
		{					
			try
			{
				Thread.sleep(17);
				moveAll();
				repaint();
			}
			catch (InterruptedException e)
			{				
				e.printStackTrace();
			}
		}		
	}
	
	public void start()
	{
		thread = new Thread(this);
		thread.start();
	}
}
