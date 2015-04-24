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
	private ArrayList<ArrayList<GameObject>> gameObjects;	
	private Thread thread;	
	private Camera camera;
	private MyKeyListener myKeyListener;
	
	public RealWorldWindowPanel(Player player, ArrayList<ArrayList<GameObject>> gameObjects, Scenario scenario, Camera camera)
	{		
		super();
		this.player = player;
		this.gameObjects = gameObjects;
		this.camera = camera;
		setSize((int)scenario.getWidth(), (int)scenario.getHeight());				
		thread = new Thread(this);
		thread.start();
		myKeyListener = new MyKeyListener(player);
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
		
		for (ArrayList<GameObject> arrayOfObjects : gameObjects)
		{
			for (GameObject currentObject : arrayOfObjects)
			{
				currentObject.draw(currentObject.getX(), currentObject.getY(), g);
			}
		}
		player.draw(player.getX(), player.getY(), g);
		
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
