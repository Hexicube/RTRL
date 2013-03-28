package org.tilegames.hexicube.topdownproto.item;

public abstract class ItemStack extends Item
{
	public abstract int getStackSize();
	public abstract void setStackSize(int stack);
	public abstract int getMaxStack();
	public abstract boolean canStack(Item item);
	public abstract int stackItem(Item item);
}