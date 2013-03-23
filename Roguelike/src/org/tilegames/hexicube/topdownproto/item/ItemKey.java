package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Entity;

public class ItemKey extends Item
{
	public KeyType type;
	
	public ItemKey(KeyType type)
	{
		this.type = type;
	}
	
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
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	@Override
	public String getName()
	{
		return type+" Key";
	}
	@Override
	public void tick(Entity entity) {}
	@Override
	public int getItemID()
	{
		if(type == KeyType.RED) return 2;
		if(type == KeyType.ORANGE) return 3;
		if(type == KeyType.YELLOW) return 4;
		if(type == KeyType.GREEN) return 5;
		if(type == KeyType.BLUE) return 6;
		if(type == KeyType.VIOLET) return 7;
		if(type == KeyType.SKELETON) return 8;
		return -1;
	}
}