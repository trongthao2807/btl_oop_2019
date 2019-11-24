package game;
public class Dan2 extends Effect
{
	public Dan2(ToaDo pos, ToaDo target)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.picture = loader.getImage("resources/dan_22.png");
		

		this.posX = pos.x;
		this.posY = pos.y;		
		

		this.velocityX = target.x - this.posX;
		this.velocityY = target.y - this.posY;

		this.ageInSeconds = 0;
	}	
}
