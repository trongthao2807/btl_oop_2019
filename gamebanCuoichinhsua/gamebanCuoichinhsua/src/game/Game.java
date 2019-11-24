package game;

import java.awt.*;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
enum GameState { SETUP, UPDATE, DRAW, WAIT, END }

public class Game implements Runnable
{
    public static void main (String[] args)
    {
        new Game ();
    }
    private Image backgr;
    private PathPoints line;

    private GamePanel gamePanel;
    private GameState state;

    private int frameCounter;
    private long lastTime;

    private boolean placingBlackHole;
    private Tower newBlackHole;
    private double elapsedTime;
    private boolean placingSun;
    private Tower newSun;

    private boolean placingTower3;
    private Tower newTower3;

    private boolean gameIsOver;
    private boolean gameIsWon;

    int livesCounter;
    int scoreCounter;
    int killsCounter;

    List<Enemy> enemies;				// danh sách đối tượng
    List<Tower> towers;					// danh sách các đối tượng tháp
    List<Effect> effects;				// danh sách các đối tượng hiệu ứng

    public Game ()
    {
        state = GameState.BEGIN;
        gamePanel = new GamePanel(this);

        Thread t = new Thread(this);
        t.start();
    }
    public void run ()
    {

        while (true)
        {

            if (state == GameState.BEGIN)
            {
                doSetupStuff();
            }

            else if (state == GameState.UPDATE)
            {
                doUpdateTasks();
            }

            else if (state == GameState.DRAW)
            {

                gamePanel.repaint();  //vẽ lại màn hình

                try { Thread.sleep(5); } catch (Exception e) {}
            }

            else if (state == GameState.WAIT)
            {
                try { Thread.sleep(50); } catch (Exception e) {}
                state = GameState.UPDATE;
            }

            else if (state == GameState.END)
            {
            }
        }
    }

    private void doSetupStuff ()
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Tower Defense Game");
        f.setContentPane(gamePanel);
        f.setSize(1365,768);
        //f.pack();
        f.setVisible(true);
        ImageLoader loader = ImageLoader.getLoader();
        backgr = loader.getImage("resources/background.jpg");
        livesCounter = 10;		//  10 sinh mạng
        scoreCounter = 2000;		//  2000 điểm để bắt đầu
        killsCounter = 0;		//bắt đầu bằng 0 mạng


        //Reset lại bộ đếm khung và thời gian
        frameCounter = 0;
        lastTime = System.currentTimeMillis();
        ClassLoader myLoader = this.getClass().getClassLoader();
        InputStream pointStream = myLoader.getResourceAsStream("resources/path.txt");
        Scanner s = new Scanner (pointStream);

        line  = new PathPoints(s);

        enemies = new LinkedList<Enemy>();
        towers = new LinkedList<Tower>();
        effects = new LinkedList<Effect>();

        placingBlackHole = false;
        newBlackHole = null;

        placingSun = false;
        newSun = null;

        gameIsOver = false;
        gameIsWon = false;

        state = GameState.UPDATE;
    }

    private void doUpdateTasks()
    {
        if(gameIsOver)
        {	state = GameState.DRAW;
            return;
        }

        if(gameIsWon)
        {	state = GameState.DRAW;
            return;
        }

        // Xem bao lâu kể từ khung cuối cùng.
        long currentTime = System.currentTimeMillis();
        elapsedTime = ((currentTime - lastTime) / 1000.0);  //Tính giây trôi qua
        lastTime = currentTime;

        for(Tower t: new LinkedList<Tower>(towers))
        {
            t.interact(this, elapsedTime);

        }
        //cho mỗi hiệu ứng, tương tác trong trò chơi này
        for(Effect e: new LinkedList<Effect>(effects))
        {
            e.interact(this, elapsedTime);
            if(e.isDone())
                effects.remove(e);
        }
        //Tiến lên từng kẻ thù trên con đường.
        for(Enemy a: new LinkedList<Enemy>(enemies))
        {
            a.advance();
            if(a.getPosition().isAtTheEnd())
            {
                enemies.remove(a);
                livesCounter--;
            }

        }
        this.generateEnemies();

        //
        //tăng bộ đếm khung
        frameCounter=frameCounter+1;

        // Đặt tháp nếu người dùng chọn
        this.placeBlackHoles();
        this.placeSuns();
        this.placeTower3s();

        if(livesCounter <= 0)
        {	gameIsOver = true;
            livesCounter = 0;
        }

        if(killsCounter >= 500)
        {	gameIsWon = true;
            killsCounter = 500;
        }

        state = GameState.DRAW;
    }


    public void draw(Graphics g)
    {

        if (state != GameState.DRAW)
            return;
        g.drawImage(backgr, 0, 0, null);

        for(Enemy e: new LinkedList<Enemy>(enemies))
            e.draw(g);

        for(Tower t: new LinkedList<Tower>(towers))
            t.draw(g);

        for(Effect s: new LinkedList<Effect>(effects))
            s.draw(g);

        // vẽ thanh menu
        g.setColor(new Color(89, 140, 85, 238));
        g.fillRect(0, 0, 300, 90);

        g.setColor(new Color(163, 204, 176, 238));
        g.fillRect(1000, 10, 240, 60);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        g.drawString(String.valueOf("Live : "+livesCounter), 1040, 35);	//cuộc sống truy cập
        g.drawString(String.valueOf("Score : "+scoreCounter), 1120, 35);	//điểm số
        g.drawString(String.valueOf("Skill : " + killsCounter), 1090, 60);
        //g.drawLine(430, 40, 610, 40);

        // vẽ tháp trong khu vực menu
        Tru1 blackhole = new Tru1(new Coordinate(60, 50));
        blackhole.draw(g);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        g.drawString("100", 67, 70);

        Tru2 sun = new Tru2(new Coordinate(155, 70));
        sun.draw(g);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        g.drawString("300", 157, 70);

        Tru3 tower3 = new Tru3(new Coordinate(260, 70));
        tower3.draw(g);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        //g.fillRect(500,70,80,80);
        g.drawString("500", 240, 70);

        if(newBlackHole != null)
            newBlackHole.draw(g);

        if(newSun != null)
            newSun.draw(g);

        if(newTower3 != null)
            newTower3.draw(g);

        ImageLoader loader = ImageLoader.getLoader();
        Image endGame = loader.getImage("resources/game_over.png");

        if(livesCounter <= 0)
            g.drawImage(endGame, 0, 0, null);

        if(killsCounter >= 500)
        {	g.setFont(new Font("Braggadocio", Font.ITALIC, 90));
            g.drawString("You Win!!!", 10, 250);
        }

        state = GameState.WAIT;
    }

    public void generateEnemies()
    {
        if(frameCounter %200==0)								// cham
        {
            enemies.add(new Xe4(line.getStart()));
            enemies.add(new Xe1(line.getStart()));

        }
        else if(frameCounter % 60 == 0 && frameCounter >= 500)	// cham
        {
            enemies.add(new Xe4(line.getStart()));
            enemies.add(new Xe1(line.getStart()));
            enemies.add(new Xe2(line.getStart()));
        }
        else if(frameCounter % 70 == 0 && frameCounter >= 1000)	// binh thuong
        {
            enemies.add(new Xe1(line.getStart()));
            enemies.add(new Xe2(line.getStart()));
            enemies.add(new Xe4(line.getStart()));
        }
        else if(frameCounter % 80 == 0 && frameCounter >= 1500)	// binh thuong
        {
            enemies.add(new Xe4(line.getStart()));
            enemies.add(new Xe1(line.getStart()));
            enemies.add(new Xe2(line.getStart()));
            enemies.add(new Xe3(line.getStart()));
        }
        else if(frameCounter % 90 == 0 && frameCounter >= 2000)	// nhanh
        {
            enemies.add(new Xe4(line.getStart()));
            enemies.add(new Xe1(line.getStart()));
            enemies.add(new Xe2(line.getStart()));
            enemies.add(new Xe3(line.getStart()));
        }

    }

    public void placeBlackHoles()
    {

        //biến để giữ vị trí chuột
        Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);

        // di chuyển đối tượng tháp khi chuột di chuyển
        if(gamePanel.mouseX > 20 && gamePanel.mouseX < 110 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY <60  &&
                gamePanel.mouseIsPressed && scoreCounter >= 100)
        {	//nếu chuột được nhấn vào biểu tượng tháp, tạo một đối tượng mới
            placingBlackHole = true;
            newBlackHole = new Tru1(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 1365 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 768 &&
                gamePanel.mouseIsPressed && placingBlackHole
                && line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
        {	// Nếu chuột được nhấn trên màn hình trò chơi, đặt tháp trên màn hình trò chơi
            newBlackHole.setPosition(mouseLocation);
            towers.add(new Tru1(mouseLocation));
            scoreCounter -= 100;
            newBlackHole = null;
            placingBlackHole = false;
        }

        // di chuyển đối tượng tháp với chuyển động chuột
        if(newBlackHole != null)
        {
            newBlackHole.setPosition(mouseLocation);
        }
    }

    public void placeSuns()
    {

        Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);
        if(gamePanel.mouseX >120  && gamePanel.mouseX < 210 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 60 &&
                gamePanel.mouseIsPressed && scoreCounter >= 300)
        {
            placingSun = true;
            newSun = new Tru2(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 1365 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 768 &&
                gamePanel.mouseIsPressed && placingSun
                && line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
        {
            newSun.setPosition(mouseLocation);
            towers.add(new Tru2(mouseLocation));
            scoreCounter -= 300;
            newSun = null;
            placingSun = false;
        }

        if(newSun != null)
        {
            newSun.setPosition(mouseLocation);
        }
    }

    public void placeTower3s()
    {

        Coordinate mouseLocation = new Coordinate(gamePanel.mouseX, gamePanel.mouseY);
        if(gamePanel.mouseX >200  && gamePanel.mouseX < 290 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 80 &&
                gamePanel.mouseIsPressed && scoreCounter >= 500)
        {
            placingTower3 = true;
            newTower3 = new Tru3(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 1365 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 768 &&
                gamePanel.mouseIsPressed && placingTower3
                && line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
        {
            newTower3.setPosition(mouseLocation);
            towers.add(new Tru3(mouseLocation));
            scoreCounter -= 500;
            newTower3 = null;
            placingTower3 = false;
        }

        if(newTower3 != null)
        {
            newTower3.setPosition(mouseLocation);
        }
    }
}	