/**
 * 
 */
package game;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import windows.Camera;
import windows.RealWorldWindowFrame;
import baseclasses.Constants;
import baseclasses.Constants.Direction;
import baseclasses.GameObject;
import baseclasses.StaticSprite;
import listeners.MyKeyListener;


@SuppressWarnings("serial")
public class GameManager extends JPanel implements Runnable
{	
	private Camera camera;	
	private ArrayList<GameObject> enemies;
	private ArrayList<GameObject> gameObjects;
	private Player player;
	private MyKeyListener myKeyListener;
	private Scenario scenario;
	private Thread thread;
	@SuppressWarnings("unused")
	private RealWorldWindowFrame realWorldWindowFrame;
	private StaticSprite emptyObject;
	
	public GameManager()
	{
		this(800,600);
	}
	
	public GameManager(int WINDOW_WIDTH, int WINDOW_HEIGHT) 
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
					
		scenario = new Scenario();
				
		player = new Player(scenario.getBlock(95));
		enemies = new ArrayList<GameObject>();
		enemies.add(new Enemy(scenario.getBlock(136)));
		enemies.add(new Enemy(scenario.getBlock(112)));		
					
		gameObjects = new ArrayList<GameObject>();
		for (ArrayList<GameObject> arrayOfObjects : scenario.getAllScenarioObjects())
		{
			for (GameObject scenarioObject : arrayOfObjects)
			{
				gameObjects.add(scenarioObject);
			}		
		}
		gameObjects.add(enemies.get(0));
		gameObjects.add(enemies.get(1));
		
		camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT, player, gameObjects, scenario.getSize());		
		this.add(camera);
		
		realWorldWindowFrame = new RealWorldWindowFrame(player, gameObjects, scenario, camera);
	
		myKeyListener = new MyKeyListener(player);
		addKeyListener(myKeyListener);
		
		emptyObject = new StaticSprite(game.Scenario.NONE, 0.0 ,0.0);
		
		this.setFocusable(true);		
	}		
	
	public boolean areColliding(Rectangle object1, Rectangle object2) 
	{
		return (object1.intersects(object2));		
	}
	
	public void checkCollisions()
	{
		player.setObjectUnder(emptyObject);
		player.setObjectCollidingWith(emptyObject);
		
		
		for (GameObject pole: scenario.getPoles())
		{		
			if (areColliding(player, pole))
			{
				player.setObjectCollidingWith(pole);
				
				if (player.getCenterY() > pole.getCenterY() && player.getX() > pole.getX())
				{
					player.grabPole(true);					
				}
			}					
		}
		
		if (player.getObjectCollidingWith().getType() != Scenario.POLE)
		{
			player.grabPole(false);
		}
				
		for (GameObject ladder: scenario.getLadders())
		{
			if (areColliding(player.getRectUnder(), ladder))
			{
				player.setObjectUnder(ladder);
			}		
			
			if (areColliding(player, ladder))
			{
				player.setObjectCollidingWith(ladder);
			}					
		}	

		for (GameObject block : scenario.getBlocks())
		{
			if (areColliding(player.getRectUnder(), block))
			{
				player.setObjectUnder(block);
			}
			
			if (areColliding(player, block))
			{
				player.setObjectCollidingWith(block);
				// Player is at the top of the block or is a the  bottom
				if (player.getBottomY() >= block.getY() || player.getY() >= block.getBottomY())
				{
					// Player at the top of the block
					if( player.getBottomY() > block.getY() - 1)
					{			
						if (player.isMovingDirection(Direction.DOWN))
						{
							player.setSpeedY(0);
							player.setY(block.getY() - player.getHeight() - 1);
						}						
					}
					// Player at the bottom of the block
					if (player.getY() > block.getBottomY() + 1)
					{
						if (player.isMovingDirection(Direction.UP))
						{
							player.setSpeedY(0);
							player.setY(block.getBottomY() + 1);
						}
					}
					// Player at the right of the block
					if (player.getX() <= block.getRightX())
					{
						if (player.getX() <= block.getRightX() + 1)
						{
							if (player.isMovingDirection(Direction.LEFT))
							{				
								player.setSpeedX(0);
								player.setX(block.getRightX() + 1);
							}
						}
					}
					// Player at the left of the block
					if (player.getRightX() >= block.getX())
					{
						if (player.getRightX() > block.getX() - 1)
						{							
							if (player.isMovingDirection(Direction.RIGHT))
							{
								player.setSpeedX(0);
								player.setX(block.getX() - player.getWidth() - 1);
							}
						}
					}						
				}
			}
		}	
	}
	
	public Scenario getScenario()
	{
		return scenario;
	}		
	
	private void moveAll()
	{		
		player.move();	
		
		if (player.isClimbing())
		{
			if (player.getObjectCollidingWith().getType() != Scenario.LADDER
				&& player.getObjectUnder().getType() != Scenario.LADDER)			
			{
				player.stopClimbing();
			}		
		}
	}
	
	private void physics()
	{
		if (player.getObjectUnder().getType() == game.Scenario.NONE && player.isGrabbingPole() == false)
		{
			player.fall(true);
		}
		else
		{
			player.fall(false);			
		}			
	}
	
	
	public void run()
	{		
		while(true)
		{					
			try
			{
				Thread.sleep(Constants.THREAD_SLEEP_TIME);
				moveAll();
				physics();
				checkCollisions();
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
