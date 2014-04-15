package org.tilegames.hexicube.topdownproto.entity;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

public abstract class EntityLiving extends Entity
{
	public double health, healthMax;
	public boolean alive;
	
	public ArrayList<Effect> effects;
	
	public abstract double damageAfterResistance(double damage, DamageType type);
	
	public abstract boolean mountable(Entity mounter);
	
	public double hurt(double damage, DamageType type)
	{
		if(damage < 0) return 0;
		damage = damageAfterResistance(damage, type);
		System.out.println(damage);
		if(damage < 0) return -heal(damage);
		else
		{
			health -= damage;
			Game.addEntity(new EntityDamageHealthDisplay(true, damage, xPos, yPos), map, false);
			if(health < 0)
			{
				damage += health;
				health = 0;
			}
			health = Math.round(health*1000)/1000;
			alive = (health > 0);
			return damage;
		}
	}
	
	public double heal(double amount)
	{
		if(amount < 0) return 0;
		double max = healthMax - health;
		if(max == 0)
		{
			Game.addEntity(new EntityDamageHealthDisplay(true, 0, xPos, yPos), map, false);
			return 0;
		}
		if(max < amount) amount = max;
		health += amount;
		Game.addEntity(new EntityDamageHealthDisplay(false, amount, xPos, yPos), map, false);
		health = Math.round(health*1000)/1000;
		return amount;
	}
	
	@Override
	public boolean visible(Entity looker)
	{
		int size = effects.size();
		for(int a = 0; a < size; a++)
		{
			Effect e = effects.get(a);
			if(e.getEffectType() == EffectType.INVISIBLE && e.getEffectStrength() > 0) return false;
		}
		return true;
	}
}