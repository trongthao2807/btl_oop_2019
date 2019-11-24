package game;

import java.util.List;
public class Tru2 extends Tower
{
	public Tru2(ToaDo pos)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.tower = loader.getImage("resources/icon_tower_2.png");
		this.position = pos;
		this.anchorX = -33;
		this.anchorY = -33;
	}
	public void interact(Game game, double deltaTime)
	{
		
		List<Enemy> enemies = game.enemies; // danh sách kẻ thù mới

		timeSinceLastFire += deltaTime;
		

		if(timeSinceLastFire < 0.5)
			return;
		

		for(Enemy e: enemies)
		{	

			//giữ vị trí của kẻ thù
			ToaDo enemyPos = e.getPosition().getToaDo();

			//Tính khoảng cách của kẻ thù đến tháp
			double dx, dy, dist;	//thay đổi trong x, y và tổng khoảng cách

			dx = enemyPos.x - position.x;
			dy = enemyPos.y - position.y;
		

			dist = Math.sqrt((dx*dx) + (dy*dy));


			ToaDo pos = new ToaDo(position.x-50, position.y-50);
			

			if(dist < 140)
			{	Dan2 sunspot = new Dan2(pos, enemyPos);
				game.effects.add(sunspot);
				timeSinceLastFire = 0;
				return;
			}	
		} 
	}
}
