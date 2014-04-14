package org.tilegames.hexicube.topdownproto.entity;

public enum DamageType
{
	GENERIC(null, null, 0), BLUNT("Blunt", "Shoddy", 1), SHARP("Sharp", "Pointy", 2),
	FIRE("Fire", "Flaming", 3), ICE("Ice", "Frosty", 4), ACID("Acid", "Acidic", 5),
	EXPLOSIVE("Explosive", "Unstable", 6), CRUSHING("Crushing", null, 7), FALLING("Falling", null, 8);
	
	public int ID;
	public String regularName, weaponName;
	
	private DamageType(String regularName, String weaponName, int ID)
	{
		this.ID = ID;
		this.regularName = regularName;
		this.weaponName = weaponName;
	}
}