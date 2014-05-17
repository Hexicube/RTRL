package org.tilegames.hexicube.topdownproto.map;

import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.item.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tile
{
	public abstract boolean onWalkAttempt(Entity entity);
	
	public abstract void render(SpriteBatch batch, int x, int y);
	
	public abstract boolean setCurrentEntity(Entity entity);
	
	public abstract Entity getCurrentEntity();
	
	public abstract boolean givesLight();
	
	public abstract boolean takesLight();
	
	public abstract boolean use(Entity entity);
	
	public abstract boolean canBeBrokeBy(Item item);
	public abstract boolean canBreakNearby(Item item);
	
	public abstract Tile clone();
	public abstract Tile parent();
	
	public int[] lightLevel = new int[3];
	public int[] lightSource = new int[3];
	
	public Map map;
}