package game;

import java.awt.*;


public class Xe2 extends Enemy
{
	Xe2(PathPosition p)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("resources/tank2.png");
		this.position = p;
		this.anchorX = -20;
		this.anchorY = -20;
		this.velocity = 6;
		this.health=30;
	}

}
