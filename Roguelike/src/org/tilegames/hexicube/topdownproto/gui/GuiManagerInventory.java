package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.KeyHandler.Key;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Effect;
import org.tilegames.hexicube.topdownproto.entity.EffectType;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.usable.ItemUsable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiManagerInventory extends GuiManagerBase
{
	private EntityPlayer player;
	private GuiElementInvItem[] items;
	
	private int currentActionMenu = -1, currentItemSwap = -1;
	private String[] actionItems;
	
	public GuiManagerInventory(EntityPlayer player)
	{
		this.player = player;
		background = new Color(0, 0, 0, 0.4f);
		items = new GuiElementInvItem[110];
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 11; y++)
			{
				int pos = x + y*10;
				items[pos] = new GuiElementInvItem(x*40-236, 164-y*40, 0.5f, 0.5f, player, x, y);
				elems.add(items[pos]);
			}
		}
		//TODO: inventory stuff
		//1. Restore basic item swap functionality with mouse control
		//2. Make clicking an item show a menu of actions that can be performed
		//3. Add methods to allow items to specify custom actions to perform based on other item (i.e. grindstone with sword)
	}
	
	@Override
	public void tick()
	{
		//TODO: check action menu
		for(int a = 0; a < items.length; a++)
		{
			if(items[a].checked())
			{
				if(currentActionMenu == -1) currentActionMenu = a;
				else
				{
					if(currentActionMenu == a)
					{
						Item i = player.getItemInSlot(a%10, a/10);
						if(i instanceof ItemUsable)
						{
							((ItemUsable)i).use(player, Direction.NONE);
						}
						//TODO: try to consume
					}
					else
					{
						Item i = player.getItemInSlot(currentActionMenu%10, currentActionMenu/10);
						Item i2 = player.getItemInSlot(a%10, a/10);
						if((i == null || currentActionMenu >= 10 || i.canMove()) && (i2 == null || a >= 10 || i2.canMove()))
						{
							player.setItemInSlot(currentActionMenu%10, currentActionMenu/10, i2);
							player.setItemInSlot(a%10, a/10, i);
						}
					}
					currentActionMenu = -1;
				}
			}
		}
		super.tick();
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		batch.draw(Game.invTex, Game.width/2 - 240, Game.height/2 - 272);
		super.render(batch);
		batch.setColor(1, 1, 1, 1);
		if(currentActionMenu != -1) batch.draw(Game.invHighlightTex, currentActionMenu%10 * 40 + Game.width/2 - 236, Game.height/2 + 164 - currentActionMenu/10 * 40, 32, 32, 32, 0, 32, 32, false, false);
		if(currentItemSwap != -1) batch.draw(Game.invHighlightTex, currentItemSwap%10 * 40 + Game.width/2 - 236, Game.height/2 + 164 - currentItemSwap/10 * 40, 32, 32, 0, 0, 32, 32, false, false);
		int size = player.effects.size();
		int posY = Game.height/2 + 108;
		for(int a = 0; a < size; a++)
		{
			Effect e = player.effects.get(a);
			int str = e.getEffectStrength();
			int time = e.timeRemaining();
			FontHolder.render(batch, FontHolder.getCharList(e.getEffectType().displayName+" " + ((str>1)?Game.romanNumerals(str):"")), Game.width/2 + 162, posY + 128, false);
			FontHolder.render(batch, FontHolder.getCharList(((time>60)?((time / 3600)+"m "):"")+((time / 60) % 60 + "s")), Game.width/2 + 172, posY + 119, false);
			posY -= 18;
		}
		//TODO: render item names
		/*Item curItem = player.getItemInSlot(player.invX, player.invY);
		String itemName = curItem == null ? player.getSlotName(player.invX, player.invY) : curItem.getName();
		if(curItem instanceof ItemStack)
		{
			int size1 = ((ItemStack) curItem).getStackSize();
			if(size1 != 1) itemName += " x" + size1;
		}
		if(curItem != null && curItem.getMaxDurability() > 1) itemName += " (" + (curItem.getCurrentDurability() * 100 / curItem.getMaxDurability()) + "%)";
		if(curItem != null && curItem instanceof ItemWeapon) itemName = "[" + ((ItemWeapon) curItem).getWeaponDamageRange() + "] " + itemName;
		if(player.invSelectY != -1)
		{
			Item otherItem = player.getItemInSlot(player.invSelectX, player.invSelectY);
			String itemName2 = otherItem == null ? player.getSlotName(player.invSelectX, player.invSelectY) : otherItem.getName();
			if(otherItem instanceof ItemStack)
			{
				int size1 = ((ItemStack) otherItem).getStackSize();
				if(size1 != 1) itemName2 += " x" + size1;
			}
			if(otherItem != null && otherItem.getMaxDurability() > 1) itemName2 += " (" + (otherItem.getCurrentDurability() * 100 / otherItem.getMaxDurability()) + "%)";
			if(otherItem != null && otherItem instanceof ItemWeapon) itemName2 = "[" + ((ItemWeapon) otherItem).getWeaponDamageRange() + "] " + itemName2;
			FontHolder.render(spriteBatch, FontHolder.getCharList(itemName2), xPos + 4, 508 + yPos, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList("<--->"), xPos + 4, 498 + yPos, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList(itemName), xPos + 4, 488 + yPos, false);
		}
		else FontHolder.render(spriteBatch, FontHolder.getCharList(itemName), xPos + 4, 508 + yPos, false);
		*/
		//TODO: draw action menu
	}
	
	@Override
	public boolean keyPress(int key)
	{
		Key k = Key.getKey(key);
		if(k == Key.INV) Game.setMenu(null);
		return true; //block input from controling character
	}
	
	@Override
	public boolean pausesGame()
	{
		return false;
	}
	
	@Override
	public boolean drawBehind()
	{
		return true;
	}
	
	@Override
	public boolean drawAbove()
	{
		return true;
	}
}