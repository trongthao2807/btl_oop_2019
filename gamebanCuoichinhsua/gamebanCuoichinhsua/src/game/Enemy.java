package game;

import java.awt.*;


abstract public class Enemy
{
	protected PathPosition position;	// giữ vị trí hiện tại của kẻ thù
	protected Image enemy;				//giữ hình ảnh của kẻ thù
	protected int anchorX;				// thay đổi vị trí trên trục x
	protected int anchorY;				// thay đổi vị trí trên trục y
	protected double velocity; 			//tăng hoặc giảm tốc độ trước
    protected double health;

	public void advance()
	{
		position.advance(1 + velocity);
	}
	public void healthy(){
		health-=5;
	}

	public void draw(Graphics g)
	{
		//
        //Vẽ đối tượng kẻ thù
		ToaDo c = position.getToaDo();
		g.drawImage(enemy, c.x + anchorX, c.y + anchorY, null);
		//health bar
		g.setColor(new Color(180,50,50));
		g.fillRect(c.x+anchorX+20,c.y-anchorY-30,30,5);
	}

	public PathPosition getPosition()
	{
		return position;
	}
	
}
