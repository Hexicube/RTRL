package org.tilegames.hexicube.topdownproto.item.accessory;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemNecklaceFeeding extends ItemAccessory
{
	private static boolean nameDiscovered = false;
	
	private int durability;
	
	public static Texture tex;
	
	public ItemNecklaceFeeding()
	{
		durability = Game.rand.nextInt(27001) + 27001;
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
			if(!nameDiscovered) Game.message("Discovered necklace: Feeding");
			nameDiscovered = true;
			((EntityPlayer) entity).hungerLevel++;
			durability--;
			if(durability == 0) Game.message("The " + getName() + " broke...");
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