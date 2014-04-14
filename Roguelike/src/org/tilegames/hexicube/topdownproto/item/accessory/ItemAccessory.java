package org.tilegames.hexicube.topdownproto.item.accessory;

import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

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