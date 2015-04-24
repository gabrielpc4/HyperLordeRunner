package windows;

import game.Player;
import game.Scenario;

import java.util.ArrayList;

import javax.swing.JFrame;

import baseclasses.GameObject;

@SuppressWarnings("serial")
public class RealWorldWindowFrame extends JFrame
{
	private RealWorldWindowPanel panel;
	
	public RealWorldWindowFrame(Player player, ArrayList<ArrayList<GameObject>> gameObjects, Scenario scenario, Camera camera)
	{	
		super("Real World Window");
		setSize(1900,920);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					
		panel = new RealWorldWindowPanel(player, gameObjects, scenario, camera);
		add(panel);		
		setVisible(true);
		toBack();
	}
}
