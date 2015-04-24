package game;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class HyperLordeRunner extends JApplet
{	
	public static GameManager game;
	
	@Override
	public void init()
	{
		super.init();
		game = new GameManager();
		setSize(game.getSize().width, game.getSize().height);
		add(game);
	}
		
	@Override
	public void start() 
	{	
		super.start();
		game.start();
	}
}