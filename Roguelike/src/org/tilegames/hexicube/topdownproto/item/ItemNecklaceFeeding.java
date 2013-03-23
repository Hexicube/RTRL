package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;

public class ItemNecklaceFeeding extends ItemAccessory
{
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
	public void tick(Entity entity)
	{
		if(entity instanceof EntityPlayer) ((EntityPlayer)entity).hungerLevel++;
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
}