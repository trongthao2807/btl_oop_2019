package game;

import java.util.List;


// doi tuong PathPoint dai dien cho 1 vi tri doc theo mot duong dan , co 3 thanh phan la :
// + duong dan ma doi tuong dang o
// + danh sach tao do cua doi tuong
// + phan tram cua doi tuong
public class PathPosition
{
	private int segment;      // gia tri cua phan doan
	private double percentage; //Ti le phan tram
	private List <ToaDo> path; // Tao danh sach toa do

	//Khoi tao PathPoint
	PathPosition(List<ToaDo> points)
	{
		this.segment = 0;
		this.percentage = 0;
		this.path = points;
	}
    // tra ve true neu vi tri duong dan nay o cuoi con duong
	public boolean isAtTheEnd () {
		return segment == path.size() - 1;
	}

	// tra ve 1 doi tuong chua toa do (x,y) cua duong dan hien tai
	public ToaDo getToaDo ()
    {	
    	if(isAtTheEnd())
    		return path.get(path.size()-1);
		// biến cho vị trí bắt đầu và kết thúc của tọa độ X
    	int startX = path.get(segment).x;
    	int endX = path.get(segment + 1).x;

		// các biến cho vị trí bắt đầu và kết thúc của tọa độ Y
    	int startY = path.get(segment).y;
    	int endY = path.get(segment + 1).y;

		//tính sự thay đổi vị trí X và Y
    	int dX = endX - startX;
    	int dY = endY - startY;

    	int ballX = startX + (int) (dX*percentage);
    	int ballY = startY + (int) (dY*percentage);

    	//Tra lai gia tri moi cua Position
        return new ToaDo(ballX, ballY);
    }

	// phuong phap advance duoc tao nham muc dich : lam cho khoang cach vuot qua duoc cuoi con duong
	// hien tai khoang cach dang dung o cuoi con duong
    public void advance (double distance) {

		//kiểm tra xem đối tượng có ở cuối đường dẫn không
		if (isAtTheEnd())
			return;

		// các biến cho vị trí bắt đầu và kết thúc của tọa độ X
		int startX = path.get(segment).x;
		int endX = path.get(segment + 1).x;


		//các biến cho vị trí bắt đầu và kết thúc của tọa độ Y
		int startY = path.get(segment).y;
		int endY = path.get(segment + 1).y;

		//tính toán sự thay đổi vị trí X và Y
		int dX = endX - startX;
		int dY = endY - startY;


		//sử dụng định lý Pythagore để tính toán vị trí mới
		double length = Math.sqrt((double) (dX * dX) + (double) (dY * dY));
		//biến cho đơn vị thay đổi vị trí theo phần trăm chiều dài
		double unit = 1 / length;

		// thêm thay đổi vào tỷ lệ phần trăm trước đó
		percentage += unit * distance;
		if (percentage > 1) {
			segment++;
			distance = distance - (1 - percentage) * length;
			percentage = 0;
			advance(distance);
		}
	}
}