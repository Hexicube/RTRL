package org.tilegames.hexicube.topdownproto.gui;

import java.util.ArrayList;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.KeyHandler.Key;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Effect;
import org.tilegames.hexicube.topdownproto.entity.EffectType;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityChest;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemStack;
import org.tilegames.hexicube.topdownproto.item.weapon.ItemWeapon;
import org.tilegames.hexicube.topdownproto.map.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GuiManagerInventory extends GuiManager
{
	private EntityPlayer player;
	private GuiElementInvItem[] items;
	
	private int currentActionMenu = -1, currentItemSwap = -1, currentMouseOverItem = -1;
	private GuiElementTextButton[] actionItems, genericActionItems;
	
	public GuiManagerInventory(EntityPlayer player)
	{
		this.player = player;
		items = new GuiElementInvItem[110];
		for(int x = 0; x < 10; x++)
		{
			for(int y = 0; y < 11; y++)
			{
				int pos = x + y*10;
				items[pos] = new GuiElementInvItem(x*40-236, 164-y*40, 0.5f, 0.5f, player, x, y);
			}
		}
		//TODO: generic action stuff
		actionItems = new GuiElementTextButton[0];
		genericActionItems = new GuiElementTextButton[3];
		genericActionItems[0] = new GuiElementTextButton(0, 0, 0.5f, 0.5f, 80, "Move", Color.RED);
		genericActionItems[1] = new GuiElementTextButton(0, 0, 0.5f, 0.5f, 80, "Drop", Color.RED);
		genericActionItems[2] = new GuiElementTextButton(0, 0, 0.5f, 0.5f, 80, "Delete", Color.RED);
	}
	
	@Override
	public void tick()
	{
		if(currentActionMenu != -1)
		{
			for(int a = 0; a < actionItems.length; a++)
			{
				if(actionItems[a].checked())
				{
					Item i = player.getItemInSlot(currentActionMenu%10, currentActionMenu/10);
					if(i != null)
					{
						Item i2 = null;
						if(currentItemSwap != -1) i2 = player.getItemInSlot(currentItemSwap%10, currentItemSwap/10);
						i.handleCustomAction(actionItems[a].text, i2);
					}
					currentActionMenu = -1;
					currentItemSwap = -1;
					if(parent != null) parent.tick();
					for(GuiElement e : actionItems)
					{
						e.tick();
					}
					for(GuiElement e : genericActionItems)
					{
						e.tick();
					}
					for(GuiElement e : items)
					{
						e.tick();
					}
					return;
				}
			}
			for(int a = 0; a < genericActionItems.length; a++)
			{
				if(genericActionItems[a].checked())
				{
					if(a == 0) //swap item
					{
						if(currentItemSwap == -1) currentItemSwap = currentActionMenu;
						else
						{
							Item i = player.getItemInSlot(currentItemSwap%10, currentItemSwap/10);
							Item i2 = player.getItemInSlot(currentActionMenu%10, currentActionMenu/10);
							if((i == null || currentItemSwap >= 10 || i.canMove()) && (i2 == null || currentActionMenu >= 10 || i2.canMove()))
							{
								if(!player.setItemInSlot(currentItemSwap%10, currentItemSwap/10, i2) ||
								   !player.setItemInSlot(currentActionMenu%10, currentActionMenu/10, i))
								{
									player.setItemInSlot(currentItemSwap%10, currentItemSwap/10, i);
									player.setItemInSlot(currentActionMenu%10, currentActionMenu/10, i2);
								}
							}
							currentItemSwap = -1;
						}
					}
					else if(a == 1) //drop
					{
						Item i = player.getItemInSlot(currentActionMenu%10, currentActionMenu/10);
						if(i != null)
						{
							if(i.canMove())
							{
								int x = player.xPos;
								int y = player.yPos;
								Map map = player.map;
								if(player.facingDir == Direction.UP) y++;
								if(player.facingDir == Direction.DOWN) y--;
								if(player.facingDir == Direction.LEFT) x--;
								if(player.facingDir == Direction.RIGHT) x++;
								Entity e = map.tiles[x][y].getCurrentEntity();
								if(e == null)
								{
									ArrayList<Item> items = new ArrayList<Item>();
									items.add(i);
									EntityChest chest = new EntityChest(x, y, items);
									if(Game.addEntity(chest, map, true))
									{
										player.setItemInSlot(currentActionMenu%10, currentActionMenu/10, null);
										Game.message("Item added to chest.");
									}
									else Game.message("You can't drop an item, something is blocking that spot!");
								}
								else if(e instanceof EntityChest)
								{
									((EntityChest)e).contents.add(i);
									player.setItemInSlot(currentActionMenu%10, currentActionMenu/10, null);
									Game.message("Item added to chest.");
								}
								else Game.message("You can't drop an item, something is blocking that spot!");
							}
							else Game.message("That item can't be removed!");
						}
					}
					else if(a == 2) //delete
					{
						Item i = player.getItemInSlot(currentActionMenu%10, currentActionMenu/10);
						if(i != null)
						{
							if(i.canMove())
							{
								i.delete();
								Game.message("Item deleted.");
							}
							else Game.message("That item can't be removed!");
						}
					}
					//TODO: generic action stuff
					currentActionMenu = -1;
					if(parent != null) parent.tick();
					for(GuiElement e : actionItems)
					{
						e.tick();
					}
					for(GuiElement e : genericActionItems)
					{
						e.tick();
					}
					for(GuiElement e : items)
					{
						e.tick();
					}
					return;
				}
			}
		}
		for(int a = 0; a < items.length; a++)
		{
			if(items[a].checked())
			{
				if(currentActionMenu == -1)
				{
					if(currentItemSwap == a)
					{
						currentItemSwap = -1;
						break;
					}
					Item i = player.getItemInSlot(a%10, a/10);
					if(i == null)
					{
						if(currentItemSwap != -1)
						{
							genericActionItems[0].x = a%10*40-200;
							genericActionItems[0].y = 180-(a/10)*40;
							genericActionItems[0].text = "Move";
							actionItems = new GuiElementTextButton[0];
							currentActionMenu = a;
						}
					}
					else
					{
						ArrayList<String> items = new ArrayList<String>();
						Item other = null;
						if(currentItemSwap != -1) other = player.getItemInSlot(currentItemSwap%10, currentItemSwap/10);
						String[] others = i.getCustomActions(other);
						for(String s : others) items.add(s);
						actionItems = new GuiElementTextButton[items.size()];
						for(int b = 0; b < actionItems.length; b++)
						{
							actionItems[b] = new GuiElementTextButton(a%10*40-200, 180-(a/10)*40 - b*20, 0.5f, 0.5f, 80, items.get(b), Color.RED);
						}
						for(int b = 0; b < genericActionItems.length; b++)
						{
							genericActionItems[b].x = a%10*40-200;
							genericActionItems[b].y = 180-(a/10)*40 - actionItems.length*20 - (actionItems.length>0?10:0) - b*20;
						}
						if(currentItemSwap == -1) genericActionItems[0].text = "Select";
						else genericActionItems[0].text = "Move";
						currentActionMenu = a;
					}
				}
				else
				{
					currentActionMenu = -1;
				}
			}
		}
		if(parent != null) parent.tick();
		for(GuiElement e : actionItems)
		{
			e.tick();
		}
		for(GuiElement e : genericActionItems)
		{
			e.tick();
		}
		for(GuiElement e : items)
		{
			e.tick();
		}
	}
	
	@Override
	public void mouseMove(int x, int y)
	{
		currentMouseOverItem = -1;
		for(int a = 0; a < items.length; a++)
		{
			if(items[a].gotClicked(x, y))
			{
				currentMouseOverItem = a;
				return;
			}
		}
	}
	
	@Override
	public void render(SpriteBatch batch)
	{
		batch.draw(Game.invTex, Game.width/2 - 240, Game.height/2 - 272);
		if(parent != null && parent.drawAbove()) parent.render(batch);
		else
		{
			batch.setColor(new Color(0, 0, 0, 0.4f));
			batch.draw(GuiManagerBase.bgTex, 0, 0, Game.width, Game.height, 0, 0, 1, 1, false, false);
		}
		for(GuiElement e : items)
		{
			e.render(batch);
		}
		batch.setColor(1, 1, 1, 1);
		if(currentItemSwap != -1) batch.draw(Game.invHighlightTex, currentItemSwap%10 * 40 + Game.width/2 - 236, Game.height/2 + 164 - currentItemSwap/10 * 40, 32, 32, 0, 0, 32, 32, false, false);
		if(currentActionMenu != -1)
		{
			batch.draw(Game.invHighlightTex, currentActionMenu%10 * 40 + Game.width/2 - 236, Game.height/2 + 164 - currentActionMenu/10 * 40, 32, 32, 32, 0, 32, 32, false, false);
			for(GuiElement e : actionItems)
			{
				e.render(batch);
			}
			Item i = player.getItemInSlot(currentActionMenu%10, currentActionMenu/10);
			if(i == null) genericActionItems[0].render(batch);
			else
			{
				for(GuiElement e : genericActionItems)
				{
					e.render(batch);
				}
			}
		}
		EffectType[] effects = EffectType.values();
		int posY = Game.height/2 + 108;
		for(int a = 0; a < effects.length; a++)
		{
			Effect e = player.getEffect(effects[a]);
			if(e != null)
			{
				int str = e.getEffectStrength();
				int time = e.timeRemaining();
				FontHolder.render(batch, FontHolder.getCharList(e.getEffectType().displayName+" " + ((str>1)?Game.romanNumerals(str):"")), Game.width/2 + 162, posY + 128, false);
				FontHolder.render(batch, FontHolder.getCharList(((time>60)?((time / 3600)+"m "):"")+((time / 60) % 60 + "s")), Game.width/2 + 172, posY + 119, false);
				posY -= 18;
			}
		}
		if(currentMouseOverItem != -1)
		{
			Item curItem = player.getItemInSlot(currentMouseOverItem%10, currentMouseOverItem/10);
			String itemName = curItem == null ? player.getSlotName(currentMouseOverItem%10, currentMouseOverItem/10) : curItem.getName();
			if(curItem instanceof ItemStack)
			{
				int size1 = ((ItemStack) curItem).getStackSize();
				if(size1 != 1) itemName += " x" + size1;
			}
			if(curItem != null && curItem.getMaxDurability() > 1) itemName += " (" + (curItem.getCurrentDurability() * 100 / curItem.getMaxDurability()) + "%)";
			if(curItem != null && curItem instanceof ItemWeapon) itemName = "[" + ((ItemWeapon)curItem).getWeaponDamageRange() + "] " + itemName;
			if(currentItemSwap != -1)
			{
				Item otherItem = player.getItemInSlot(currentItemSwap%10, currentItemSwap/10);
				String itemName2 = otherItem == null ? player.getSlotName(currentItemSwap%10, currentItemSwap/10) : otherItem.getName();
				if(otherItem instanceof ItemStack)
				{
					int size1 = ((ItemStack) otherItem).getStackSize();
					if(size1 != 1) itemName2 += " x" + size1;
				}
				if(otherItem != null && otherItem.getMaxDurability() > 1) itemName2 += " (" + (otherItem.getCurrentDurability() * 100 / otherItem.getMaxDurability()) + "%)";
				if(otherItem != null && otherItem instanceof ItemWeapon) itemName2 = "[" + ((ItemWeapon) otherItem).getWeaponDamageRange() + "] " + itemName2;
				FontHolder.render(batch, FontHolder.getCharList(itemName2), Game.width/2 - 236, Game.height/2+236, false);
				FontHolder.render(batch, FontHolder.getCharList("<--->"), Game.width/2 - 236, Game.height/2+226, false);
				FontHolder.render(batch, FontHolder.getCharList(itemName), Game.width/2 - 236, Game.height/2+216, false);
			}
			else FontHolder.render(batch, FontHolder.getCharList(itemName), Game.width/2 - 236, Game.height/2+236, false);
		}
		else if(currentItemSwap != -1)
		{
			Item item = player.getItemInSlot(currentItemSwap%10, currentItemSwap/10);
			String itemName = item == null ? player.getSlotName(currentItemSwap%10, currentItemSwap/10) : item.getName();
			if(item != null && item.getMaxDurability() > 1) itemName += " (" + (item.getCurrentDurability() * 100 / item.getMaxDurability()) + "%)";
			if(item != null && item instanceof ItemWeapon) itemName = "[" + ((ItemWeapon) item).getWeaponDamageRange() + "] " + itemName;
			FontHolder.render(batch, FontHolder.getCharList(itemName), Game.width/2 - 236, Game.height/2+236, false);
		}
	}
	
	@Override
	public void mousePress(int x, int y, int pointer)
	{
		if(currentActionMenu != -1)
		{
			for(GuiElementClickable e : actionItems)
			{
				if(e.gotClicked(x, y, pointer))
				{
					e.handleClick();
					return;
				}
			}
			Item i = player.getItemInSlot(currentActionMenu%10, currentActionMenu/10);
			if(i == null)
			{
				if(genericActionItems[0].gotClicked(x, y, pointer))
				{
					genericActionItems[0].handleClick();
					return;
				}
			}
			else
			{
				for(GuiElementClickable e : genericActionItems)
				{
					if(e.gotClicked(x, y, pointer))
					{
						e.handleClick();
						return;
					}
				}
			}
		}
		for(GuiElementClickable e : items)
		{
			if(e.gotClicked(x, y, pointer))
			{
				e.handleClick();
				return;
			}
		}
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