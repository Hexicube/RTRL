package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.KeyHandler.Key;
import org.tilegames.hexicube.topdownproto.gui.GuiManagerInventory;
import org.tilegames.hexicube.topdownproto.item.*;
import org.tilegames.hexicube.topdownproto.item.accessory.AccessorySlot;
import org.tilegames.hexicube.topdownproto.item.accessory.ItemAccessory;
import org.tilegames.hexicube.topdownproto.item.armour.ArmourSlot;
import org.tilegames.hexicube.topdownproto.item.armour.ItemArmour;
import org.tilegames.hexicube.topdownproto.item.usable.ItemUsable;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityPlayer extends EntityLiving
{
	public static Texture tex = Game.loadImage("entity/player");
	
	public Direction facingDir = Direction.UP;
	
	private int walkDelay, useDelay;
	
	public int invX, invY, invSelectX, invSelectY;
	
	public Item[] inventory;
	public ItemUsable heldItem;
	public ItemArmour[] armour;
	public ItemAccessory necklace, ring1, ring2, bracelet1, bracelet2;
	
	public int hungerLevel, hungerLevelMax, hungerTicker, mana, manaMax, manaExperience, manaTicker;
	
	public EntityPlayer(int x, int y)
	{
		xPos = x;
		yPos = y;
		health = healthMax = 30;
		inventory = new Item[100];
		armour = new ItemArmour[4];
		hungerLevel = hungerLevelMax = 25200;
		mana = 20;
		manaMax = 20;
		manaTicker = 300;
	}
	
	@Override
	public double damageAfterResistance(double damage, DamageType type)
	{
		double mult = 0;
		for(int a = 0; a < armour.length; a++)
		{
			if(armour[a] != null)
			{
				mult += armour[a].getDamageMod(type);
			}
			else mult++;
		}
		damage *= mult / 4;
		return damage;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		if(rider != null) rider.tick();
		for(int a = 0; a < 100; a++)
		{
			if(inventory[a] != null)
			{
				if(inventory[a].needsDeletion() || (inventory[a].getCurrentDurability() <= 0 && inventory[a].getMaxDurability() > 0)) inventory[a] = null;
				else inventory[a].tick(this, false);
			}
		}
		for(int a = 0; a < 4; a++)
		{
			if(armour[a] != null)
			{
				if(armour[a].needsDeletion() || (armour[a].getCurrentDurability() <= 0 && armour[a].getMaxDurability() > 0)) armour[a] = null;
				else armour[a].tick(this, true);
			}
		}
		if(heldItem != null)
		{
			if(heldItem.needsDeletion() || (heldItem.getCurrentDurability() <= 0 && heldItem.getMaxDurability() > 0)) heldItem = null;
			else heldItem.tick(this, true);
		}
		if(ring1 != null)
		{
			if(ring1.needsDeletion() || (ring1.getCurrentDurability() <= 0 && ring1.getMaxDurability() > 0)) ring1 = null;
			else ring1.tick(this, true);
		}
		if(ring2 != null)
		{
			if(ring2.needsDeletion() || (ring2.getCurrentDurability() <= 0 && ring2.getMaxDurability() > 0)) ring2 = null;
			else ring2.tick(this, true);
		}
		if(bracelet1 != null)
		{
			if(bracelet1.needsDeletion() || (bracelet1.getCurrentDurability() <= 0 && bracelet1.getMaxDurability() > 0)) bracelet1 = null;
			else bracelet1.tick(this, true);
		}
		if(bracelet2 != null)
		{
			if(bracelet2.needsDeletion() || (bracelet2.getCurrentDurability() <= 0 && bracelet2.getMaxDurability() > 0)) bracelet2 = null;
			else bracelet2.tick(this, true);
		}
		if(necklace != null)
		{
			if(necklace.needsDeletion() || (necklace.getCurrentDurability() <= 0 && necklace.getMaxDurability() > 0)) necklace = null;
			else necklace.tick(this, true);
		}
		if(alive)
		{
			if(hungerLevel > 0) hungerLevel--;
			double hungerAmount = (double)hungerLevel / (double)hungerLevelMax;
			double rate;
			if(hungerAmount < 0.1) rate = hungerAmount*10 - 1;
			else if(hungerAmount > 0.75) rate = (hungerAmount-0.75)*4;
			else rate = 0;
			if(rate != 0)
			{
				double amount = rate * .02;
				health += amount;
				if(health > healthMax) health = healthMax;
				if(health < 0) health = 0;
				health = (double)Math.round(health*1000)/1000;
			}
		}
		alive = (health > 0);
		if(!alive)
		{
			while(Game.getMenu() != null && !Game.getMenu().pausesGame()) Game.setMenu(null);
			//TODO: death screen
			return;
		}
		while(manaExperience >= 5 * manaMax)
		{
			manaExperience -= 5 * manaMax;
			manaMax++;
			Game.message("Your max mana has increased to " + manaMax + "!");
		}
		if(manaTicker > 0) manaTicker--;
		if(manaTicker <= 0)
		{
			if(mana < manaMax)
			{
				mana++;
				manaTicker = 900 / (int) Math.sqrt(manaMax);
			}
		}
		if(Game.keys.isKeyHeld(Key.USE))
		{
			if(useDelay == 0)
			{
				if(facingDir == Direction.DOWN)
				{
					if(yPos > 0 && map.tiles[xPos][yPos - 1].use(this)) useDelay = 15;
				}
				if(facingDir == Direction.UP)
				{
					if(yPos < map.tiles[xPos].length - 1 && map.tiles[xPos][yPos + 1].use(this)) useDelay = 15;
				}
				if(facingDir == Direction.LEFT)
				{
					if(xPos > 0 && map.tiles[xPos - 1][yPos].use(this)) useDelay = 15;
				}
				if(facingDir == Direction.RIGHT)
				{
					if(xPos < map.tiles.length - 1 && map.tiles[xPos + 1][yPos].use(this)) useDelay = 15;
				}
			}
		}
		if(Game.keys.isKeyHeld(Key.UP))
		{
			if(walkDelay == 0 && !Game.keys.isKeyHeld(Key.LOOK))
			{
				walkDelay = 15;
				move(Direction.UP);
				map.updateTexture(xPos, yPos);
			}
			facingDir = Direction.UP;
		}
		else if(Game.keys.isKeyHeld(Key.DOWN))
		{
			if(walkDelay == 0 && !Game.keys.isKeyHeld(Key.LOOK))
			{
				walkDelay = 15;
				move(Direction.DOWN);
				map.updateTexture(xPos, yPos);
			}
			facingDir = Direction.DOWN;
		}
		else if(Game.keys.isKeyHeld(Key.LEFT))
		{
			if(walkDelay == 0 && !Game.keys.isKeyHeld(Key.LOOK))
			{
				walkDelay = 15;
				move(Direction.LEFT);
				map.updateTexture(xPos, yPos);
			}
			facingDir = Direction.LEFT;
		}
		else if(Game.keys.isKeyHeld(Key.RIGHT))
		{
			if(walkDelay == 0 && !Game.keys.isKeyHeld(Key.LOOK))
			{
				walkDelay = 15;
				move(Direction.RIGHT);
				map.updateTexture(xPos, yPos);
			}
			facingDir = Direction.RIGHT;
		}
		if(Game.keys.isKeyHeld(Key.USE))
		{
			if(useDelay == 0)
			{
				if(heldItem == null)
				{
					useDelay = 15;
					Game.message("You have no held item!");
				}
				else
				{
					useDelay = heldItem.useDelay();
					walkDelay += 7;
					heldItem.use(this, facingDir);
				}
			}
		}
		if(Game.keys.isKeyPressed(Key.INV))
		{
			if(Game.getMenu() == null) Game.setMenu(new GuiManagerInventory(this));
		}
		if(walkDelay > 0) walkDelay--;
		if(useDelay > 0) useDelay--;
		Game.camX = xPos;
		Game.camY = yPos;
	}
	
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		int texX = 0, texY = 0;
		if(facingDir == Direction.DOWN || facingDir == Direction.RIGHT) texX += 32;
		if(facingDir == Direction.LEFT || facingDir == Direction.DOWN) texY += 32;
		batch.draw(tex, Game.width/2 + (xPos - camX) * 32 - 16, Game.height/2 + (yPos - camY) * 32 - 16, 32, 32, texX, texY, 32, 32, false, false);
		// TODO: animation render (do I want to have animations?)
		if(rider != null) rider.render(batch, camX, camY);
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
					inventory[a] = ((EntityItem) entity).curItem;
					Game.message("Collected item: " + inventory[a].getName());
					return;
				}
			}
			Game.message("Your inventory is full!");
		}
		// TODO: check more collision things, such as pushing a boulder
	}
	
	public Item getItemInSlot(int x, int y)
	{
		if(y == 0)
		{
			if(x < 4) return armour[x];
			if(x == 4) return heldItem;
			if(x == 5) return necklace;
			if(x == 6) return ring1;
			if(x == 7) return ring2;
			if(x == 8) return bracelet1;
			if(x == 9) return bracelet2;
		}
		else return inventory[(y - 1) * 10 + x];
		return null;
	}
	
	public boolean setItemInSlot(int x, int y, Item item)
	{
		if(y == 0)
		{
			if(x < 4)
			{
				if(item == null)
				{
					armour[x] = null;
					return true;
				}
				if(item instanceof ItemArmour)
				{
					ItemArmour i = (ItemArmour) item;
					if(x == 0 && i.getArmourType() != ArmourSlot.HEAD) return false;
					if(x == 1 && i.getArmourType() != ArmourSlot.CHEST) return false;
					if(x == 2 && i.getArmourType() != ArmourSlot.LEGS) return false;
					if(x == 3 && i.getArmourType() != ArmourSlot.FEET) return false;
					armour[x] = i;
					return true;
				}
				return false;
			}
			if(x == 4)
			{
				if(item == null || item instanceof ItemUsable)
				{
					heldItem = (ItemUsable) item;
					return true;
				}
				return false;
			}
			if(!(item instanceof ItemAccessory) && item != null) return false;
			ItemAccessory i = (ItemAccessory) item;
			if(x == 5)
			{
				if(i != null && i.getAccessoryType() != AccessorySlot.NECKLACE) return false;
				necklace = i;
				return true;
			}
			if(x == 6)
			{
				if(i != null && i.getAccessoryType() != AccessorySlot.RING) return false;
				ring1 = i;
				return true;
			}
			if(x == 7)
			{
				if(i != null && i.getAccessoryType() != AccessorySlot.RING) return false;
				ring2 = i;
				return true;
			}
			if(x == 8)
			{
				if(i != null && i.getAccessoryType() != AccessorySlot.BRACELET) return false;
				bracelet1 = i;
				return true;
			}
			if(x == 9)
			{
				if(i != null && i.getAccessoryType() != AccessorySlot.BRACELET) return false;
				bracelet2 = i;
				return true;
			}
		}
		else
		{
			inventory[(y - 1) * 10 + x] = item;
			return true;
		}
		return false;
	}
	
	public String getSlotName(int x, int y)
	{
		if(y == 0)
		{
			if(x == 0) return "Helmet slot";
			if(x == 1) return "Chestplate slot";
			if(x == 2) return "Leggings slot";
			if(x == 3) return "Boots slot";
			if(x == 4) return "Held item slot";
			if(x == 5) return "Necklace slot";
			if(x == 6 || x == 7) return "Ring slot";
			return "Bracelet slot";
		}
		else return "Inventory slot";
	}
	
	public boolean canMoveItem(int x, int y)
	{
		if(y == 0)
		{
			if(x < 4)
			{
				if(armour[x] == null) return true;
				return armour[x].canMove();
			}
			if(x == 4)
			{
				if(heldItem == null) return true;
				return heldItem.canMove();
			}
			if(x == 5)
			{
				if(necklace == null) return true;
				return necklace.canMove();
			}
			if(x == 6)
			{
				if(ring1 == null) return true;
				return ring1.canMove();
			}
			if(x == 7)
			{
				if(ring2 == null) return true;
				return ring2.canMove();
			}
			if(x == 8)
			{
				if(bracelet1 == null) return true;
				return bracelet1.canMove();
			}
			if(x == 9)
			{
				if(bracelet2 == null) return true;
				return bracelet2.canMove();
			}
		}
		return true;
	}
	
	@Override
	public boolean mountable(Entity mounter)
	{
		// TODO: check for buffs and armour that blocks this
		return true;
	}
}