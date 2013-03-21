package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.map.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity
{
	public int xPos, yPos, width, height;
	
	public Map map;
	
	public Entity rider;
	
	public abstract void tick();
	public abstract void render(SpriteBatch batch, int camX, int camY);
	public abstract void collide(Entity entity);
	
	public void move(boolean horizontal, int amount)
	{
		int dirX = 0, dirY = 0;
		if(horizontal) dirX = amount/Math.abs(amount);
		else dirY = amount/Math.abs(amount);
		for(int a = 0; a < Math.abs(amount); a++)
		{
			int newX = xPos + dirX;
			if(newX < 0 || newX >= map.tiles.length) break;
			int newY = yPos + dirY;
			if(newY < 0 || newY >= map.tiles[newX].length) break;
			Entity e = map.tiles[newX][newY].getCurrentEntity();
			if(e != null)
			{
				e.collide(this);
				this.collide(e);
			}
			if(map.tiles[newX][newY].onWalkAttempt(this))
			{
				map.tiles[newX][newY].setCurrentEntity(this);
				xPos = newX;
				yPos = newY;
			}
		}
	}
}