package org.tilegames.hexicube.topdownproto.item.usable;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemPotionMana extends ItemUsable
{
	public static Texture tex;
	
	private static boolean nameDiscovered = false;
	
	private boolean used;
	
	@Override
	public boolean use(Entity source, Direction dir)
	{
		if(dir == Direction.NONE)
		{
			if(source instanceof EntityPlayer)
			{
				if(!nameDiscovered)
				{
					nameDiscovered = true;
					Game.message("Discovered potion type: Mana");
				}
				EntityPlayer p = (EntityPlayer) source;
				used = true;
				p.mana += 25;
				if(p.mana > p.manaMax) p.mana = p.manaMax;
				return true;
			}
			return false;
		}
		return false;
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
		if(nameDiscovered) return "Mana Potion";
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