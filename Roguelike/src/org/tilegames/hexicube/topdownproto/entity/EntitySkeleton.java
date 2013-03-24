package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntitySkeleton extends EntityLiving
{
	private static Texture tex = Game.loadImage("skeleton");
	
	private int movementTimer;
	
	public EntitySkeleton()
	{
		health = healthMax = 100;
		alive = true;
	}
	
	@Override
	public long damageAfterResistance(long damage, DamageType type)
	{
		if(type == DamageType.ACID) return damage * 2;
		if(type == DamageType.BLUNT) return (long) (damage * 1.5);
		if(type == DamageType.CRUSHING) return damage * 2;
		if(type == DamageType.EXPLOSIVE) return damage * 5;
		if(type == DamageType.FALLING) return damage * 2;
		if(type == DamageType.FIRE) return 0;
		if(type == DamageType.GENERIC) return damage;
		if(type == DamageType.ICE) return (long) (damage * 0.4);
		if(type == DamageType.SHARP) return (long) (damage * 0.2);
		return 0;
	}
	@Override
	public void tick()
	{
		if(!alive)
		{
			//TODO: die
			return;
		}
		//TODO: many things
	}
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
	}
	@Override
	public void collide(Entity entity) {}
}