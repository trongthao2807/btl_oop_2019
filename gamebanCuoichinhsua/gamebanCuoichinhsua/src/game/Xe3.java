package game;

public class Xe3 extends Enemy
{
	Xe3(PathPosition p)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("resources/3.png");
		this.position = p;
		this.anchorX = -20;
		this.anchorY = -20;
		this.velocity = 9;
		this.health=50;
	}
	
}
