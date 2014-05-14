package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityArrow extends Entity
{
	private int damageSides, damageDice;
	private Direction direction;
	private int timer, flightTime;
	private DamageType type;
	
	private static Texture tex = Game.loadImage("entity/arrow");
	
	public EntityArrow(int x, int y, int dmgS, int dmgD, Direction dir, DamageType type)
	{
		damageSides = dmgS;
		damageDice = dmgD;
		direction = dir;
		timer = 5;
		xPos = x;
		yPos = y;
		this.type = type;
		flightTime = 0;
	}
	
	@Override
	public void tick()
	{
		if(map == null) return;
		timer--;
		if(timer == 0)
		{
			timer = 5;
			int oldX = xPos, oldY = yPos;
			move(direction);
			flightTime++;
			if((oldX == xPos && oldY == yPos) || flightTime >= 10) Game.removeEntity(this);
		}
	}
	
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		int texX = 0, texY = 0;
		if(direction == Direction.DOWN || direction == Direction.RIGHT) texX += 32;
		if(direction == Direction.LEFT || direction == Direction.DOWN) texX += 64;
		texY = type.ID * 32;
		batch.draw(tex, Game.width/2 + (xPos - camX) * 32 - 16, Game.height/2 + (yPos - camY) * 32 - 16, 32, 32, texX, texY, 32, 32, false, false);
	}
	
	@Override
	public void collide(Entity entity)
	{
		if(entity instanceof EntityLiving)
		{
			((EntityLiving)entity).hurt(Game.rollDice(damageSides, damageDice), type);
		}
		Game.removeEntity(this);
	}
	
	@Override
	public boolean visible(Entity looker)
	{
		return true;
	}
}