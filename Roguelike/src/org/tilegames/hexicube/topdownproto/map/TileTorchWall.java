package org.tilegames.hexicube.topdownproto.map;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileTorchWall extends Tile
{
	@Override
	public boolean onWalkAttempt(Entity entity)
	{
		return false;
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		batch.setColor(1, 1, 1, 1);
		batch.draw(Game.tileTex, Game.width/2 + x * 32 - 16, Game.height/2 + y * 32 - 16, 32, 32, 64, 0, 32, 32, false, false);
	}
	
	@Override
	public boolean setCurrentEntity(Entity entity)
	{
		return false;
	}
	
	@Override
	public Entity getCurrentEntity()
	{
		return null;
	}
	
	@Override
	public boolean givesLight()
	{
		return true;
	}
	
	@Override
	public boolean takesLight()
	{
		return false;
	}
	
	@Override
	public boolean canBeBrokeBy(Item item)
	{
		return true;
	}
	
	@Override
	public boolean canBreakNearby(Item item)
	{
		return true;
	}
	
	@Override
	public Tile clone()
	{
		return this;
	}
	
	@Override
	public Tile parent()
	{
		return new TileFloor();
	}
}