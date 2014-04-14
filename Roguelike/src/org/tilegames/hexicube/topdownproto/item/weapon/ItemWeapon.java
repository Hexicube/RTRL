package org.tilegames.hexicube.topdownproto.item.weapon;

import org.tilegames.hexicube.topdownproto.item.usable.ItemUsable;

public abstract class ItemWeapon extends ItemUsable
{
	@Override
	public boolean isWeapon()
	{
		return true;
	}
	
	public abstract String getWeaponDamageRange();
}