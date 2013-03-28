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
	
	public ItemArrow(int count)
	{
		stack = count;
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
		return (item instanceof ItemArrow);
	}
	@Override
	public int stackItem(Item item)
	{
		if(!(item instanceof ItemArrow)) return 0;
		ItemArrow i = (ItemArrow)item;
		int totalAllowed = getMaxStack() - getStackSize();
		if(totalAllowed > i.getStackSize())
		{
			stack += i.getStackSize();
			i.setStackSize(0);
			return i.getStackSize();
		}
		return 0;
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
		batch.draw(tex, x, y);
		if(stack != 1) FontHolder.render(batch, FontHolder.getCharList(String.valueOf(stack)), x+2, y+9, false);
	}
}