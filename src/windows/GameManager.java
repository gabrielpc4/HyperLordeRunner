/**
 * 
 */
package windows;

import game.Enemy;
import game.Player;
import game.Scenario;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import baseclasses.GameObject;
import listeners.MyKeyListener;


@SuppressWarnings("serial")
public class GameManager extends JPanel implements Runnable
{	
	private Camera camera;
	public static enum Direction {UP,RIGHT,DOWN,LEFT,HORIZONTAL,VERTICAL};
	private ArrayList<GameObject> enemies;
	ArrayList<ArrayList<GameObject>> gameObjects;
	private Player player;
	private MyKeyListener myKeyListener;
	private Scenario scenario;
	private Thread thread;
	@SuppressWarnings("unused")
	private RealWorldWindowFrame realWorldWindowFrame;
	public static final int THREAD_SLEEP_TIME = 17;
	
	
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
				
		player = new Player(300,300);
		enemies = new ArrayList<GameObject>();
		enemies.add(new Enemy(100,100));
		enemies.add(new Enemy(200,200));
					
		gameObjects = new ArrayList<ArrayList<GameObject>>();
		gameObjects.add(scenario.getBlocks());
		gameObjects.add(scenario.getGoldPiles());
		gameObjects.add(enemies);
		
		camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT, player, gameObjects, scenario.getSize());		
		this.add(camera);
		
		realWorldWindowFrame = new RealWorldWindowFrame(player, gameObjects, scenario, camera);
	
		myKeyListener = new MyKeyListener(player);
		addKeyListener(myKeyListener);
	
	
		this.setFocusable(true);
		
	}		
	
	private void moveAll()
	{		
		player.move();				
	}
	
	
	public void run()
	{		
		while(true)
		{					
			try
			{
				Thread.sleep(THREAD_SLEEP_TIME);
				moveAll();
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
