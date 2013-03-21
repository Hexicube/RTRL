package org.tilegames.hexicube.topdownproto.map;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileWall extends Tile
{
	@Override
	public boolean onWalkAttempt(Entity entity)
	{
		return false;
	}

	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		batch.draw(Game.tileTex, Game.xOffset+x*32, Game.yOffset+y*32, 32, 32, 32, 0, 32, 32, false, false);
	}
	
	@Override
	public void setCurrentEntity(Entity entity) {}
	
	@Override
	public Entity getCurrentEntity()
	{
		return null;
	}
	
	@Override
	public boolean lightable()
	{
		return false;
	}
	
	@Override
	public void use(Entity entity) {}
}