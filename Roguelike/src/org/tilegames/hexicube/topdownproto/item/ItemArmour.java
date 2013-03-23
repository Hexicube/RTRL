package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.DamageType;

public abstract class ItemArmour extends ItemExpirable
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
	
	public abstract double getProtectionMod(DamageType type);
	public abstract ArmourSlot getArmourType();
}