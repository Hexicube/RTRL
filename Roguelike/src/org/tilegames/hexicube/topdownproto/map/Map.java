package org.tilegames.hexicube.topdownproto.map;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.entity.Entity;

public class Map
{
	public Map(int width, int height)
	{
		tiles = new Tile[width][height];
		entities = new ArrayList<Entity>();
		needsLighting = true;
	}
	
	public ArrayList<Entity> entities;
	public Tile[][] tiles;
	
	public boolean needsLighting;
}