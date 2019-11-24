package game;

import java.awt.Graphics;
import java.awt.Image;
abstract public class Tower 
{
	/* instance variables */
	protected ToaDo position;
	protected Image tower;
	protected int anchorX;			//thay đổi tọa độ X
	protected int anchorY;
	protected double timeSinceLastFire;// thời gian kể từ khi hiệu ứng cuối cùng bị sa thải
	
	public void draw(Graphics g)
	{
		//
		//Vẽ đối tượng tháp đến vị trí được chỉ định bởi người dùng
		g.drawImage(tower, position.getX() + anchorX, position.getY() + anchorY, null);

	}
	public void setPosition(ToaDo c)
	{
		position = c;
	}
	
	abstract void interact(Game game, double deltaTime);
}





