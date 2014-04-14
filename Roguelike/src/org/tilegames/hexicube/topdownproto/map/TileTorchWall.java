package org.tilegames.hexicube.topdownproto.map;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;

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
		batch.draw(Game.tileTex, Game.xOffset + x * 32, Game.yOffset + y * 32, 32, 32, 64, 0, 32, 32, false, false);
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
	public void use(Entity entity)
	{}
}