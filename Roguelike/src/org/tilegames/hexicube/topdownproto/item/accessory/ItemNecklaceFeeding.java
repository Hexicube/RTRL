package org.tilegames.hexicube.topdownproto.item.accessory;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemNecklaceFeeding extends ItemAccessory
{
	public static boolean nameDiscovered = false;
	
	private int durability;
	
	public static Texture tex;
	
	public ItemNecklaceFeeding()
	{
		durability = Game.rand.nextInt(3001) + 9000;
	}
	
	@Override
	public String[] getCustomActions(Item other)
	{
		return new String[0];
	}
	
	@Override
	public void handleCustomAction(String action, Item other) {}
	
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
			if(!nameDiscovered) Game.message("Discovered necklace: Feeding");
			nameDiscovered = true;
			EntityPlayer p = (EntityPlayer)entity;
			double level = (double)p.hungerLevel / (double)p.hungerLevelMax;
			double thisLevel = (double)durability / (double)getMaxDurability();
			if(level < thisLevel)
			{
				p.hungerLevel += 2;
				durability -= 2;
				if(durability == 0) Game.message("The " + getName() + " broke...");
			}
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
		return 12000;
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
	public boolean purify()
	{
		return false;
	}
	
	@Override
	public Color getInvBorderCol()
	{
		if(!nameDiscovered) return Color.ORANGE;
		return new Color(0, 0, 0, 0);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y, 32, 32, equipped ? 32 : 0, 0, 32, 32, false, false);
	}
}