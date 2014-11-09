package org.tilegames.hexicube.topdownproto.map;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityChest;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tile
{
	public abstract boolean onWalkAttempt(Entity entity);
	
	public abstract void render(SpriteBatch batch, int x, int y);
	
	public abstract boolean setCurrentEntity(Entity entity);
	
	public abstract Entity getCurrentEntity();
	
	public abstract boolean givesLight();
	
	public abstract boolean takesLight();
	
	protected Entity curEnt;
	public boolean use(Entity entity)
	{
		if(curEnt instanceof EntityChest && entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			ArrayList<Item> items = ((EntityChest) curEnt).contents;
			if(items.size() == 0)
			{
				Game.message("The chest is empty!");
				Game.removeEntity(curEnt);
			}
			else
			{
				while(items.size() > 0)
				{
					Item i = items.get(0);
					if(player.giveItem(i))
					{
						Game.message("Collected item: "+i.getName());
						items.remove(0);
					}
					else
					{
						Game.message("Your inventory is full!");
						break;
					}
				}
				if(items.size() == 0) Game.removeEntity(curEnt);
			}
			return true;
		}
		return false;
	}
	
	public abstract boolean canBeBrokeBy(Item item);
	public abstract boolean canBreakNearby(Item item);
	
	public abstract Tile clone();
	public abstract Tile parent();
	
	public int[] lightLevel = new int[3];
	public int[] lightSource = new int[3];
	
	public Map map;
}