package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

import com.badlogic.gdx.graphics.Color;

public abstract class ItemBox extends Item
{
	protected abstract Item[] open();
	protected abstract void check();
	
	@Override
	public String[] getCustomActions(Item other)
	{
		return new String[]{"Open", "Check"};
	}
	
	@Override
	public void handleCustomAction(String action, Item other)
	{
		if(action.equals("Open"))
		{
			Game.message("You open "+getName()+"...");
			Item[] items = open();
			delete();
			for(Item i : items)
			{
				if(Game.player.giveItem(i)) Game.message("Collected item: "+i.getName());
				else Game.message("Unable to collect item: "+i.getName());
			}
		}
		else if(action.equals("Check"))
		{
			check();
		}
	}
	
	@Override
	public boolean isWeapon()
	{
		return false;
	}
	
	@Override
	public DamageType getAttackType()
	{
		return null;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	
	@Override
	public int getMaxDurability()
	{
		return 0;
	}
	
	@Override
	public int getCurrentDurability()
	{
		return 0;
	}
	
	@Override
	public boolean canMove()
	{
		return true;
	}
	
	@Override
	public boolean purify()
	{
		return false;
	}
	
	@Override
	public Color getInvBorderCol()
	{
		return new Color(0, 0, 0, 0);
	}
}