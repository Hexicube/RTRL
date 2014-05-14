package org.tilegames.hexicube.topdownproto.gui;

import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiManagerInventory extends GuiManagerBase
{
	private EntityPlayer player;
	private GuiElementInvItem[] items;
	
	public GuiManagerInventory(EntityPlayer player)
	{
		this.player = player;
		background = new Color(0, 0, 0, 0.4f);
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				int pos = x + y*10;
				items[pos] = new GuiElementInvItem(x*40-232, y*40-212, 0.5f, 0.5f, player, x, y);
			}
		}
		//TODO
		//1. Render inventory code, with a custom gui element class extending clickable
		//2. Restore basic item swap functionality with mouse control
		//3. Make clicking an item show a menu of actions that can be performed
		//4. Add methods to allow items to specify custom actions to perform based on other item (i.e. grindstone with sword)
	}
	
	@Override
	public void tick()
	{
		super.tick();
		//TODO: check inputs
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		super.render(batch);
		/*int xPos = screenW / 2 - 200;
		int yPos = screenH / 2 - 240 - 32;
		spriteBatch.draw(invTex, xPos, yPos);
		int harmTimer = -1, harmStrength = 0, slowTimer = -1, slowStrength = 0, healTimer = -1, healStrength = 0, invisTimer = -1, invulTimer = -1, ghostTimer = -1;
		size = player.effects.size();
		for(int a = 0; a < size; a++)
		{
			Effect e = player.effects.get(a);
			int str = e.getEffectStrength();
			int time = e.timeRemaining();
			if(e.getEffectType() == EffectType.HARM)
			{
				if(str > harmStrength)
				{
					harmStrength = str;
					harmTimer = time;
				}
				else if(str == harmStrength && time > harmTimer)
				{
					harmTimer = time;
				}
			}
			else if(e.getEffectType() == EffectType.SLOW)
			{
				if(str > slowStrength)
				{
					slowStrength = str;
					slowTimer = time;
				}
				else if(str == slowStrength && time > slowTimer)
				{
					slowTimer = time;
				}
			}
			else if(e.getEffectType() == EffectType.HEAL)
			{
				if(str > healStrength)
				{
					healStrength = str;
					healTimer = time;
				}
				else if(str == healStrength && time > healTimer)
				{
					healTimer = time;
				}
			}
			else if(e.getEffectType() == EffectType.INVISIBLE)
			{
				if(str > 0 && time > invisTimer)
				{
					invisTimer = time;
				}
			}
			else if(e.getEffectType() == EffectType.INVULNERABLE)
			{
				if(str > 0 && time > invulTimer)
				{
					invulTimer = time;
				}
			}
			else if(e.getEffectType() == EffectType.GHOSTLY)
			{
				if(str > 0 && time > ghostTimer)
				{
					ghostTimer = time;
				}
			}
		}
		int posY = yPos + 508;
		if(harmStrength > 0)
		{
			FontHolder.render(spriteBatch, FontHolder.getCharList("Harm " + romanNumerals(harmStrength)), xPos + 402, posY, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList((harmTimer / 60) + "s"), xPos + 422, posY - 9, false);
			posY -= 18;
		}
		if(slowStrength > 0)
		{
			FontHolder.render(spriteBatch, FontHolder.getCharList("Slow " + romanNumerals(slowStrength)), xPos + 402, posY, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList((slowTimer / 60) + "s"), xPos + 422, posY - 9, false);
			posY -= 18;
		}
		if(healStrength > 0)
		{
			FontHolder.render(spriteBatch, FontHolder.getCharList("Heal " + romanNumerals(healStrength)), xPos + 402, posY, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList((healTimer / 60) + "s"), xPos + 422, posY - 9, false);
			posY -= 18;
		}
		if(invisTimer > -1)
		{
			FontHolder.render(spriteBatch, FontHolder.getCharList("Invisibility"), xPos + 402, posY, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList((invisTimer / 60) + "s"), xPos + 422, posY - 9, false);
			posY -= 18;
		}
		if(invulTimer > -1)
		{
			FontHolder.render(spriteBatch, FontHolder.getCharList("Invulnerability"), xPos + 402, posY, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList((invisTimer / 60) + "s"), xPos + 422, posY - 9, false);
			posY -= 18;
		}
		if(ghostTimer > -1)
		{
			FontHolder.render(spriteBatch, FontHolder.getCharList("Ghostly"), xPos + 402, posY, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList((ghostTimer / 60) + "s"), xPos + 422, posY - 9, false);
			posY -= 18;
		}
		spriteBatch.draw(invHighlightTex, xPos + 4 + player.invX * 40, 436 + yPos - player.invY * 40, 32, 32, 32, 0, 32, 32, false, false);
		Item curItem = player.getItemInSlot(player.invX, player.invY);
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
			spriteBatch.draw(invHighlightTex, xPos + 4 + player.invSelectX * 40, 436 + yPos - player.invSelectY * 40, 32, 32, 0, 0, 32, 32, false, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList(itemName2), xPos + 4, 508 + yPos, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList("<--->"), xPos + 4, 498 + yPos, false);
			FontHolder.render(spriteBatch, FontHolder.getCharList(itemName), xPos + 4, 488 + yPos, false);
		}
		else FontHolder.render(spriteBatch, FontHolder.getCharList(itemName), xPos + 4, 508 + yPos, false);*/
		//TODO: draw action menu
	}
	
	@Override
	public boolean keyPress(int key)
	{
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
}