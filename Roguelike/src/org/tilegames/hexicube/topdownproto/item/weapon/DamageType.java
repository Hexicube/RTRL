package org.tilegames.hexicube.topdownproto.item.weapon;

public enum DamageType
{
	GENERIC(0), BLUNT(1), SHARP(2),	FIRE(3), ICE(4),
	ACID(5), EXPLOSIVE(6), CRUSHING(7), FALLING(8);
	
	public int ID;
	
	private DamageType(int ID)
	{
		this.ID = ID;
	}
}