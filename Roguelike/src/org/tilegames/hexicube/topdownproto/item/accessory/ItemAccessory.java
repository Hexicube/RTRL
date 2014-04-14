package org.tilegames.hexicube.topdownproto.item.accessory;

import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.item.Item;

public abstract class ItemAccessory extends Item
{
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
	
	public abstract AccessorySlot getAccessoryType();
}