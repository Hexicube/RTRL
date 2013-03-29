package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityLeechBolt extends Entity
{
	private static Texture tex = Game.loadImage("entity/leechbolt");
	
	private Direction direction;
	private int timer;
	private Entity source;
	
	public EntityLeechBolt(Direction dir, Entity src, int x, int y)
	{
		direction = dir;
		source = src;
		timer = 5;
		xPos = x;
		yPos = y;
	}
	@Override
	public void tick()
	{
		timer--;
		if(timer == 0)
		{
			timer = 5;
			move(direction);
		}
	}
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		batch.draw(tex, Game.xOffset+(xPos-camX)*32, Game.yOffset+(yPos-camY)*32);
	}
	@Override
	public void collide(Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			int dmg = ((EntityLiving)entity).hurt(Game.rollDice(10, 2), DamageType.GENERIC);
			if(source instanceof EntityLiving) ((EntityLiving)source).heal(dmg);
		}
		Game.removeEntity(this);
	}
	@Override
	public boolean visible(Entity looker)
	{
		return true;
	}
}