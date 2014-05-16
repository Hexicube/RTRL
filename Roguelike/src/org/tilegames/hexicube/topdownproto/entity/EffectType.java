package org.tilegames.hexicube.topdownproto.entity;

public enum EffectType
{
	HARM("Harm"), SLOW("Slow"), HEAL("Heal"), SPEED("Speed"),
	INVISIBLE("Invisibility"), INVULNERABLE("Invulnerability"),
	GHOSTLY("Spectral");
	
	public String displayName;
	
	private EffectType(String name)
	{
		displayName = name;
	}
}