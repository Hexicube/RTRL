package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemBraceletCredits extends ItemAccessory
{
	private static final String[] credits = new String[]{
		
	};
	
	private int timer1, timer2, creditsPos;
	
	public static Texture tex;
	
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
			if(timer1 == 60) Game.message("--CREDITS--");
			else if(timer1 == 120) Game.message("Concept: Hexicube");
			else if(timer1 == 180) Game.message("Artwork: Allyally");
			else if(timer1 == 240) Game.message("Programming: Hexicube");
			else if(timer1 == 300) Game.message("Inspiration: geckojsc's roguelike");
			else if(timer1 == 360) Game.message("");
			else if(timer1 == 420) Game.message("Special thanks to:");
			else if(timer1 == 480) Game.message("geckojsc - For being an inspiration source");
			else if(timer1 == 540) Game.message("allyally - For making awesome artwork when I wanted it");
			else if(timer1 == 600) Game.message("brawler - For keeping my entertainement levels nominal");
			else if(timer1 == 660) Game.message("Gameinsky - For being (occasionlly) humorous on an IRC chat I hang out on");
			else if(timer1 == 720) Game.message("The guys who made libGDX - For making this so much easier");
			else if(timer1 == 780) Game.message("");
			else if(timer1 == 840) Game.message("----END----");
			//TODO: credits
		}
		else
		{
			timer1 = 0;
			timer2++;
			if(timer2 == 3600)
			{
				timer2 = 0;
				Game.message("You hear your Memory Bracelet talking to itself.");
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
		return (creditsPos==credits.length)?0:1;
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