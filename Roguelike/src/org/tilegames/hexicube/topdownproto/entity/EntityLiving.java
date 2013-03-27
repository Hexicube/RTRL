package org.tilegames.hexicube.topdownproto.entity;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;

public abstract class EntityLiving extends Entity
{
	public long health, healthMax;
	public boolean alive;
	
	public ArrayList<Effect> effects;
	
	public abstract long damageAfterResistance(long damage, DamageType type);
	
	public void hurt(long damage, DamageType type)
	{
		if(damage < 0) return;
		damage = damageAfterResistance(damage, type);
		if(damage < 0) heal(damage);
		else
		{
			health -= damage;
			if(health < 0) health = 0;
			alive = (health > 0);
			Game.addEntity(new EntityDamageHealthDisplay(true, damage, xPos, yPos), map, false);
		}
	}
	
	public void heal(long amount)
	{
		if(amount < 0) return;
		long max = healthMax - health;
		if(max == 0) return;
		if(max < amount) amount = max;
		health += amount;
		Game.addEntity(new EntityDamageHealthDisplay(false, amount, xPos, yPos), map, false);
	}
	@Override
	public boolean visible(Entity looker)
	{
		int size = effects.size();
		for(int a = 0; a < size; a++)
		{
			if(effects.get(a).getEffectType() == EffectType.INVISIBLE && effects.get(a).getEffectStrength() > 0) return false;
		}
		return true;
	}
}