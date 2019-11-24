package game;

public class Dan3 extends Effect
{
    public Dan3(Coordinate pos, Coordinate target)
    {
        // Tải hình ảnh
        ImageLoader loader = ImageLoader.getLoader();
        this.picture = loader.getImage("resources/comet.png");

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
