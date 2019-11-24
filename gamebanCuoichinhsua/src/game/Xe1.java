package game;
public class Xe1 extends Enemy
{
	Xe1(PathPosition p)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.enemy = loader.getImage("resources/1.png");
		this.position = p;
		this.anchorX = -20;
		this.anchorY = -20;
		this.velocity = 3;
	}
	
}