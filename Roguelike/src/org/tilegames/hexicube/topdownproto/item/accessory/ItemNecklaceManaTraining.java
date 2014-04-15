package org.tilegames.hexicube.topdownproto.item.accessory;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemNecklaceManaTraining extends ItemAccessory
{
	public static Texture tex;
	
	private static boolean nameDiscovered = false;
	
	private int durability;
	private boolean shoddy;
	
	public ItemNecklaceManaTraining()
	{
		durability = 100;
		if(Game.rand.nextInt(10) < 3) shoddy = true;
		else shoddy = false;
	}
	
	@Override
	public AccessorySlot getAccessoryType()
	{
		return AccessorySlot.NECKLACE;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return shoddy ? ItemModifier.SHODDY : ItemModifier.NONE;
	}
	
	@Override
	public String getName()
	{
		if(nameDiscovered)
		{
			if(shoddy) return "Wasteful Necklace of Mana Exercise";
			else return "Necklace of Mana Exercise";
		}
		else
		{
			if(shoddy) return "Unusual Necklace";
			else return "Unknown Necklace";
		}
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(equipped && entity instanceof EntityPlayer)
		{
			if(!nameDiscovered)
			{
				nameDiscovered = true;
				Game.message("Discovered necklace: Mana Exercise");
				if(shoddy) Game.message("You realise the Necklace of Mana Exercise is wasteful...");
			}
			EntityPlayer p = (EntityPlayer) entity;
			if(p.mana > 0)
			{
				p.mana--;
				durability--;
				if(shoddy)
				{
					if(Game.rand.nextBoolean()) p.manaExperience++;
				}
				else p.manaExperience++;
				if(durability == 0) Game.message("The " + getName() + " broke...");
			}
		}
	}
	
	@Override
	public int getMaxDurability()
	{
		return 100;
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