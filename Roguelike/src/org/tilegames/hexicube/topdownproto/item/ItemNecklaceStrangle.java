package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;

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
		durability = Game.rand.nextInt(3601)+3600;
		ticker = 20;
	}
	@Override
	public AccessorySlot getAccessoryType()
	{
		return AccessorySlot.NECKLACE;
	}
	@Override
	public ItemModifier getModifier()
	{
		return cursed?ItemModifier.CURSED:ItemModifier.NONE;
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
		if(entity instanceof EntityPlayer && equipped)
		{
			EntityPlayer p = (EntityPlayer)entity;
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
				if(p.alive) p.hurt(Game.rollDice(6, 2), DamageType.GENERIC); //TODO: involve oxygen system
			}
			durability--;
			if(durability == 0) Game.message("The "+getName()+" broke...");
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
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y, 32, 32, equipped?32:0, 0, 32, 32, false, false);
	}
}