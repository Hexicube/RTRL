package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Entity;

public class ItemWeaponTestSword extends ItemWeapon
{
	private int durability;
	private ItemModifier modifier;
	private int[] dmgRng;
	
	public ItemWeaponTestSword()
	{
		int rand = Game.rand.nextInt(10);
		if(rand < 6) modifier = ItemModifier.NONE;
		else if(rand < 9) modifier = ItemModifier.CURSED;
		else modifier = ItemModifier.SHARPENED;

		dmgRng = new int[]{3, 5};
		if(modifier == ItemModifier.SHARPENED)
		{
			dmgRng[0] += 2;
			dmgRng[1] += 3;
		}
	}
	
	public ItemWeaponTestSword(ItemModifier mod, int dur)
	{
		modifier = mod;
		durability = dur;
		dmgRng = new int[]{3, 5};
		if(modifier == ItemModifier.SHARPENED)
		{
			dmgRng[0] += 2;
			dmgRng[1] += 3;
		}
	}
	
	@Override
	public boolean use(Entity source, int direction)
	{
		//TODO: check for hitting something
		if(durability <= 0) return false;
		durability--;
		return true;
	}
	@Override
	public int[] getWeaponDamageRange()
	{
		return dmgRng;
	}
	@Override
	public int getMaxDurability()
	{
		return 200;
	}

	@Override
	public int getCurrentDurability()
	{
		return durability;
	}

	@Override
	public DamageType getAttackType()
	{
		return DamageType.SHARP;
	}
	@Override
	public ItemModifier getModifier()
	{
		return modifier;
	}
	@Override
	public String getName()
	{
		if(modifier == null || modifier == ItemModifier.NONE) return "Test Sword";
		if(modifier == ItemModifier.CURSED) return "Cursed Test Sword";
		if(modifier == ItemModifier.RESTRICTIVE)
		{
			modifier = ItemModifier.NONE;
			return "Test Sword";
		}
		if(modifier == ItemModifier.SHARPENED) return "Sharpened Test Sword";
		if(modifier == ItemModifier.WEIGHTED)
		{
			modifier = ItemModifier.NONE;
			return "Test Sword";
		}
		return "???";
	}
	@Override
	public void tick() {}
	@Override
	public int getItemID()
	{
		return 0;
	}
}