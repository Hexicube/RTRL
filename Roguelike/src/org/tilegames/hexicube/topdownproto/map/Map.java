package org.tilegames.hexicube.topdownproto.map;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Map
{
	public Map(int width, int height)
	{
		tiles = new Tile[width][height];
		entities = new ArrayList<Entity>();
		needsLighting = true;
		
		mapImage = new Pixmap(Game.nextPowerTwo(width), Game.nextPowerTwo(height), Pixmap.Format.RGBA8888);
		mapImage.setColor(0, 0, 0, 0);
		mapImage.fill();
		mapImage.setColor(0, 0, 0, 1);
		mapImage.fillRectangle(0, 0, width, height);
		mapTex = new Texture(mapImage);
	}
	
	public void updateTexture(int x, int y)
	{
		for(int x2 = x - 2; x2 <= x + 2; x2++)
		{
			for(int y2 = y - 2; y2 <= y + 2; y2++)
			{
				try
				{
					if(tiles[x2][y2] instanceof TileVoid) mapImage.setColor(0, 0, 0, 1);
					else if(tiles[x2][y2] instanceof TileWall || tiles[x2][y2] instanceof TileTorchWall) mapImage.setColor(0.5f, 0.5f, 0.5f, 1);
					else if(tiles[x2][y2] instanceof TileFloor) mapImage.setColor(0.8f, 0.8f, 0.8f, 1);
					else if(tiles[x2][y2] instanceof TileDoor) mapImage.setColor(1, 0, 0, 1);
					else if(tiles[x2][y2] instanceof TileStair) mapImage.setColor(0, 1, 0, 1);
					else mapImage.setColor(0, 1, 1, 1);
					mapImage.drawPixel(x2, tiles[0].length - y2 - 1);
				}
				catch(IndexOutOfBoundsException e)
				{}
			}
		}
		mapTex.draw(mapImage, 0, 0);
	}
	
	public ArrayList<Entity> entities;
	public Tile[][] tiles;
	private Pixmap mapImage;
	public Texture mapTex;
	
	public boolean needsLighting;
}