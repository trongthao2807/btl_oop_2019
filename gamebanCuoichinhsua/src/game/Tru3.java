package game;

import java.util.List;

public class Tru3 extends Tower
{
    public Tru3(ToaDo pos)
    {
        ImageLoader loader = ImageLoader.getLoader();
        this.tower = loader.getImage("resources/Tower_Sniper.png");
        this.position = pos;
        this.anchorX = -30;
        this.anchorY = -40;
    }
    public void interact(Game game, double deltaTime)
    {
        timeSinceLastFire += deltaTime;


        if(timeSinceLastFire < 0.3)
            return;

        List<Enemy> enemies = game.enemies; // danh sách kẻ thù mới


        for(Enemy e: enemies)
        {


            ToaDo enemyPos = e.getPosition().getToaDo();


            double dx, dy, dist;


            dx = enemyPos.x - position.x;
            dy = enemyPos.y - position.y;


            dist = Math.sqrt((dx*dx) + (dy*dy));

            ToaDo pos = new ToaDo(position.x-50, position.y-50);


            if(dist < 120)
            {	Dan3 stardust = new Dan3(pos, enemyPos);
                game.effects.add(stardust);
                timeSinceLastFire = 0;
                return;
            }
        }
    }
}
