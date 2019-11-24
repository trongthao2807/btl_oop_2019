package game;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

abstract public class Effect
{
	protected int posX, posY;
	protected double velocityX, velocityY;
	protected double ageInSeconds;
	protected Image picture;
	
	public void interact(Game game, double deltaTime)
	{
		// Thời gian tăng
		ageInSeconds += deltaTime;
		
		// Đặt vị trí mới
		posX += velocityX*deltaTime;
		posY += velocityY*deltaTime;
		

		List<Enemy> enemies = game.enemies;
		for(Enemy e: new LinkedList<Enemy>(enemies))
		{

			double dx, dy, dist;
			

			dx = e.position.getToaDo().x - posX;
			dy = e.position.getToaDo().y - posY;
			

			dist = Math.sqrt((dx*dx) + (dy*dy));
			
			

			if(dist < 25)
			{

				game.enemies.remove(e);
				game.scoreCounter += 10;
				game.killsCounter += 1;
			}
		}
	}

	public void draw(Graphics g)
	{
		// Vẽ hiệu ứng
		g.drawImage(picture, posX, posY, null);


	}
	
	public boolean isDone()
	{
		return ageInSeconds >= 1;
	}
}
