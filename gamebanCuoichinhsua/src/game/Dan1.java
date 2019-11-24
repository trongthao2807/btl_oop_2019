package game;

public class Dan1 extends Effect
{
	public Dan1(ToaDo pos, ToaDo target)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.picture = loader.getImage("resources/dan_11.png");

		this.posX = pos.x;
		this.posY = pos.y;		
		

		this.velocityX = target.x - this.posX;
		this.velocityY = target.y - this.posY;
		

		this.ageInSeconds = 0;
	}	
}
