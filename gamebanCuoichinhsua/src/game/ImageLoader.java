package game;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;


// class dung de load anh va cac thao tac voi anh
public class ImageLoader 
{
	private Map<String, Image> imageBank; //khai bao map de giu hinh anh
	private static ImageLoader instance;

	// khoi tao qua trinh load anh
	private ImageLoader()
	{
		imageBank = new TreeMap<String, Image>();

	}

	// lay doi tuong tai hinh anh
	static ImageLoader getLoader()
	{	
		if(instance == null)
			instance = new ImageLoader();
		return instance;

	}
	public Image getImage(String pic)
	{	
		Image loaded = null;


		// ngan chan viec tai hinh anh 2 lan
		if(imageBank.containsKey(pic)) // neu hinh anh da co trong ban do
			return imageBank.get(pic);  // tra lai hinh anh do

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
			
			return loaded; // tra ve hinh anh
		}
	}
}
