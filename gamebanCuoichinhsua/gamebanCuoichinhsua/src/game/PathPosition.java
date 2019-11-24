package game;

import java.util.List;

public class PathPosition
{
	private int segment;
	private double percentage;
	private List <ToaDo> path;

	PathPosition(List<ToaDo> points)
	{
		
		this.segment = 0;
		this.percentage = 0;
		this.path = points;
	}
    public boolean isAtTheEnd () {
		return segment == path.size() - 1;
	}
    public ToaDo getToaDo ()
    {	
    	if(isAtTheEnd())
    		return path.get(path.size()-1);

    	//
		//// biến cho vị trí bắt đầu và kết thúc của tọa độ X
    	int startX = path.get(segment).x;
    	int endX = path.get(segment + 1).x;
    	
    	// các biến cho vị trí bắt đầu và kết thúc của tọa độ Y
    	int startY = path.get(segment).y;
    	int endY = path.get(segment + 1).y;
    	
    	//
		//tính toán sự thay đổi vị trí X và Y bằng cách lấy chênh lệch
    	int dX = endX - startX;
    	int dY = endY - startY;
 	
    	//
		//thiết lập vị trí mới của quả bóng bằng cách thêm thay đổi để bắt đầu
    	int ballX = startX + (int) (dX*percentage);
    	int ballY = startY + (int) (dY*percentage);
    	
        return new ToaDo(ballX, ballY);
    }
    public void advance (double distance)
    {	
    	//
		//kiểm tra xem đối tượng có ở cuối đường dẫn không
        if(isAtTheEnd())
        	return;		//con đường không tiến
    	
    	// các biến cho vị trí bắt đầu và kết thúc của tọa độ X
    	int startX = path.get(segment).x;
    	int endX = path.get(segment + 1).x;
    	
    	//các biến cho vị trí bắt đầu và kết thúc của tọa độ Y
    	int startY = path.get(segment).y;
    	int endY = path.get(segment + 1).y;
    	
    	//
		//tính toán sự thay đổi vị trí X và Y bằng cách lấy chênh lệch
    	int dX = endX - startX;
    	int dY = endY - startY;
    	
    	//
		//sử dụng định lý Pythagore để tính toán vị trí mới
    	double length = Math.sqrt((double) (dX*dX) + (double) (dY*dY));
    	double unit = 1/length;	//biến cho đơn vị thay đổi vị trí theo phần trăm chiều dài
    	
    	percentage += unit*distance;	// thêm thay đổi vào tỷ lệ phần trăm trước đó


// tìm tỷ lệ khoảng cách và chiều dài
		// nhân đôi tỷ lệ phần trăm = khoảng cách / chiều dài;

		// kiểm tra khoảng cách dọc theo đối tượng
    	if(percentage > 1)
    	{
    		segment ++;										// phân khúc gia tăng
    		distance = distance-(1-percentage)*length;	// giảm khoảng cách theo chiều dài
    		percentage = 0;									// set percent to 0
    		advance(distance);								//tiến khoảng cách còn lại
    	}
    }
}