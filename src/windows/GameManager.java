/**
 * 
 */
package windows;

import game.Enemy;
import game.Player;
import game.Scenario;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import baseclasses.GameObject;
import baseclasses.StaticSprite;
import listeners.MyKeyListener;


@SuppressWarnings("serial")
public class GameManager extends JPanel implements Runnable
{	
	private Camera camera;
	public static enum Direction {UP,RIGHT,DOWN,LEFT,HORIZONTAL,VERTICAL, NONE};	
	private ArrayList<GameObject> enemies;
	ArrayList<GameObject> gameObjects;
	private Player player;
	private MyKeyListener myKeyListener;
	private Scenario scenario;
	private Thread thread;
	@SuppressWarnings("unused")
	private RealWorldWindowFrame realWorldWindowFrame;
	public static final int THREAD_SLEEP_TIME = 3;
	public static final int ENEMY = 4;
	public static final int PLAYER = 5;
	
	
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
				
		player = new Player(scenario.getBlock(93));
		enemies = new ArrayList<GameObject>();
		enemies.add(new Enemy(scenario.getBlock(134)));
		enemies.add(new Enemy(scenario.getBlock(110)));		
					
		gameObjects = new ArrayList<GameObject>();
		for (ArrayList<GameObject> arrayOfObjects : scenario.getAllScenarioObjects())
		{
			for (GameObject scenarioObject : arrayOfObjects)
			{
				gameObjects.add(scenarioObject);
			}		
		}				
		
		camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT, player, gameObjects, scenario.getSize());		
		this.add(camera);
		
		realWorldWindowFrame = new RealWorldWindowFrame(player, gameObjects, scenario, camera, this);
	
		myKeyListener = new MyKeyListener(player, this);
		addKeyListener(myKeyListener);
	
	
		this.setFocusable(true);		
	}	
	
	public void checkCollisions()
	{
		player.setObjectUnder(new StaticSprite(game.Scenario.NONE, 0.0 ,0.0));
		player.setObjectCollidingWith(new StaticSprite(game.Scenario.NONE, 0.0 ,0.0));
		
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
					if( player.getBottomY() > block.getY())
					{			
						if (player.isMovingDirection(Direction.DOWN))
						{
							player.setSpeedY(0);
							player.setY(block.getY() - player.getHeight());
						}						
					}
					// Player at the bottom of the block
					if (player.getY() > block.getBottomY())
					{
						if (player.isMovingDirection(Direction.UP))
						{
							player.setSpeedY(0);
							player.setY(block.getBottomY());
						}
					}
					// Player at the right of the block
					if (player.getX() <= block.getRightX())
					{
						if (player.getX() <= block.getRightX())
						{
							if (player.isMovingDirection(Direction.LEFT))
							{				
								player.setSpeedX(0);
								player.setX(block.getRightX());
							}
						}
					}
					// Player at the left of the block
					if (player.getRightX() >= block.getX())
					{
						if (player.getRightX() > block.getX())
						{							
							if (player.isMovingDirection(Direction.RIGHT))
							{
								player.setSpeedX(0);
								player.setX(block.getX() - player.getWidth());
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
		
		if (player.isClimbing() &&  (player.getObjectCollidingWith().getType() != game.Scenario.LADDERS))
		{
			if (player.getObjectUnder().getType() != game.Scenario.LADDERS)
			player.stopClimbing();
		}
	}
	
	private void physics()
	{
		if (player.getObjectUnder().getType() == game.Scenario.NONE)
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
				Thread.sleep(THREAD_SLEEP_TIME);
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

	public boolean areColliding(Rectangle object1, Rectangle object2) 
	{
		return (object1.intersects(object2));		
	}
}
