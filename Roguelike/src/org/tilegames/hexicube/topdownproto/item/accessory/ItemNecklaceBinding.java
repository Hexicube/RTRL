package org.tilegames.hexicube.topdownproto.item.accessory;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemNecklaceBinding extends ItemAccessory
{
	public static boolean nameDiscovered = false;
	private int durability;
	
	public ItemNecklaceBinding()
	{
		durability = 50;
	}
	
	@Override
	public String[] getCustomActions(Item other)
	{
		return new String[0];
	}
	
	@Override
	public void handleCustomAction(String action, Item other) {}
	
	@Override
	public AccessorySlot getAccessoryType()
	{
		return AccessorySlot.NECKLACE;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return ItemModifier.NONE;
	}
	
	@Override
	public String getName()
	{
		return "Necklace of Sacred Bindings";
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(entity instanceof EntityPlayer && equipped)
		{
			EntityPlayer p = (EntityPlayer)entity;
			if(durability > 0)
			{
				if(p.heldItem != null && p.heldItem.purify())
				{
					Game.message("Your "+p.heldItem.getName()+" was purified.");
					durability--;
				}
			}
			for(Item i : p.armour)
			{
				if(durability > 0)
				{
					if(i != null && i.purify())
					{
						Game.message("Your "+i.getName()+" was purified.");
						durability--;
					}
				}
			}
			if(durability > 0)
			{
				if(p.ring1 != null && p.ring1.purify())
				{
					Game.message("Your "+p.ring1.getName()+" was purified.");
					durability--;
				}
			}
			if(durability > 0)
			{
				if(p.ring2 != null && p.ring2.purify())
				{
					Game.message("Your "+p.ring2.getName()+" was purified.");
					durability--;
				}
			}
			if(durability > 0)
			{
				if(p.bracelet1 != null && p.bracelet1.purify())
				{
					Game.message("Your "+p.bracelet1.getName()+" was purified.");
					durability--;
				}
			}
			if(durability > 0)
			{
				if(p.bracelet2 != null && p.bracelet2.purify())
				{
					Game.message("Your "+p.bracelet2.getName()+" was purified.");
					durability--;
				}
			}
			if(durability <= 0)
			{
				Game.message("The "+getName()+" broke...");
			}
		}
	}
	
	@Override
	public int getMaxDurability()
	{
		return 50;
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
	public boolean purify()
	{
		return false;
	}
	
	@Override
	public Color getInvBorderCol()
	{
		return new Color(0, 0, 0, 0);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(ItemNecklaceStrangle.tex, x, y, 32, 32, equipped ? 32 : 0, 0, 32, 32, false, false);
	}
}