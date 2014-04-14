package org.tilegames.hexicube.topdownproto.item.armour;

import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.item.Item;

public abstract class ItemArmour extends Item
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