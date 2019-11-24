package game;

import java.util.List;
public class Tru2 extends Tower
{
	public Tru2(Coordinate pos)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.tower = loader.getImage("resources/icon_tower_2.png");
		this.position = pos;
		this.anchorX = -50;
		this.anchorY = -50;
	}
	public void interact(Game game, double deltaTime)
	{
		
		List<Enemy> enemies = game.enemies; // danh sách kẻ thù mới
		
		//
		//theo dõi thời gian mà hiệu ứng đã tồn tại
		timeSinceLastFire += deltaTime;
		
		//
		//nếu thời gian dưới 1,5 giây, đừng tương tác
		if(timeSinceLastFire < 0.5)
			return;
		
		//
		//Cung cấp vị trí của kẻ thù trong danh sách kẻ thù
		for(Enemy e: enemies)
		{	
			//
			//giữ vị trí của kẻ thù
			Coordinate enemyPos = e.getPosition().getCoordinate();

			//Tính khoảng cách của kẻ thù đến tháp
			double dx, dy, dist;	//thay đổi trong x, y và tổng khoảng cách

			// tính toán của thay đổi trong x và vị trí y
			dx = enemyPos.x - position.x; //x vị trí của kẻ thù - tháp
			dy = enemyPos.y - position.y; // y position of enemy - tower
		
			// sử dụng định lý Pythagore để tính khoảng cách
			dist = Math.sqrt((dx*dx) + (dy*dy));
			
			//
			//giữ vị trí hiệu lực
			Coordinate pos = new Coordinate(position.x-50, position.y-50);
			
			//
			//pham vi ban
			if(dist < 150)
			{	Dan2 sunspot = new Dan2(pos, enemyPos);
				game.effects.add(sunspot);
				timeSinceLastFire = 0;
				return;
			}	
		} 
	}
}
