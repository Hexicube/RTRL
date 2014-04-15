package org.tilegames.hexicube.topdownproto.item.accessory;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemNecklaceManaTraining extends ItemAccessory
{
	public static Texture tex;
	
	private static boolean nameDiscovered = false;
	
	private int durability;
	private boolean modDiscovered;
	private ItemModifier mod;
	
	public ItemNecklaceManaTraining()
	{
		durability = 100;
		int val = Game.rand.nextInt(10);
		if(val < 3) mod = ItemModifier.CONSERVATIVE;
		else if(val < 9) mod = ItemModifier.SHODDY;
		else
		{
			mod = null;
			modDiscovered = true;
		}
	}
	
	@Override
	public AccessorySlot getAccessoryType()
	{
		return AccessorySlot.NECKLACE;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return mod;
	}
	
	@Override
	public String getName()
	{
		if(nameDiscovered)
		{
			if(modDiscovered)
			{
				if(mod == ItemModifier.SHODDY) return "Wasteful Necklace of Mana Exercise";
				if(mod == ItemModifier.CONSERVATIVE) return "Efficient Necklace of Mana Exercise";
				mod = null;
				return "Necklace of Mana Exercise";
			}
			else return "Unusual Necklace of Mana Exercise";
		}
		else
		{
			if(mod == null) return "Unknown Necklace";
			else return "Unusual Necklace";
		}
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(equipped && entity instanceof EntityPlayer)
		{
			if(!nameDiscovered)
			{
				nameDiscovered = true;
				Game.message("Discovered necklace: Mana Exercise");
			}
			if(!modDiscovered)
			{
				if(mod == ItemModifier.CONSERVATIVE) Game.message("You realise the Necklace of Mana Exercise is efficient...");
				if(mod == ItemModifier.SHODDY) Game.message("You realise the Necklace of Mana Exercise is wasteful...");
				modDiscovered = true;
			}
			EntityPlayer p = (EntityPlayer) entity;
			if(p.mana > 0)
			{
				p.mana--;
				durability--;
				if(mod == ItemModifier.SHODDY)
				{
					if(Game.rand.nextBoolean()) p.manaExperience++;
				}
				else if(mod == ItemModifier.CONSERVATIVE)
				{
					p.manaExperience++;
					if(Game.rand.nextBoolean()) p.manaExperience++;
					if(Game.rand.nextBoolean()) p.manaExperience++;
				}
				else p.manaExperience++;
				if(durability == 0) Game.message("The " + getName() + " broke...");
			}
		}
	}
	
	@Override
	public int getMaxDurability()
	{
		return 100;
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
	public Color getInvBorderCol()
	{
		if(!nameDiscovered) return Color.ORANGE;
		return new Color(0, 0, 0, 0);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y, 32, 32, equipped ? 32 : 0, 0, 32, 32, false, false);
	}
}