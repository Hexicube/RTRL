package org.tilegames.hexicube.topdownproto.item.accessory;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemNecklaceStrangle extends ItemAccessory
{
	public static Texture tex;
	
	private static boolean nameDiscovered = false;
	private boolean cursed, modDiscovered;
	private int durability, ticker;
	
	public ItemNecklaceStrangle()
	{
		if(Game.rand.nextInt(10) < 8) cursed = true;
		else cursed = false;
		durability = Game.rand.nextInt(3601) + 3600;
		ticker = 20;
	}
	
	@Override
	public String[] getCustomActions(Item other)
	{
		return new String[0];
	}
	
	@Override
	public void handleCustomAction(String action, Item other) {}
	
	@Override
	public AccessorySlot getAccessoryType()
	{
		return AccessorySlot.NECKLACE;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return cursed ? ItemModifier.CURSED : ItemModifier.NONE;
	}
	
	@Override
	public String getName()
	{
		if(!nameDiscovered)
		{
			if(!modDiscovered) return "Unusual Necklace";
			return "Unknown Necklace";
		}
		if(!modDiscovered) return "Unusual Necklace of Strangulation";
		if(cursed) return "Cursed Necklace of Strangulation";
		return "Necklace of Strangulation";
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(!cursed) modDiscovered = true;
		if(entity instanceof EntityPlayer && equipped)
		{
			EntityPlayer p = (EntityPlayer) entity;
			if(!nameDiscovered) Game.message("Discovered necklace: Strangulation");
			nameDiscovered = true;
			if(!modDiscovered)
			{
				modDiscovered = true;
				if(cursed) Game.message("The Necklace of Strangulation is cursed, you can't remove it!");
			}
			ticker--;
			if(ticker == 0)
			{
				ticker = 20;
				if(p.alive) p.health--;
			}
			durability--;
			if(durability == 0)
			{
				Game.message("The " + getName() + " broke...");
				Game.message("Your max HP increased by 10!");
				p.healthMax += 10;
				p.health += 10;
			}
		}
	}
	
	@Override
	public int getMaxDurability()
	{
		return 7200;
	}
	
	@Override
	public int getCurrentDurability()
	{
		return durability;
	}
	
	@Override
	public boolean canMove()
	{
		return !cursed;
	}
	
	@Override
	public Color getInvBorderCol()
	{
		if(!nameDiscovered || !modDiscovered) return Color.ORANGE;
		if(cursed) return Color.RED;
		return new Color(0, 0, 0, 0);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y, 32, 32, equipped ? 32 : 0, 0, 32, 32, false, false);
	}
}