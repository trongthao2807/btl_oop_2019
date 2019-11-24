package game;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

public class ImageLoader 
{
	private Map<String, Image> imageBank;	// khai báo bản đồ để giữ hình ảnh
	private static ImageLoader instance;
	private ImageLoader()
	{
		imageBank = new TreeMap<String, Image>();
// khai báo bản đồ để giữ hình ảnh
	}

	static ImageLoader getLoader()
	{	
		if(instance == null)
			instance = new ImageLoader();
		return instance;
// lấy đối tượng tải hình ảnh
	}
	public Image getImage(String pic)
	{	
		Image loaded = null;


// Ngăn chặn và hình ảnh được tải hai lần
		if(imageBank.containsKey(pic))	// nếu hình ảnh đã có trong bản đồ
			return imageBank.get(pic);
// trả lại hình ảnh đó
		else
		{
			try
			{


				ClassLoader myLoader = this.getClass().getClassLoader();

				InputStream imageStream = myLoader.getResourceAsStream(pic);

				loaded = ImageIO.read(imageStream);
					
			}
			catch (IOException e) {System.out.println ("Could not load: " + e);}
			
			imageBank.put(pic, loaded);
			
			return loaded;
		}
	}
}
