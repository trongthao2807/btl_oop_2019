package game;

public class Dan1 extends Effect
{
	public Dan1(ToaDo pos, ToaDo target)
	{
		// Tải hình ảnh dan1
		ImageLoader loader = ImageLoader.getLoader();
		this.picture = loader.getImage("resources/dan_11.png");
		
		//
		//Vị trí hiệu ứng X và Y
		this.posX = pos.x;
		this.posY = pos.y;		
		
		// Vị trí X và Y của kẻ thù mục tiêu
		this.velocityX = target.x - this.posX;
		this.velocityY = target.y - this.posY;
		
		//
		//Đặt thời gian thành 0
		this.ageInSeconds = 0;
	}	
}
