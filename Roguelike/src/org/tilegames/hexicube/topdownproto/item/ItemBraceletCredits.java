package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemBraceletCredits extends ItemAccessory
{
	private static final String[] credits = new String[]
	{
		"--------CREDITS--------",
		"---Concept: Hexicube---",
		"---Artwork: allyally---",
		"--Artwork: Daft Freak--",
		"-Programming: Hexicube-",
		"-Inspiration: geckojsc-",
		"-----------------------",
		"---SPECIAL THANKS TO---",
		"-allyally---Daft Freak-",
		"-----------------------",
		"-THANKS FOR LISTENING!-"
	};
	
	private int timer1, timer2, creditsPos, durability;
	
	public static Texture tex;
	
	public ItemBraceletCredits()
	{
		durability = 1;
		timer1 = 0;
		timer2 = 0;
		creditsPos = -1;
	}
	
	@Override
	public AccessorySlot getAccessoryType()
	{
		return AccessorySlot.BRACELET;
	}
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	@Override
	public String getName()
	{
		return "Memory Bracelet";
	}

	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(equipped)
		{
			timer2 = 0;
			timer1++;
			if(timer1 == 120)
			{
				creditsPos++;
				if(creditsPos < credits.length) Game.message(credits[creditsPos]);
				else
				{
					durability = 0;
					Game.message("The Memory Bracelet crumbles into pieces...");
				}
			}
		}
		else
		{
			timer1 = 0;
			creditsPos = 0;
			timer2++;
			if(timer2 == 3600)
			{
				timer2 = 0;
				Game.message("You hear your Memory Bracelet talking to itself...");
			}
		}
	}
	@Override
	public int getMaxDurability()
	{
		return 1;
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