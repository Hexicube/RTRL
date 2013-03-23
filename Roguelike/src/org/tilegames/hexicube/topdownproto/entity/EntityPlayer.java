package org.tilegames.hexicube.topdownproto.entity;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityPlayer extends EntityLiving
{	
	public static Texture tex = Game.loadImage("player");
	
	private Direction facingDir = Direction.UP;
	
	private int walkDelay, useDelay, useAnimTime, useAnimType;
	
	public Item[] inventory;
	public ItemUsable heldItem;
	private ItemArmour[] armour;
	public ItemAccessory necklace, ring1, ring2, bracelet1, bracelet2;
	
	public boolean viewingInventory;
	
	public int hungerLevel;
	
	public ArrayList<Effect> effects;
	
	public EntityPlayer(int x, int y, int maxHP)
	{
		xPos = x;
		yPos = y;
		health = maxHP;
		healthMax = maxHP;
		inventory = new Item[100];
		armour = new ItemArmour[4];
		effects = new ArrayList<Effect>();
		hungerLevel = 60*60*7;
	}
	
	@Override
	public long damageAfterResistance(long damage, DamageType type)
	{
		double mult = 1;
		for(int a = 0; a < armour.length; a++)
		{
			if(armour[a] != null)
			{
				mult *= armour[a].getProtectionMod(type);
			}
		}
		damage *= mult;
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
		//0-9 -> 7-16
		if(viewingInventory)
		{
			//0 -> close inventory
			//TODO: These controls:
			//1/2/3 -> unused
			//4/5/6/8 -> left/down/right/up
			//7/9 -> unused
		}
		else
		{
			//0 -> open inventory
			//1 -> use on self
			//2/3 -> unused
			//4/5/6/8 -> left/down/right/up
			//7 -> open door
			//9 -> use held item
			if(Game.keyPress[14])
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
			if(Game.keysDown[15])
			{
				if(walkDelay == 0)
				{
					walkDelay = 15;
					move(false, 1);
				}
				facingDir = Direction.UP;
			}
			else if(Game.keysDown[12])
			{
				if(walkDelay == 0)
				{
					walkDelay = 15;
					move(false, -1);
				}
				facingDir = Direction.DOWN;
			}
			else if(Game.keysDown[11])
			{
				if(walkDelay == 0)
				{
					walkDelay = 15;
					move(true, -1);
				}
				facingDir = Direction.LEFT;
			}
			else if(Game.keysDown[13])
			{
				if(walkDelay == 0)
				{
					walkDelay = 15;
					move(true, 1);
				}
				facingDir = Direction.RIGHT;
			}
			if(Game.keyPress[8])
			{
				Game.message("TODO: Use held item on self.");
			}
			else if(Game.keyPress[16])
			{
				Game.message("TODO: Use held item at target.");
			}
		}
		if(Game.keyPress[7])
		{
			viewingInventory = !viewingInventory;
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
		if(entity instanceof EntityItem)
		{
			for(int a = 0; a < inventory.length; a++)
			{
				if(inventory[a] == null)
				{
					Game.removeEntity(entity);
					inventory[a] = ((EntityItem)entity).curItem;
					Game.message("Collected item: "+inventory[a].getName());
					return;
				}
			}
			Game.message("Your inventory is full!");
		}
		//TODO: check collision things, such as pushing a boulder
	}
}