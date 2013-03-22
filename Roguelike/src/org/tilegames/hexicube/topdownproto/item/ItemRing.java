package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.DamageType;

public abstract class ItemRing extends Item
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
}