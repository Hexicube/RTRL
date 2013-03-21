package org.tilegames.hexicube.topdownproto.map;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.KeyType;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileDoor extends Tile
{
	private boolean opened, horizontal;
	
	private KeyType requiredKey;
	
	private Entity curEnt;
	
	@Override
	public boolean onWalkAttempt(Entity entity)
	{
		if(entity instanceof EntityPlayer && !opened) Game.message("You need to open the door first!");
		return opened;
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
	}
	
	@Override
	public void setCurrentEntity(Entity entity)
	{
		curEnt = entity;
	}
	
	@Override
	public Entity getCurrentEntity()
	{
		return curEnt;
	}
	
	@Override
	public boolean lightable()
	{
		return true;
	}
	
	@Override
	public void use(Entity entity)
	{
		if(requiredKey == KeyType.NONE)
		{
			if(opened)
			{
				if(curEnt == null) opened = false;
				else Game.message("Something is blocking the door!");
			}
			else opened = true;
		}
	}
}