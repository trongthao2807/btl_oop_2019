package game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
public class GamePanel extends JPanel implements MouseListener, MouseMotionListener
{
    private Game enclosingGame;
    public int mouseX; //tao do x theo chuot
    public int mouseY; // tao do y theo chuot
    public boolean mouseIsPressed;    // xac dinh xem chuot duoc nhap hay chua

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
    public ToaDo getToaDo()
    {
    	return new ToaDo(mouseX, mouseY);
    }
    

    
    public Dimension getMinimumSize ()
    {
        return new Dimension(1365,768);
    }
    public Dimension getMaximumSize ()
    {
        return new Dimension(1365,768);
    }
    public Dimension getPreferredSize ()
    {
        return new Dimension(1365,768);
    }


    
	// Phương thức MouseListener
	public void mouseClicked(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
	}


	public void mouseEntered(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}


	public void mouseExited(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}


	public void mousePressed(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
	}


	public void mouseReleased(MouseEvent e) 
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = true;
		
	}
	

	public void mouseDragged(MouseEvent e) 
	{	
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = false;
	}


	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		mouseIsPressed = false;

	}
}
