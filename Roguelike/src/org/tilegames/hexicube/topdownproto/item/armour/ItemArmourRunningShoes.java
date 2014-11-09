package org.tilegames.hexicube.topdownproto.item.armour;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;
import org.tilegames.hexicube.topdownproto.item.Item;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemArmourRunningShoes extends ItemArmour
{
	private static Texture tex = Game.loadImage("armour/runshoes");
	
	@Override
	public String[] getCustomActions(Item other)
	{
		return new String[0];
	}
	
	@Override
	public void handleCustomAction(String action, Item other) {}
	
	@Override
	public double getDamageMod(DamageType type)
	{
		if(type == DamageType.CRUSHING) return 0.9;
		return 1;
	}
	
	@Override
	public ArmourSlot getArmourType()
	{
		return ArmourSlot.FEET;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	
	@Override
	public String getName()
	{
		return "Running Shoes";
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(equipped)
		{
			if(entity instanceof EntityPlayer)
			{
				if(((EntityPlayer)entity).walkDelay > 6) ((EntityPlayer)entity).walkDelay = 6;
			}
		}
	}
	
	@Override
	public int getMaxDurability()
	{
		return 0;
	}
	
	@Override
	public int getCurrentDurability()
	{
		return 0;
	}
	
	@Override
	public boolean canMove()
	{
		return true;
	}
	
	@Override
	public Color getInvBorderCol()
	{
		return new Color(0, 0, 0, 0);
	}
	
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y);
	}
}