package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;


public class PathPoints 
{
	//
    //Mỗi đối tượng PathPoints sẽ có một danh sách tọa độ:
	private List <ToaDo> path;

	public PathPoints(Scanner s)
	{	
		path = new ArrayList<Coordinate>();		//tạo ArrayList mới của tọa độ
		int counter = s.nextInt(); 				// đọc số tọa độ c
		
		for(int n = 0; n < counter; n++)
		{
			//// xây dựng đối tượng tọa độ
			Coordinate c = new Coordinate(s.nextInt(), s.nextInt());
			path.add(c);	//thêm đối tượng tọa độ vào danh sách đường dẫn
		}	
	}
	

	public void drawLine(Graphics g)
	{	
		g.setColor(Color.WHITE);	//đặt màu đường

		for(int n = 0; n < path.size()-1; n++)
		{	//vẽ một đoạn sau đó lặp lại
            g.drawLine(path.get(n).x, path.get(n).y, path.get(n+1).x, path.get(n+1).y);
		}	
	}
    public PathPosition getStart()
    {	
        return new PathPosition(new ArrayList<>(path));
    }

    public double distanceToPath (double px, double py)
    {
        double minDistance = Double.MAX_VALUE;
        
        for (int i = 0; i < path.size()-1; i ++)
        {
            //
            //Nhận điểm bắt đầu và điểm kết thúc cho một phân khúc.
            
            double sx = path.get(i).x;
            double sy = path.get(i).y;
            double ex = path.get(i+1).x;
            double ey = path.get(i+1).y;
            
            // Vectơ phân đoạn tính toán từ đầu đến cuối và chiều dài của nó, sau đó chuẩn hóa nó.
            
            double vx = ex - sx;
            double vy = ey - sy;
            double vl = Math.sqrt(vx * vx + vy * vy);
            vx /= vl;
            vy /= vl;
            
            // Tính toán vectơ từ đầu đến điểm
            
            double dx = px - sx;
            double dy = py - sy;
            double dl = Math.sqrt(dx * dx + dy * dy);

            
            double segDist = dx * vx + dy * vy;

            // chỉ cần tính khoảng cách đến điểm cuối đó
            
            double distance;
            if (segDist < 0)
                distance = Math.sqrt((vx-sx)*(vx-sx) + (vy-sy)*(vy-sy));
            else if (segDist > vl)
                distance = Math.sqrt((vx-ex)*(vx-ex) + (vy-ey)*(vy-ey));
            else
                distance = Math.sqrt(dl*dl - segDist*segDist);


            
            if (distance < minDistance)
                minDistance = distance;
        }
        
        return minDistance;
    }

}
