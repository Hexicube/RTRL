package org.tilegames.hexicube.topdownproto.item.usable;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityLiving;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemPotionHealing extends ItemUsable
{
	public static Texture tex;
	
	private static boolean nameDiscovered = false;
	
	private boolean used;
	
	@Override
	public boolean use(Entity source, Direction dir)
	{
		Entity target = null;
		if(dir == Direction.NONE)
		{
			if(source instanceof EntityLiving)
			{
				((EntityLiving) source).heal(25);
				if(source instanceof EntityPlayer && !nameDiscovered)
				{
					nameDiscovered = true;
					Game.message("Discovered potion type: Healing");
				}
				used = true;
				return true;
			}
			return false;
		}
		else if(dir == Direction.UP) target = source.map.tiles[source.xPos][source.yPos + 1].getCurrentEntity();
		else if(dir == Direction.DOWN) target = source.map.tiles[source.xPos][source.yPos - 1].getCurrentEntity();
		else if(dir == Direction.LEFT) target = source.map.tiles[source.xPos - 1][source.yPos].getCurrentEntity();
		else if(dir == Direction.RIGHT) target = source.map.tiles[source.xPos + 1][source.yPos].getCurrentEntity();
		else return false;
		if(target == null) return false;
		if(!(target instanceof EntityLiving)) return false;
		EntityLiving e = (EntityLiving) target;
		if(!e.alive) return false;
		e.heal(25);
		if(source instanceof EntityPlayer && !nameDiscovered)
		{
			nameDiscovered = true;
			Game.message("Discovered potion type: Healing");
		}
		used = true;
		return true;
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
		return null;
	}
	
	@Override
	public String getName()
	{
		if(nameDiscovered) return "Healing Potion";
		return "Unknown Potion";
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{}
	
	@Override
	public int getMaxDurability()
	{
		return 1;
	}
	
	@Override
	public int getCurrentDurability()
	{
		return used ? 0 : 1;
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
	
	@Override
	public int useDelay()
	{
		return 15;
	}
}