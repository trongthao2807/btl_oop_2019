package game;
public class ToaDo
{
	public int x, y;

	public ToaDo(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public int getX()
	{
		return this.x; 
	}
	public int getY()
	{
		return this.y; 
	}
	
	public String toString()
	{
		return ("" + x + " " + y);
	}

}