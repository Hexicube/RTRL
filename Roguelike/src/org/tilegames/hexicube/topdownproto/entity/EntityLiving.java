package org.tilegames.hexicube.topdownproto.entity;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

public abstract class EntityLiving extends Entity
{
	public double health, healthMax;
	public boolean alive;
	
	private int ticksSinceLastDamage = 51;
	private EntityDamageHealthDisplay lastDamageIndicator;
	
	public ArrayList<Effect> effects;
	
	public abstract double damageAfterResistance(double damage, DamageType type);
	
	public abstract boolean mountable(Entity mounter);
	
	@Override
	public void tick()
	{
		ticksSinceLastDamage++;
	}
	
	public double hurt(double damage, DamageType type)
	{
		if(damage < 0) return 0;
		damage = damageAfterResistance(damage, type);
		if(damage < 0) return -heal(-damage);
		else
		{
			health -= damage;
			if(ticksSinceLastDamage <= 50)
			{
				lastDamageIndicator.amount += damage;
				lastDamageIndicator.timeLived = 0;
				lastDamageIndicator.xPos = xPos;
				lastDamageIndicator.yPos = yPos;
				lastDamageIndicator.compound = true;
			}
			else
			{
				lastDamageIndicator = new EntityDamageHealthDisplay(map, true, damage, xPos, yPos);
				Game.addEntity(lastDamageIndicator, map, false);
			}
			ticksSinceLastDamage = 0;
			if(health < 0)
			{
				damage += health;
				health = 0;
			}
			health = (double)Math.round(health*1000)/1000;
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
			Game.addEntity(new EntityDamageHealthDisplay(map, true, 0, xPos, yPos), map, false);
			return 0;
		}
		if(max < amount) amount = max;
		health += amount;
		Game.addEntity(new EntityDamageHealthDisplay(map, false, amount, xPos, yPos), map, false);
		health = (double)Math.round(health*1000)/1000;
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