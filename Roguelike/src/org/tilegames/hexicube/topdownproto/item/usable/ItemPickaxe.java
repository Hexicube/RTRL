package org.tilegames.hexicube.topdownproto.item.usable;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;
import org.tilegames.hexicube.topdownproto.map.TileDoor;
import org.tilegames.hexicube.topdownproto.map.TileFloor;
import org.tilegames.hexicube.topdownproto.map.TileTorchWall;
import org.tilegames.hexicube.topdownproto.map.TileVoid;
import org.tilegames.hexicube.topdownproto.map.TileWall;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemPickaxe extends ItemUsable
{
	private static Texture tex = Game.loadImage("item/pickaxe");
	
	private int durability;
	
	public ItemPickaxe()
	{
		durability = 30;
	}
	
	@Override
	public boolean use(Entity source, Direction dir)
	{
		int targX = source.xPos, targY = source.yPos;
		if(dir == Direction.NONE) return false;
		else if(dir == Direction.UP)
		{
			if(targY >= source.map.tiles[source.xPos].length - 2) return false;
			targY++;
		}
		else if(dir == Direction.DOWN)
		{
			if(targY <= 1) return false;
			targY--;
		}
		else if(dir == Direction.LEFT)
		{
			if(targX <= 1) return false;
			targX--;
		}
		else if(dir == Direction.RIGHT)
		{
			if(targX >= source.map.tiles.length - 2) return false;
			targX++;
		}
		else return false;
		if(source.map.tiles[targX][targY] instanceof TileWall || source.map.tiles[targX][targY] instanceof TileTorchWall || source.map.tiles[targX][targY] instanceof TileDoor)
		{
			if(source.map.tiles[targX + 1][targY] instanceof TileDoor)
			{
				Game.message("Remove the door first!");
				return false;
			}
			if(source.map.tiles[targX - 1][targY] instanceof TileDoor)
			{
				Game.message("Remove the door first!");
				return false;
			}
			if(source.map.tiles[targX][targY + 1] instanceof TileDoor)
			{
				Game.message("Remove the door first!");
				return false;
			}
			if(source.map.tiles[targX][targY - 1] instanceof TileDoor)
			{
				Game.message("Remove the door first!");
				return false;
			}
			durability--;
			source.map.tiles[targX][targY] = new TileFloor();
			for(int x = targX - 1; x <= targX + 1; x++)
			{
				for(int y = targY - 1; y <= targY + 1; y++)
				{
					if(source.map.tiles[x][y] instanceof TileVoid) source.map.tiles[x][y] = new TileWall();
				}
			}
			Game.removeLight(source.map, targX, targY);
			source.map.updateTexture(targX, targY);
			Game.updateLighting(source.map);
			source.map.needsLighting = true;
			if(durability == 0) Game.message("The Pickaxe broke...");
			return true;
		}
		return false;
	}
	
	@Override
	public int useDelay()
	{
		return 60;
	}
	
	@Override
	public boolean isWeapon()
	{
		return false;
	}
	
	@Override
	public DamageType getAttackType()
	{
		return null;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return ItemModifier.NONE;
	}
	
	@Override
	public String getName()
	{
		return "Pickaxe";
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{}
	
	@Override
	public int getMaxDurability()
	{
		return 30;
	}
	
	@Override
	public int getCurrentDurability()
	{
		return durability;
	}
	
	@Override
	public boolean canMove()
	{
		return true;
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y);
	}
}