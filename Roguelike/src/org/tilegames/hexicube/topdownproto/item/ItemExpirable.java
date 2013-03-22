package org.tilegames.hexicube.topdownproto.item;

public abstract class ItemExpirable extends Item
{
	public abstract int getMaxDurability();
	public abstract int getCurrentDurability();
}