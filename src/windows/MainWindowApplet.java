package windows;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class MainWindowApplet extends JApplet
{	
	public static GameManager game;
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	@Override
	public void init()
	{
		super.init();		
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
		game = new GameManager(WINDOW_WIDTH, WINDOW_HEIGHT);		
		add(game);
	}
		
	@Override
	public void start() 
	{	
		super.start();
		game.start();
	}
}