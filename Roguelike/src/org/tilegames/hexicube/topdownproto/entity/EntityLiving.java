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
	
	private ArrayList<Effect> effects;
	
	public abstract double damageAfterResistance(double damage, DamageType type);
	
	public abstract boolean mountable(Entity mounter);
	
	public EntityLiving()
	{
		effects = new ArrayList<Effect>();
	}
	
	@Override
	public void tick()
	{
		ticksSinceLastDamage++;
		Object[] o = effects.toArray();
		for(int a = 0; a < o.length; a++)
		{
			Effect e = (Effect) o[a];
			if(e.timeRemaining() <= 0) effects.remove(e);
			else e.tick(this);
		}
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
	
	public void addEffect(Effect effect)
	{
		int size = effects.size();
		for(int a = 0; a < size; a++)
		{
			Effect e = effects.get(a);
			if(e.getEffectType() == effect.getEffectType())
			{
				if(e.getEffectStrength() == effect.getEffectStrength())
				{
					e.setTimeRemaining(Math.max(e.timeRemaining(), effect.timeRemaining()));
					return;
				}
				if(e.getEffectStrength() < effect.getEffectStrength())
				{
					if(e.timeRemaining() <= effect.timeRemaining()) effects.remove(e);
					effects.add(effect);
					return;
				}
				if(e.timeRemaining() < effect.timeRemaining()) effects.add(effect);
				return;
			}
		}
		effects.add(effect);
	}
	
	public Effect getEffect(EffectType type)
	{
		Effect effect = null;
		int size = effects.size();
		for(int a = 0; a < size; a++)
		{
			Effect e = effects.get(a);
			if(e.getEffectType() == type)
			{
				if(effect == null || e.getEffectStrength() > effect.getEffectStrength()) effect = e;
			}
		}
		return effect;
	}
}