package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.map.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityDamageHealthDisplay extends Entity
{
	private boolean damage;
	public double amount;
	
	public short timeLived;
	public boolean compound;
	
	public EntityDamageHealthDisplay(Map map, boolean damage, double amount, int x, int y)
	{
		this.map = map;
		this.damage = damage;
		this.amount = amount;
		timeLived = 0;
		xPos = x;
		yPos = y;
	}
	
	@Override
	public void tick()
	{
		timeLived++;
		if(timeLived >= 60) Game.removeEntity(this);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		if(amount < 1) batch.setColor(1, 1, 1, (float) (60 - timeLived) / 60);
		else if(damage)
		{
			if(compound) batch.setColor(1, 1, 0, (float) (60 - timeLived) / 60);
			else batch.setColor(1, 0, 0, (float) (60 - timeLived) / 60);
		}
		else batch.setColor(0, 1, 0, (float) (60 - timeLived) / 60);
		char[] data = FontHolder.getCharList(Game.numToStr(amount));
		FontHolder.render(batch, data, Game.width/2 + x * 32 - FontHolder.getTextWidth(data, true) / 2 + 1, Game.height/2 + y * 32 + 8, true);
	}
	
	@Override
	public void collide(Entity entity)
	{}
	
	@Override
	public boolean visible(Entity looker)
	{
		return false;
	}
}