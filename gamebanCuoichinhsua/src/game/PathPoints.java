package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;


public class PathPoints 
{

	// Tao 1 danh sach cac toa do
    private List <ToaDo> path;

	public PathPoints(Scanner s)
	{	
		path = new ArrayList<ToaDo>(); // Tao moi 1 danh sach cac toa do
		int counter = s.nextInt();     // nhap vao so toa do
		
		for(int n = 0; n < counter; n++)
		{

            ToaDo c = new ToaDo(s.nextInt(), s.nextInt());
			path.add(c); // them doi tuong toa do tu danh sach path.txt
		}	
	}
	
    //Khoi tao PathPosition
    public PathPosition getStart()
    {	
        return new PathPosition(new ArrayList<>(path));
    }

    // tinh khoang cach gan nhat tu diem chi dinh den duong dan
    public double distanceToPath (double px, double py)
    {
        // gia tri max
        double minDistance = Double.MAX_VALUE;
        
        for (int i = 0; i < path.size()-1; i ++)
        {
            //Nhận điểm bắt đầu và điểm kết thúc cho 1 doan duong
            
            double sx = path.get(i).x;
            double sy = path.get(i).y;
            double ex = path.get(i+1).x;
            double ey = path.get(i+1).y;

            
            double vx = ex - sx;
            double vy = ey - sy;
            double vl = Math.sqrt(vx * vx + vy * vy);
            vx /= vl;
            vy /= vl;

            
            double dx = px - sx;
            double dy = py - sy;
            double dl = Math.sqrt(dx * dx + dy * dy);

            
            double segDist = dx * vx + dy * vy;

            
            double distance;
            if (segDist < 0)
                distance = Math.sqrt((vx-sx)*(vx-sx) + (vy-sy)*(vy-sy));
            else if (segDist > vl)
                distance = Math.sqrt((vx-ex)*(vx-ex) + (vy-ey)*(vy-ey));
            else
                distance = Math.sqrt(dl*dl - segDist*segDist);

            
            // neu khoang cach nho hon khoang cach hien tai thi giu diem do
            if (distance < minDistance)
                minDistance = distance;
        }
        
        return minDistance;
    }

}
