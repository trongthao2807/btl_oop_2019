package game;

import java.awt.*;


abstract public class Enemy
{
	protected PathPosition position;
	protected Image enemy;
	protected int anchorX;
	protected int anchorY;
	protected double velocity;

	public void advance()
	{
		position.advance(1 + velocity);
	}

	public void draw(Graphics g)
	{
		//
        //Vẽ đối tượng kẻ thù
		ToaDo c = position.getToaDo();
		g.drawImage(enemy, c.x + anchorX, c.y + anchorY, null);
		//health bar
		g.setColor(new Color(180,50,50));
		g.fillRect(c.x+anchorX+20,c.y-anchorY-30,30,5);
	}

	public PathPosition getPosition()
	{
		return position;
	}
	
}
