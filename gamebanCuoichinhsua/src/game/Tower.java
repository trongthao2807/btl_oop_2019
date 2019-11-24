package game;

import java.awt.Graphics;
import java.awt.Image;
abstract public class Tower 
{
	/* instance variables */
	protected ToaDo position;
	protected Image tower;
	protected int anchorX;
	protected int anchorY;
	protected double timeSinceLastFire;
	
	public void draw(Graphics g)
	{

		g.drawImage(tower, position.getX() + anchorX, position.getY() + anchorY, null);

	}
	public void setPosition(ToaDo c)
	{
		position = c;
	}
	
	abstract void interact(Game game, double deltaTime);
}





