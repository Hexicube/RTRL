package org.tilegames.hexicube.topdownproto.entity;

public class EffectInvisibilityPotion extends Effect
{
	private int timeLeft;
	
	public EffectInvisibilityPotion(int time)
	{
		timeLeft = time;
	}
	
	@Override
	public void tick(Entity entity)
	{
		timeLeft--;
	}
	
	@Override
	public EffectType getEffectType()
	{
		return EffectType.INVISIBLE;
	}
	
	@Override
	public int getEffectStrength()
	{
		return (timeLeft > 0) ? 1 : 0;
	}
	
	@Override
	public int timeRemaining()
	{
		return timeLeft;
	}
	
	@Override
	public void setTimeRemaining(int val)
	{
		timeLeft = val;
	}
}