package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemArrow extends ItemStack
{
	private int stack;
	
	private static Texture tex = Game.loadImage("item/arrow");
	
	private ArrowType type;
	
	public ItemArrow(int count, ArrowType type)
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
			return ((ItemArrow)item).type == type;
		}
		else return false;
	}
	@Override
	public int stackItem(Item item)
	{
		if(!canStack(item)) return 0;
		ItemArrow i = (ItemArrow)item;
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
		return DamageType.SHARP;
	}
	@Override
	public ItemModifier getModifier()
	{
		return ItemModifier.NONE;
	}
	@Override
	public String getName()
	{
		if(type == ArrowType.PLAIN) return "Arrow";
		if(type == ArrowType.FLAMING) return "Flaming Arrow";
		if(type == ArrowType.ACIDIC) return "Acidic Arrow";
		if(type == ArrowType.MAGICICE) return "Icicle Arrow";
		return "Arrow";
	}
	@Override
	public void tick(Entity entity, boolean equipped) {}
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
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		int t;
		if(type == ArrowType.PLAIN) t = 0;
		else if(type == ArrowType.FLAMING) t = 1;
		else if(type == ArrowType.ACIDIC) t = 2;
		else if(type == ArrowType.MAGICICE) t = 3;
		else t = -1;
		batch.draw(tex, x, y, 32, 32, t*32, 0, 32, 32, false, false);
		if(stack != 1) FontHolder.render(batch, FontHolder.getCharList(String.valueOf(stack)), x+2, y+9, false);
	}
	public ArrowType getType()
	{
		return type;
	}
}