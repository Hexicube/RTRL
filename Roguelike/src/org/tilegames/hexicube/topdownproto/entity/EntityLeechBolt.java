package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityLeechBolt extends Entity
{
	private static Texture tex = Game.loadImage("entity/leechbolt");
	
	private Direction direction;
	private int timer, flightTime;
	private Entity source;
	
	public EntityLeechBolt(Direction dir, Entity src, int x, int y)
	{
		direction = dir;
		source = src;
		timer = 4;
		xPos = x;
		yPos = y;
		flightTime = 0;
	}
	
	@Override
	public void tick()
	{
		if(map == null) return;
		timer--;
		if(timer == 0)
		{
			timer = 4;
			int oldX = xPos, oldY = yPos;
			move(direction);
			flightTime++;
			if((oldX == xPos && oldY == yPos) || flightTime >= 10) Game.removeEntity(this);
		}
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		batch.draw(tex, Game.width/2 + x * 32 - 16, Game.height/2 + y * 32 - 16);
	}
	
	@Override
	public void collide(Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			double dmg = ((EntityLiving) entity).hurt(Game.rollDice(10, 2), DamageType.GENERIC);
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