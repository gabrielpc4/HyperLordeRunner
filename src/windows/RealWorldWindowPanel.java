package windows;

import game.Player;
import game.Scenario;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import baseclasses.GameObject;
import listeners.MyKeyListener;

@SuppressWarnings("serial")
public class RealWorldWindowPanel extends JPanel implements Runnable 
{
	private Player player;
	private ArrayList<GameObject> gameObjects;	
	private Thread thread;	
	private Camera camera;
	private MyKeyListener myKeyListener;	
	
	public RealWorldWindowPanel(Player player, ArrayList<GameObject> gameObjects, Scenario scenario, Camera camera, GameManager gameManager)
	{		
		super();
		this.player = player;
		this.gameObjects = gameObjects;
		this.camera = camera;		
		setSize((int)scenario.getWidth(), (int)scenario.getHeight());				
		thread = new Thread(this);
		thread.start();
		myKeyListener = new MyKeyListener(player, gameManager);
		addKeyListener(myKeyListener);	
		setFocusable(true);
		setVisible(true); 		
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{	
		super.paintComponents(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());	
		
		
		for (GameObject currentObject : gameObjects)
		{
			currentObject.draw(g);
		}
		
		player.draw(g);
		
		g.setColor(Color.red);
		g.drawRect((int)camera.getCameraPositon().getX(), (int)camera.getCameraPositon().getY(),(int) camera.getWidth(), (int)camera.getHeight());
	}
	
	public void run() 
	{
		while(true)
		{					
			try
			{
				Thread.sleep(17);				
				this.repaint();
			}
			catch (InterruptedException e)
			{				
				e.printStackTrace();
			}
		}		
	}

}
