package org.tilegames.hexicube.topdownproto.item.weapon;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;
import org.tilegames.hexicube.topdownproto.item.ItemStack;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemArrow extends ItemStack
{
	private int stack;
	
	private static Texture tex = Game.loadImage("item/arrow");
	
	private DamageType type;
	
	public ItemArrow(int count, DamageType type)
	{
		stack = count;
		this.type = type;
	}
	
	@Override
	public int getStackSize()
	{
		return stack;
	}
	
	@Override
	public void setStackSize(int stack)
	{
		if(stack > 99) stack = 99;
		if(stack < 1) stack = 1;
		this.stack = stack;
	}
	
	@Override
	public int getMaxStack()
	{
		return 99;
	}
	
	@Override
	public boolean canStack(Item item)
	{
		if(item instanceof ItemArrow)
		{
			return ((ItemArrow) item).type == type;
		}
		else return false;
	}
	
	@Override
	public int stackItem(Item item)
	{
		if(!canStack(item)) return 0;
		ItemArrow i = (ItemArrow) item;
		int totalAllowed = getMaxStack() - getStackSize();
		if(totalAllowed > i.getStackSize())
		{
			totalAllowed = i.getStackSize();
			stack += totalAllowed;
			i.setStackSize(0);
			return totalAllowed;
		}
		else
		{
			i.setStackSize(i.getStackSize() - totalAllowed);
			stack += totalAllowed;
			return totalAllowed;
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
		return type;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return ItemModifier.NONE;
	}
	
	@Override
	public String getName()
	{
		if(type == null) return "Arrow";
		switch(type)
		{
			case ACID: return "Acidic Arrow";
			case EXPLOSIVE: return "Explosive Arrow";
			case FIRE: return "Flaming Arrow";
			case ICE: return "Freezing Arrow";
			default:
				type = null;
				return "Arrow";
		}
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{}
	
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
	public Color getInvBorderCol()
	{
		return new Color(0, 0, 0, 0);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		int t = type.ID;
		batch.draw(tex, x, y, 32, 32, (t/4) * 32, (t%4) * 32, 32, 32, false, false);
		batch.setColor(0, 0, 0, 1);
		if(stack != 1) FontHolder.render(batch, FontHolder.getCharList(String.valueOf(stack)), x + 3, y + 29, false);
	}
}