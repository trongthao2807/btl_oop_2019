package game;

import java.awt.*;
import javax.swing.JFrame;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.List;

enum GameState { BEGIN, UPDATE, DRAW, WAIT, END }

public class Game implements Runnable
{
    public static void main (String[] args)
    {
        // Khoi tao chuong trinh
        new Game ();
    }
    private Image backgr;        // anh background
    private PathPoints line;     // toa do

    private GamePanel gamePanel; // doi tuong gamepanel
    private GameState state;    // doi tuong trang thai cua tro choi

    private int frameCounter;   //dung de theo doi cap nhat khung hinh
    private long lastTime;      // dung de theo doi thoi gian

    private boolean placingTru1; // neu tru1 dang duoc dat thi dung
    private Tower newTru1;      // tao them tru1
    private double elapsedTime; // dem thoi gian troi qua

    private boolean placingTru2; // neu tru2 dang duoc dat thi dung
    private Tower newTru2;      // tao them tru2

    private boolean placingTru3; // neu tru3 dang duoc dat thi dung
    private Tower newTru3;        // tao them tru3

    private boolean gameIsOver;   // kiem tra xem game da ket thuc chua
    private boolean gameIsWon;      // kiem tra xem game chien thang chua

    int livesCounter;  // so mang song
    int scoreCounter;   // so diem
    int killsCounter;   // so kill

    List<Enemy> enemies;    // danh sach cac quan dich
    List<Tower> towers;     // danh sach cac tru
    List<Effect> effects;   // danh sach cac hieu ung

    public Game ()
    {
        state = GameState.BEGIN;
        gamePanel = new GamePanel(this);

        Thread t = new Thread(this);
        t.start();
    }
    public void run ()
    {
        // voi dieu kien vong lap la true thi chuong chinh se chay mai mai
        // cho den khi ta dong cua so chuong trinh
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
        f.setVisible(true);
        ImageLoader loader = ImageLoader.getLoader();
        backgr = loader.getImage("resources/background.png");
        livesCounter = 10;		// cung cap so mang ban dau la 10
        scoreCounter = 2000;		// cung cap so diem ban dau la 2000
        killsCounter = 0;		// cung cap so mang da giet ban dau la 0


        frameCounter = 0;       // bo dem khung ban dau la 0
        lastTime = System.currentTimeMillis();    // do thoi gian tinh tu 1/1/1970
        ClassLoader myLoader = this.getClass().getClassLoader();
        // Duong dan den file .txt ( gom cac toa do )
        InputStream pointStream = myLoader.getResourceAsStream("resources/path.txt");
        Scanner s = new Scanner (pointStream);

        line  = new PathPoints(s);

        enemies = new LinkedList<Enemy>();  // tao danh sach quan dich moi
        towers = new LinkedList<Tower>();   // tao danh sach tru moi
        effects = new LinkedList<Effect>(); // tao danh sach hieu ung moi

        // khoi tao cac gia tri
        placingTru1 = false;
        newTru1 = null;

        placingTru2 = false;
        newTru2 = null;

        placingTru3 = false;
        newTru3 = null;

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

        long currentTime = System.currentTimeMillis();
        elapsedTime = ((currentTime - lastTime) / 1000.0);  // tinh thoi gian troi qua
        lastTime = currentTime;         // cap nhat thoi gian currentTime

        // Tuong tac giua tru va game
        for(Tower t: new LinkedList<Tower>(towers))
        {
            t.interact(this, elapsedTime);

        }

        // Tuong tac giua hieu ung va game
        for(Effect e: new LinkedList<Effect>(effects))
        {
            e.interact(this, elapsedTime);
            if(e.isDone())
                effects.remove(e);
        }

        // Tuong tac giua linh va game
        for(Enemy a: new LinkedList<Enemy>(enemies))
        {
            a.advance();
            if(a.getPosition().isAtTheEnd())
            {
                enemies.remove(a);
                livesCounter--;
            }

        }
        // them vao danh sach ke thu
        this.generateEnemies();


        // tang bo dem khung
        frameCounter=frameCounter+1;


        this.addressTru1(); // dat them tru1
        this.addressTru2(); // dat them tru2
        this.addressTru3(); // dat them tru3

        // kiem tra xem thua chua
        if(livesCounter <= 0)
        {
            gameIsOver = true;
            livesCounter = 0;
        }

        // kiem tra xem thang chua
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
        // ve hinh nen
        g.drawImage(backgr, 0, 0, null);

        // Ve tat ca cac doi tuong
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
        g.fillRect(1000, 10, 210, 60);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        g.drawString(String.valueOf("Live : "+livesCounter), 1020, 35);	// so mang song
        g.drawString(String.valueOf("Score : "+scoreCounter), 1100, 35);	// diem so
        g.drawString(String.valueOf("Kill : " + killsCounter), 1070, 60); // so mang da kill

        Tru1 tru1 = new Tru1(new ToaDo(60, 50));
        tru1.draw(g);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        g.drawString("100", 67, 70);

        Tru2 tru2 = new Tru2(new ToaDo(155, 50));
        tru2.draw(g);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        g.drawString("300", 157, 70);

        Tru3 tru3 = new Tru3(new ToaDo(260, 50));
        tru3.draw(g);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Lucidia Sans", Font.BOLD, 16));
        //g.fillRect(500,70,80,80);
        g.drawString("500", 240, 70);

        if(newTru1 != null)
            newTru1.draw(g);

        if(newTru2 != null)
            newTru2.draw(g);

        if(newTru3 != null)
            newTru3.draw(g);

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

    // Xay dung ke thu
    public void generateEnemies()
    {
        // xay dung tan so xuat hien cua ke thu
        if(frameCounter %50==0)								// cham
        {
            enemies.add(new Xe1(line.getStart()));
        }
        else if(frameCounter % 60 == 0 && frameCounter >= 500)	// cham
        {
            enemies.add(new Xe1(line.getStart()));
            enemies.add(new Xe2(line.getStart()));
        }
        else if(frameCounter % 70 == 0 && frameCounter >= 1000)	// binh thuong
        {
            enemies.add(new Xe1(line.getStart()));
            enemies.add(new Xe2(line.getStart()));
            enemies.add(new Xe3(line.getStart()));
        }
        else if(frameCounter % 80 == 0 && frameCounter >= 1500)	// binh thuong
        {
            enemies.add(new Xe1(line.getStart()));
            enemies.add(new Xe2(line.getStart()));
            enemies.add(new Xe3(line.getStart()));
            enemies.add(new Xe4(line.getStart()));
        }
        else if(frameCounter % 90 == 0 && frameCounter >= 2000)	// nhanh
        {
            enemies.add(new Xe4(line.getStart()));
            enemies.add(new Xe1(line.getStart()));
            enemies.add(new Xe2(line.getStart()));
            enemies.add(new Xe3(line.getStart()));
        }

    }

    //Thao tac voi chuot Tru1
    public void addressTru1()
    {


        ToaDo mouseLocation = new ToaDo(gamePanel.mouseX, gamePanel.mouseY);


        if(gamePanel.mouseX > 20 && gamePanel.mouseX < 100 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY <60  &&
                gamePanel.mouseIsPressed && scoreCounter >= 100)
        {
            placingTru1 = true;
            newTru1 = new Tru1(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 1365 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 768 &&
                gamePanel.mouseIsPressed && placingTru1
                && line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
        {
            newTru1.setPosition(mouseLocation);
            towers.add(new Tru1(mouseLocation));
            scoreCounter -= 100;
            newTru1 = null;
            placingTru1 = false;
        }


        if(newTru1 != null)
        {
            newTru1.setPosition(mouseLocation);
        }
    }

    //Thao tac voi chuot Tru2
    public void addressTru2()
    {

        ToaDo mouseLocation = new ToaDo(gamePanel.mouseX, gamePanel.mouseY);
        if(gamePanel.mouseX >115  && gamePanel.mouseX < 200 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 60 &&
                gamePanel.mouseIsPressed && scoreCounter >= 300)
        {
            placingTru2 = true;
            newTru2 = new Tru2(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 1365 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 768 &&
                gamePanel.mouseIsPressed && placingTru2
                && line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
        {
            newTru2.setPosition(mouseLocation);
            towers.add(new Tru2(mouseLocation));
            scoreCounter -= 300;
            newTru2 = null;
            placingTru2 = false;
        }

        if(newTru2 != null)
        {
            newTru2.setPosition(mouseLocation);
        }
    }


    //Thao tac voi chuot Tru3
    public void addressTru3()
    {

        ToaDo mouseLocation = new ToaDo(gamePanel.mouseX, gamePanel.mouseY);
        if(gamePanel.mouseX >215  && gamePanel.mouseX < 300 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 80 &&
                gamePanel.mouseIsPressed && scoreCounter >= 500)
        {
            placingTru3 = true;
            newTru3 = new Tru3(mouseLocation);
        }
        else if(gamePanel.mouseX > 0 && gamePanel.mouseX < 1365 &&
                gamePanel.mouseY > 0 && gamePanel.mouseY < 768 &&
                gamePanel.mouseIsPressed && placingTru3
                && line.distanceToPath(gamePanel.mouseX, gamePanel.mouseY) > 60)
        {
            newTru3.setPosition(mouseLocation);
            towers.add(new Tru3(mouseLocation));
            scoreCounter -= 500;
            newTru3 = null;
            placingTru3 = false;
        }

        if(newTru3 != null)
        {
            newTru3.setPosition(mouseLocation);
        }
    }
}	