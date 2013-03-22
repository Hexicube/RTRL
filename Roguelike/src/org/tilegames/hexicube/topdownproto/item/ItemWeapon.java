package org.tilegames.hexicube.topdownproto.item;

public abstract class ItemWeapon extends ItemUsable
{
	@Override
	public boolean isWeapon()
	{
		return true;
	}
	
	public abstract int[] getWeaponDamageRange();
}