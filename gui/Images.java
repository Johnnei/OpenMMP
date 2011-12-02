package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import monopoly.Game;

public class Images {
	
	private static final Images imgs = new Images();
	public BufferedImage Bord;
	public BufferedImage[] Dice;
	public BufferedImage Icon;
	
	private Images()
	{
		try {
			Bord = ImageIO.read(new File("src/bord.jpg"));
			Icon = ImageIO.read(new File("src/icon.png"));
			Dice = new BufferedImage[6];
			for(int i = 0; i < Dice.length; i++)
			{
				try 
				{
					Dice[i] = ImageIO.read(new File("src/dice"+(i+1)+".png"));
				}
				catch(Exception e)
				{
					Game.Log("Err Loading: src/dice"+(i+1)+".png");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			Game.Log("ImageLoadError: " + e.getMessage());
		}
	}
	
	public static Images getImages()
	{
		return imgs;
	}

}
