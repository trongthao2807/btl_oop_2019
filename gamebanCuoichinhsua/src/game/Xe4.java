package game;

import java.awt.*;


public class Xe4 extends Enemy
{
    Xe4(PathPosition p)
    {
        ImageLoader loader = ImageLoader.getLoader();
        this.enemy = loader.getImage("resources/aaaa.png");
        this.position = p;
        this.anchorX = -10;
        this.anchorY = -10;
        this.velocity = 1;
    }

}
