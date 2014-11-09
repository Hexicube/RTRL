package org.tilegames.hexicube.topdownproto.map;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileFloor extends Tile
{
	@Override
	public boolean onWalkAttempt(Entity entity)
	{
		return true;
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		batch.draw(Game.tileTex, Game.width/2 + x * 32 - 16, Game.height/2 + y * 32 - 16, 32, 32, 0, 0, 32, 32, false, false);
	}
	
	@Override
	public boolean setCurrentEntity(Entity entity)
	{
		curEnt = entity;
		return true;
	}
	
	@Override
	public Entity getCurrentEntity()
	{
		return curEnt;
	}
	
	@Override
	public boolean givesLight()
	{
		return true;
	}
	
	@Override
	public boolean takesLight()
	{
		return true;
	}
	
	@Override
	public boolean canBeBrokeBy(Item item)
	{
		return false;
	}
	
	@Override
	public boolean canBreakNearby(Item item)
	{
		return true;
	}
	
	@Override
	public Tile clone()
	{
		return new TileFloor();
	}
	
	@Override
	public Tile parent()
	{
		return new TileVoid();
	}
}