package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemKey extends Item
{
	private static Texture tex = Game.loadImage("item/key");
	private static Texture tex2 = Game.loadImage("item/keyskeleton");
	
	public KeyType type;
	
	public ItemKey(KeyType type)
	{
		this.type = type;
	}
	
	@Override
	public boolean isWeapon()
	{
		return false;
	}
	@Override
	public DamageType getAttackType()
	{
		return null;
	}
	@Override
	public ItemModifier getModifier()
	{
		return null;
	}
	@Override
	public String getName()
	{
		return type+" Key";
	}
	@Override
	public void tick(Entity entity, boolean equipped) {}
	@Override
	public int getMaxDurability()
	{
		return -1;
	}
	@Override
	public int getCurrentDurability()
	{
		return -1;
	}
	@Override
	public boolean canMove()
	{
		return true;
	}
	@Override
	public void render(SpriteBatch batch, int x, int y, boolean equipped)
	{
		if(type == KeyType.SKELETON)
		{
			batch.draw(tex2, x, y);
			return;
		}
		if(type == KeyType.RED) batch.setColor(1, 0, 0, 1);
		else if(type == KeyType.ORANGE) batch.setColor(1, 0.6f, 0, 1);
		else if(type == KeyType.YELLOW) batch.setColor(1, 1, 0, 1);
		else if(type == KeyType.GREEN) batch.setColor(0, 1, 0, 1);
		else if(type == KeyType.BLUE) batch.setColor(0, 0, 1, 1);
		else if(type == KeyType.VIOLET) batch.setColor(0.6f, 0, 1, 1);
		else batch.setColor(0.2f, 0.2f, 0.2f, 1);
		batch.draw(tex, x, y);
		batch.setColor(1, 1, 1, 1);
	}
}