package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.DamageType;

public abstract class Item
{
	public abstract boolean isWeapon();
	public abstract DamageType getAttackType();
	public abstract ItemModifier getModifier();
	public abstract String getName();
	public abstract void tick();
	public abstract int getItemID();
}