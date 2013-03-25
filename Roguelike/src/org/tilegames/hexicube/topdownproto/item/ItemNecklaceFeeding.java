package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemNecklaceFeeding extends ItemAccessory
{
	private static boolean nameDiscovered = false;
	
	private int durability;
	
	public static Texture tex;
	
	public ItemNecklaceFeeding()
	{
		durability = getMaxDurability();
	}
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	@Override
	public String getName()
	{
		if(!nameDiscovered) return "Unknown Necklace";
		return "Necklace of Feeding";
	}
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(equipped && entity instanceof EntityPlayer)
		{
			nameDiscovered = true;
			((EntityPlayer)entity).hungerLevel++;
			durability--;
		}
	}
	@Override
	public AccessorySlot getAccessoryType()
	{
		return AccessorySlot.NECKLACE;
	}
	@Override
	public int getMaxDurability()
	{
		return 54000;
	}
	@Override
	public int getCurrentDurability()
	{
		return durability;
	}
	@Override
	public boolean canMove()
	{
		return true;
	}
	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		batch.draw(tex, x, y);
	}
}