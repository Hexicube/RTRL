package org.tilegames.hexicube.topdownproto.entity;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.map.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntitySkeleton extends EntityLiving
{
	//private static Texture tex = Game.loadImage("skeleton");
	
	private int movementTimer;
	
	private boolean chasing;
	
	public EntitySkeleton(int x, int y)
	{
		health = healthMax = 100;
		alive = true;
		xPos = x;
		yPos = y;
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
			ArrayList<Item> items = new ArrayList<Item>();
			Game.insertRandomLoot(items, this);
			Map map = this.map;
			Game.removeEntity(this);
			Game.addEntity(new EntityChest(xPos, yPos, items), map, true);
			return;
		}
		if(movementTimer == 0)
		{
			movementTimer = 45;
			int xDist = Game.player.xPos - xPos;
			int yDist = Game.player.yPos - yPos;
			int dist = Math.abs(xDist) + Math.abs(yDist);
			if(dist < 6) chasing = true;
			if(dist > 10) chasing = false;
			if(chasing)
			{
				if(dist == 1)
				{
					Game.player.hurt(Game.rollDice(4, 1), DamageType.BLUNT);
				}
				else
				{
					if(yDist == 0)
					{
						move((xDist>0)?Direction.RIGHT:Direction.LEFT);
					}
					else if(xDist == 0)
					{
						move((yDist>0)?Direction.UP:Direction.DOWN);
					}
					else if(Game.rand.nextBoolean())
					{
						int oldX = xPos;
						move((xDist>0)?Direction.RIGHT:Direction.LEFT);
						if(oldX == xPos) move((yDist>0)?Direction.UP:Direction.DOWN);
					}
					else
					{
						int oldY = yPos;
						move((yDist>0)?Direction.UP:Direction.DOWN);
						if(oldY == yPos) move((xDist>0)?Direction.RIGHT:Direction.LEFT);
					}
				}
			}
			else
			{
				int oldX = xPos, oldY = yPos;
				for(int a = 0; a < 5; a++)
				{
					int rand = Game.rand.nextInt(4);
					if(rand == 0) move(Direction.UP);
					else if(rand == 1) move(Direction.DOWN);
					else if(rand == 2) move(Direction.LEFT);
					else if(rand == 3) move(Direction.RIGHT);
					if(xPos != oldX || yPos != oldY) break;
				}
			}
		}
		else movementTimer--;
	}
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		//TODO: proper rendering
		batch.draw(EntityPlayer.tex, Game.xOffset+(xPos-camX)*32, Game.yOffset+(yPos-camY)*32, 32, 32, 0, 0, 32, 32, false, false);
	}
	@Override
	public void collide(Entity entity) {}
}