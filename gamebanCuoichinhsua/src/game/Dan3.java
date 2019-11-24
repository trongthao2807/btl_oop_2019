package game;

public class Dan3 extends Effect
{
    public Dan3(ToaDo pos, ToaDo target)
    {

        ImageLoader loader = ImageLoader.getLoader();
        this.picture = loader.getImage("resources/comet.png");


        this.posX = pos.x;
        this.posY = pos.y;


        this.velocityX = target.x - this.posX;
        this.velocityY = target.y - this.posY;


        this.ageInSeconds = 0;
    }
}
