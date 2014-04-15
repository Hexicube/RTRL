package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityDamageHealthDisplay extends Entity
{
	private boolean damage;
	private double amount;
	
	private int renderOffset;
	
	private short timeLived;
	
	public EntityDamageHealthDisplay(boolean damage, double amount, int x, int y)
	{
		this.damage = damage;
		this.amount = amount;
		timeLived = 0;
		xPos = x;
		yPos = y;
		renderOffset = 0;
	}
	
	@Override
	public void tick()
	{
		timeLived++;
		if(timeLived >= 60) Game.removeEntity(this);
		else renderOffset++;
	}
	
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		if(amount == 0) batch.setColor(1, 1, 1, (float) (60 - timeLived) / 60);
		else if(damage) batch.setColor(1, 0, 0, (float) (60 - timeLived) / 60);
		else batch.setColor(0, 1, 0, (float) (60 - timeLived) / 60);
		char[] data = FontHolder.getCharList(Game.numToStr((int) amount, 0, "-"));
		FontHolder.render(batch, data, Game.xOffset + (xPos - camX) * 32 - FontHolder.getTextWidth(data, false) / 2 + 16, Game.yOffset + (yPos - camY) * 32 + renderOffset + 24, false);
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