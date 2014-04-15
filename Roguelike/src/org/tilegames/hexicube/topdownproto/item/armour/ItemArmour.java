package org.tilegames.hexicube.topdownproto.item.armour;

import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

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
	
	public abstract double getDamageMod(DamageType type);
	
	public abstract ArmourSlot getArmourType();
}