package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;

public enum ArmourType
{
	LEATHER, IRON, STEEL, GOLD, CHAIN;
	
	public double getDamageMod(DamageType type)
	{
		if(type == DamageType.GENERIC) return 1;
		if(this == LEATHER)
		{
			if(type == DamageType.BLUNT) return 0.9;
			if(type == DamageType.SHARP) return 1;
			if(type == DamageType.FIRE) return 0.6;
			if(type == DamageType.ICE) return 0.4;
			if(type == DamageType.ACID) return 0.8;
			if(type == DamageType.EXPLOSIVE) return 0.9;
			if(type == DamageType.CRUSHING) return 1;
			if(type == DamageType.FALLING) return 1;
		}
		if(this == IRON)
		{
			if(type == DamageType.BLUNT) return 0.3;
			if(type == DamageType.SHARP) return 0.3;
			if(type == DamageType.FIRE) return 1.5;
			if(type == DamageType.ICE) return 0.7;
			if(type == DamageType.ACID) return 1.5;
			if(type == DamageType.EXPLOSIVE) return 0.6;
			if(type == DamageType.CRUSHING) return 0.6;
			if(type == DamageType.FALLING) return 1.3;
		}
		if(this == STEEL)
		{
			if(type == DamageType.BLUNT) return 0.3;
			if(type == DamageType.SHARP) return 0.3;
			if(type == DamageType.FIRE) return 1.5;
			if(type == DamageType.ICE) return 0.7;
			if(type == DamageType.ACID) return 3;
			if(type == DamageType.EXPLOSIVE) return 0.4;
			if(type == DamageType.CRUSHING) return 0.4;
			if(type == DamageType.FALLING) return 1.3;
		}
		if(this == GOLD)
		{
			if(type == DamageType.BLUNT) return 0.6;
			if(type == DamageType.SHARP) return 0.8;
			if(type == DamageType.FIRE) return 0.4;
			if(type == DamageType.ICE) return 0.2;
			if(type == DamageType.ACID) return 5;
			if(type == DamageType.EXPLOSIVE) return 0.5;
			if(type == DamageType.CRUSHING) return 0.8;
			if(type == DamageType.FALLING) return 1;
		}
		if(this == CHAIN)
		{
			if(type == DamageType.BLUNT) return 0.4;
			if(type == DamageType.SHARP) return 0.4;
			if(type == DamageType.FIRE) return 2;
			if(type == DamageType.ICE) return 2;
			if(type == DamageType.ACID) return 3;
			if(type == DamageType.EXPLOSIVE) return 0.2;
			if(type == DamageType.CRUSHING) return 0.7;
			if(type == DamageType.FALLING) return 1;
		}
		Game.message("Unimplemented armour/damage type: " + type + " vs " + this);
		return 1;
	}
}