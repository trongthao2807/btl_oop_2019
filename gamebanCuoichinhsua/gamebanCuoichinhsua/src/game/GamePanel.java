package game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
public class GamePanel extends JPanel implements MouseListener, MouseMotionListener
{
    private Game enclosingGame;  	//Tham chiếu trở lại đối tượng Trò chơi đã tạo đối tượng
    public int mouseX;
    public int mouseY;
    public boolean mouseIsPressed;	//Xác định xem chuột đã được nhấp hay chưa

    public GamePanel (Game enclosingGame)
    {
    	this.addMouseListener(this);
    	this.addMouseMotionListener(this);
        this.enclosingGame = enclosingGame;
    }

    public void paintComponent (Graphics g)
    {
        enclosingGame.draw (g);
    }
    public Coordinate getCoordinate()
    {
    	return new Coordinate(mouseX, mouseY);
    }
    
     //kích thước bảng điều khiển
    
    public Dimension getMinimumSize ()
    {
        return new Dimension(1920,1080);
    }
    public Dimension getMaximumSize ()
    {
        return new Dimension(1920,1080);
    }
    public Dimension getPreferredSize ()
    {
        return new Dimension(1920,1080);
    }


    
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{	
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = false;
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = false;

	}
}
