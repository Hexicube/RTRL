package org.tilegames.hexicube.topdownproto.entity;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Direction;
import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.Item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityPlayer extends EntityLiving
{	
	public static Texture tex = Game.loadImage("player");
	
	private Direction facingDir = Direction.UP;
	
	private int walkDelay, useDelay, useAnimTime, useAnimType;
	
	private Item[] inventory;
	private Item[] itemsInUse;
	
	private int hungerLevel;
	
	public ArrayList<Effect> effects;
	
	public EntityPlayer(int x, int y, int maxHP)
	{
		xPos = x;
		yPos = y;
		health = maxHP;
		healthMax = maxHP;
		inventory = new Item[100];
		itemsInUse = new Item[11];
		hungerLevel = 25200; //7 mins of food
		//main hand, off hand(shield), helmet,
		//chestplate, leggings, boots, ring1, ring2,
		//nechlace, bracelet, headband
		effects = new ArrayList<Effect>();
	}
	
	@Override
	public long damageAfterResistance(long damage, DamageType type)
	{
		//TODO: armour checks
		return damage;
	}
	
	@Override
	public void tick()
	{
		hungerLevel--;
		if(hungerLevel <= 0)
		{
			health = 0;
			return;
		}
		if(Game.keyPress[45])
		{
			//TODO: open inventory screen
		}
		if(Game.keyPress[33])
		{
			if(facingDir == Direction.DOWN)
			{
				if(yPos > 0) map.tiles[xPos][yPos-1].use(this);
			}
			if(facingDir == Direction.UP)
			{
				if(yPos < map.tiles[xPos].length-1) map.tiles[xPos][yPos+1].use(this);
			}
			if(facingDir == Direction.LEFT)
			{
				if(xPos > 0) map.tiles[xPos-1][yPos].use(this);
			}
			if(facingDir == Direction.RIGHT)
			{
				if(xPos < map.tiles.length-1) map.tiles[xPos+1][yPos].use(this);
			}
		}
		if(Game.keysDown[19])
		{
			if(walkDelay == 0)
			{
				walkDelay = 15;
				move(false, 1);
			}
			facingDir = Direction.UP;
		}
		else if(Game.keysDown[20])
		{
			if(walkDelay == 0)
			{
				walkDelay = 15;
				move(false, -1);
			}
			facingDir = Direction.DOWN;
		}
		else if(Game.keysDown[21])
		{
			if(walkDelay == 0)
			{
				walkDelay = 15;
				move(true, -1);
			}
			facingDir = Direction.LEFT;
		}
		else if(Game.keysDown[22])
		{
			if(walkDelay == 0)
			{
				walkDelay = 15;
				move(true, 1);
			}
			facingDir = Direction.RIGHT;
		}
		if(walkDelay > 0) walkDelay--;
		Game.camX = xPos;
		Game.camY = yPos;
	}
	
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		int texX = 0, texY = 0;
		if(facingDir == Direction.DOWN || facingDir == Direction.RIGHT) texX += 32;
		if(facingDir == Direction.LEFT || facingDir == Direction.DOWN) texY += 32;
		batch.draw(tex, Game.xOffset+(xPos-camX)*32, Game.yOffset+(yPos-camY)*32, 32, 32, texX, texY, 32, 32, false, false);
		//TODO: animation render (do I want to have animations?)
	}
	
	@Override
	public void collide(Entity entity)
	{
		//TODO: check collision things, such as pushing a boulder
	}
}