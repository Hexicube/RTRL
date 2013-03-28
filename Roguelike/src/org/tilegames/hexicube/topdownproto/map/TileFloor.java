package org.tilegames.hexicube.topdownproto.map;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityChest;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemStack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileFloor extends Tile
{
	private Entity curEnt;
	
	@Override
	public boolean onWalkAttempt(Entity entity)
	{
		return true;
	}

	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		batch.draw(Game.tileTex, Game.xOffset+x*32, Game.yOffset+y*32, 32, 32, 0, 0, 32, 32, false, false);
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
	public void use(Entity entity)
	{
		if(curEnt instanceof EntityChest && entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entity;
			ArrayList<Item> items = ((EntityChest)curEnt).contents;
			if(items.size() == 0)
			{
				Game.message("The chest is empty!");
				Game.removeEntity(curEnt);
			}
			else
			{
				while(items.size() > 0)
				{
					boolean found = false;
					for(int a = 0; a < player.inventory.length && !found; a++)
					{
						if(player.inventory[a] instanceof ItemStack)
						{
							Item i = items.get(0);
							if(((ItemStack)player.inventory[a]).canStack(i))
							{
								int count = ((ItemStack)player.inventory[a]).stackItem(i);
								if(!(i instanceof ItemStack))
								{
									items.remove(0);
									found = true;
								}
								else
								{
									ItemStack i2 = (ItemStack)i;
									if(i2.getStackSize() == 0)
									{
										items.remove(0);
										found = true;
									}
								}
								Game.message("Collected item: "+player.inventory[a].getName()+((count!=1)?" x"+count:""));
							}
						}
						else if(player.inventory[a] == null)
						{
							player.inventory[a] = items.remove(0);
							int size = 1;
							if(player.inventory[a] instanceof ItemStack)
							{
								size = ((ItemStack)player.inventory[a]).getStackSize();
							}
							Game.message("Collected item: "+player.inventory[a].getName()+((size!=1)?" x"+size:""));
							found = true;
						}
					}
					if(!found)
					{
						Game.message("Your inventory is full!");
						return;
					}
				}
				if(items.size() == 0) Game.removeEntity(curEnt);
			}
		}
	}
}