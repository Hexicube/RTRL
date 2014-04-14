package org.tilegames.hexicube.topdownproto.item.armour;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.item.ItemModifier;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemArmourHoodie extends ItemArmour
{
	private static Texture tex = Game.loadImage("armour/hoodie");
	
	@Override
	public double getProtectionMod(DamageType type)
	{
		if(type == DamageType.FIRE) return 1.1;
		if(type == DamageType.ICE) return 0.6;
		return 1;
	}
	
	@Override
	public ArmourSlot getArmourType()
	{
		return ArmourSlot.CHEST;
	}
	
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	
	@Override
	public String getName()
	{
		return "Hoodie";
	}
	
	@Override
	public void tick(Entity entity, boolean equipped)
	{}
	
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
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		batch.draw(tex, x, y);
	}
}