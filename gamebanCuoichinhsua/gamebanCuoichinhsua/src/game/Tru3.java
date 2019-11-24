package game;

import java.util.List;

public class Tru3 extends Tower
{
    public Tru3(Coordinate pos)
    {
        ImageLoader loader = ImageLoader.getLoader();
        this.tower = loader.getImage("resources/Tower_Sniper.png");
        this.position = pos;
        this.anchorX = -60;
        this.anchorY = -60;
    }
    public void interact(Game game, double deltaTime)
    {	//theo dõi thời gian mà hiệu ứng đã tồn tại
        timeSinceLastFire += deltaTime;

        // nếu thời gian dưới 1,5 giây, đừng tương tác
        if(timeSinceLastFire < 0.3)
            return;

        List<Enemy> enemies = game.enemies; // danh sách kẻ thù mới

        //
        //Cung cấp vị trí của kẻ thù trong danh sách kẻ thù
        for(Enemy e: enemies)
        {

            // giữ vị trí của kẻ thù
            Coordinate enemyPos = e.getPosition().getCoordinate();

            // Tính khoảng cách của kẻ thù đến tháp
            double dx, dy, dist;	//thay đổi trong x, y và tổng khoảng cách

            //
            //tính toán của thay đổi trong x và vị trí y
            dx = enemyPos.x - position.x;
            dy = enemyPos.y - position.y; // vị trí y của kẻ thù - tháp

            //
            //tính khoảng cách
            dist = Math.sqrt((dx*dx) + (dy*dy));

            //
            //giữ vị trí hiệu lực
            Coordinate pos = new Coordinate(position.x-50, position.y-50);

            //
            //pham vi ban
            if(dist < 170)
            {	Dan3 stardust = new Dan3(pos, enemyPos);
                game.effects.add(stardust);
                timeSinceLastFire = 0;
                return;
            }
        }
    }
}
