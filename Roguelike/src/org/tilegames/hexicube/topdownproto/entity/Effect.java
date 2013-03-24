package org.tilegames.hexicube.topdownproto.entity;

public abstract class Effect
{
	public abstract void tick(Entity entity);
	public abstract EffectType getEffectType();
	public abstract int getEffectStrength();
	public abstract int timeRemaining();
}