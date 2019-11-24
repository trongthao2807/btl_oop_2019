package game;
public class Dan2 extends Effect
{
	public Dan2(Coordinate pos, Coordinate target)
	{
		ImageLoader loader = ImageLoader.getLoader();
		this.picture = loader.getImage("resources/dan_22.png");
		
		// Vị trí hiệu ứng X và Y
		this.posX = pos.x;
		this.posY = pos.y;		
		
		// Vị trí X và Y của kẻ thù cần bắn
		this.velocityX = target.x - this.posX;
		this.velocityY = target.y - this.posY;

		this.ageInSeconds = 0;
	}	
}
