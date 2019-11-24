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
		ageInSeconds += deltaTime;
		
		// Đặt vị trí mới của Hiệu ứng
		posX += velocityX*deltaTime;
		posY += velocityY*deltaTime;


		List<Enemy> enemies = game.enemies;
		for(Enemy e: new LinkedList<Enemy>(enemies))
		{

			double dx, dy, dist;	//thay đổi trong x, y và tổng khoảng cách
			
			//
			//tính toán của thay đổi trong x và vị trí y
			dx = e.position.getToaDo().x - posX; // x vị trí của kẻ thù - hiệu ứng
			dy = e.position.getToaDo().y - posY; //y vị trí của kẻ thù - hiệu ứng
			
			//
			//khoảng cách
			dist = Math.sqrt((dx*dx) + (dy*dy));

			if(dist < 25)
			{

				game.enemies.remove(e);
				game.scoreCounter += 5;
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
