package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemNecklaceScarf extends ItemAccessory
{
	private static Texture tex = Game.loadImage("necklace/scarf");
	
	@Override
	public AccessorySlot getAccessoryType()
	{
		return AccessorySlot.NECKLACE;
	}
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	@Override
	public String getName()
	{
		return "Scarf";
	}
	@Override
	public void tick(Entity entity, boolean equipped) {}
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
		batch.draw(tex, x, y, 32, 32, equipped?32:0, 0, 32, 32, false, false);
	}
}