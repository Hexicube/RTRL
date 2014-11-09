package org.tilegames.hexicube.topdownproto.item.accessory;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemBraceletCredits extends ItemAccessory
{
	private static final String[][] credits = new String[][]
	{
			new String[]
			{
				"CREDITS"
			}, new String[]
			{
				"Concept: Hexicube", "Artwork: allyally", "Artwork: Daft Freak", "Programming: Hexicube", "HTML port: Daft Freak", "Inspiration: geckojsc"
			}, new String[]
			{
				"SPECIAL THANKS TO"
			}, new String[]
			{
				"allyally", "Daft Freak"
			}, new String[]
			{
				"THANKS FOR LISTENING!"
			}
	};
	
	private int timer1, timer2, creditsPos, durability;
	
	public static Texture tex;
	
	public ItemBraceletCredits()
	{
		durability = 1;
		timer1 = 0;
		timer2 = 0;
		creditsPos = 0;
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
			if(creditsPos == 0 || timer1 == credits[creditsPos - 1].length * 50 + 50)
			{
				timer1 = 0;
				Game.message("-----------------");
				for(int a = 0; a < credits[creditsPos].length; a++)
				{
					Game.message(credits[creditsPos][credits[creditsPos].length-a-1]);
				}
				creditsPos++;
				if(creditsPos == credits.length)
				{
					durability = 0;
					Game.message("-----------------");
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
	public boolean purify()
	{
		return false;
	}
	
	@Override
	public Color getInvBorderCol()
	{
		return new Color(0, 0, 0, 0);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y, 32, 32, equipped ? 32 : 0, 0, 32, 32, false, false);
	}
}