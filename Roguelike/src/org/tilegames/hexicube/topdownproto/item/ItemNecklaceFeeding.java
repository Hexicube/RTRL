package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;

public class ItemNecklaceFeeding extends ItemAccessory
{
	private int durability;
	
	public ItemNecklaceFeeding()
	{
		durability = getMaxDurability();
	}
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	@Override
	public String getName()
	{
		return "Necklace of Feeding";
	}
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(equipped && entity instanceof EntityPlayer)
		{
			((EntityPlayer)entity).hungerLevel++;
			durability--;
		}
	}
	@Override
	public int getItemID()
	{
		return 1;
	}
	@Override
	public AccessorySlot getAccessoryType()
	{
		return AccessorySlot.NECKLACE;
	}
	@Override
	public int getMaxDurability()
	{
		return 54000;
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
}