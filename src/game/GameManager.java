/**
 * 
 */
package game;

import game.Player.Direction;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JApplet;


@SuppressWarnings("serial")
public class GameManager extends JApplet implements Runnable, KeyListener
{
	private Scenario scenario;
	private Thread thread;
	private Camera camera;
	private Player player;
	private ArrayList<GameObject> enemies;
	ArrayList<ArrayList<GameObject>> gameObjects;
	
	// Double buffering	
	private Image offImage;
	private Graphics offGraphics;
	
	public GameManager() 
	{

	}

	/* 
	 * @see java.applet.Applet#init()
	 */
	@Override
	public void init()
	{	
		super.init();	
		setSize(1900, 720);
		addKeyListener(this);
		this.setFocusable(true);
		scenario = new Scenario();
		camera = new Camera(0,0, 800, 600);
		player = new Player();
		enemies = new ArrayList<GameObject>();
		enemies.add(new Enemy());
		enemies.add(new Enemy());	
		
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
		camera.move(player);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
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

	/* 
	 * @see java.applet.Applet#start()
	 */
	@Override
	public void start()
	{
		super.start();
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void update(Graphics g)
	{			
		if (offImage == null)
		{
			offImage = createImage(this.getSize().width, this.getSize().height);
			offGraphics = offImage.getGraphics();
		}
		 offGraphics.setColor(getBackground());
	     offGraphics.fillRect(0, 0, this.getSize().width, this.getSize().height);	     
	     
	     offGraphics.setColor(getForeground());
	     g.drawImage(offImage, 0, 0,this);
	}

}
