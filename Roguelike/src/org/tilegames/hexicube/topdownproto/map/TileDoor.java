package org.tilegames.hexicube.topdownproto.map;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityChest;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemKey;
import org.tilegames.hexicube.topdownproto.item.KeyType;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileDoor extends Tile
{
	private static Texture tex = Game.loadImage("door");
	
	private boolean opened, horizontal;
	
	private KeyType requiredKey;
	
	private Entity curEnt;
	
	public TileDoor(boolean horizontal, boolean forceBasic)
	{
		this.horizontal = horizontal;
		if(forceBasic) requiredKey = KeyType.NONE;
		else
		{
			int rand = Game.rand.nextInt(10);
			if(rand == 0) requiredKey = KeyType.RED;
			else if(rand == 1) requiredKey = KeyType.ORANGE;
			else if(rand == 2) requiredKey = KeyType.YELLOW;
			else if(rand == 3) requiredKey = KeyType.GREEN;
			else if(rand == 4) requiredKey = KeyType.BLUE;
			else if(rand == 5) requiredKey = KeyType.VIOLET;
			else requiredKey = KeyType.NONE;
		}
	}
	
	@Override
	public boolean onWalkAttempt(Entity entity)
	{
		if(entity instanceof EntityPlayer && !opened) use(entity);
		return opened;
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		boolean doorHorizontal = (horizontal ^ opened);
		int tileX = x + Game.camX;
		int tileY = y + Game.camY;
		if(!opened)
		{
			Tile t2 = map.tiles[doorHorizontal ? tileX : (tileX + 1)][doorHorizontal ? (tileY - 1) : tileY];
			int[] light = new int[]
			{
					t2.lightLevel[0], t2.lightLevel[1], t2.lightLevel[2]
			};
			if(light[0] == 0) light[0] = 1;
			if(light[1] == 0) light[1] = 1;
			if(light[2] == 0) light[2] = 1;
			batch.setColor((float) (light[0] + 2) / 18f, (float) (light[1] + 2) / 18f, (float) (light[2] + 2) / 18f, 1);
		}
		batch.draw(Game.tileTex, Game.width/2 + x * 32 - 16, Game.height/2 + y * 32 - 16, 32, 32, 0, 0, 32, 32, false, false);
		if(requiredKey == KeyType.NONE) batch.setColor(1, 1, 1, 1);
		else if(requiredKey == KeyType.RED) batch.setColor(1, 0, 0, 1);
		else if(requiredKey == KeyType.ORANGE) batch.setColor(1, 0.6f, 0, 1);
		else if(requiredKey == KeyType.YELLOW) batch.setColor(1, 1, 0, 1);
		else if(requiredKey == KeyType.GREEN) batch.setColor(0, 1, 0, 1);
		else if(requiredKey == KeyType.BLUE) batch.setColor(0, 0, 1, 1);
		else if(requiredKey == KeyType.VIOLET) batch.setColor(0.6f, 0, 1, 1);
		else batch.setColor(0.2f, 0.2f, 0.2f, 1);
		/* if(!opened)
		 * {
		 * Tile t2 =
		 * map.tiles[doorHorizontal?tileX:(tileX+1)][doorHorizontal?(tileY
		 * -1):tileY];
		 * Tile t3 =
		 * map.tiles[doorHorizontal?tileX:(tileX-1)][doorHorizontal?(tileY
		 * +1):tileY];
		 * int[] light = new int[]{t2.lightLevel[0], t2.lightLevel[1],
		 * t2.lightLevel[2]};
		 * if(t3.lightLevel[0] > light[0]) light[0] = t3.lightLevel[0];
		 * if(t3.lightLevel[1] > light[0]) light[1] = t3.lightLevel[1];
		 * if(t3.lightLevel[2] > light[0]) light[2] = t3.lightLevel[2];
		 * if(light[0] == 0) light[0] = 1;
		 * if(light[1] == 0) light[1] = 1;
		 * if(light[2] == 0) light[2] = 1;
		 * batch.setColor((float)(light[0]+2)/18f, (float)(light[1]+2)/18f,
		 * (float)(light[2]+2)/18f, 1);
		 * } */
		int texX = doorHorizontal ? 0 : 32;
		batch.draw(tex, Game.width/2 + x * 32 - 16, Game.height/2 + y * 32 - 16, 32, 32, texX, 0, 32, 32, false, false);
	}
	
	@Override
	public boolean setCurrentEntity(Entity entity)
	{
		if(!opened) return false;
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
		return opened;
	}
	
	@Override
	public boolean takesLight()
	{
		return opened;
	}
	
	@Override
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
					boolean found = false;
					for(int a = 0; a < player.inventory.length && !found; a++)
					{
						if(player.inventory[a] == null)
						{
							player.inventory[a] = items.remove(0);
							Game.message("Collected item: " + player.inventory[a].getName());
							found = true;
						}
					}
					if(!found)
					{
						Game.message("Your inventory is full!");
						return true;
					}
				}
				if(items.size() == 0) Game.removeEntity(curEnt);
			}
			return true;
		}
		if(requiredKey == KeyType.SECRET)
		{
			Game.message("The door can't directly be opened, try looking elsewhere!");
			return true;
		}
		if(requiredKey == KeyType.NONE)
		{
			if(opened)
			{
				if(curEnt == null)
				{
					opened = false;
					entity.map.needsLighting = true;
				}
				else Game.message("Something is blocking the door!");
			}
			else
			{
				opened = true;
				entity.map.needsLighting = true;
			}
		}
		else if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			for(int a = 0; a < 100; a++)
			{
				if(player.inventory[a] instanceof ItemKey)
				{
					ItemKey key = (ItemKey) player.inventory[a];
					if(key.type == requiredKey)
					{
						player.inventory[a] = null;
						requiredKey = KeyType.NONE;
						Game.message("Door unlocked.");
						return true;
					}
				}
			}
			Game.message("You need the " + requiredKey.name + " key to open that!");
		}
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
		return false;
	}
	
	@Override
	public Tile clone()
	{
		TileDoor door = new TileDoor(horizontal, true);
		door.requiredKey = requiredKey;
		return door;
	}
	
	@Override
	public Tile parent()
	{
		return new TileFloor();
	}
}