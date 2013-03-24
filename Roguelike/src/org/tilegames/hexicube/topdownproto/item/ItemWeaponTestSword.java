package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemWeaponTestSword extends ItemWeapon
{
	private static Texture tex = Game.loadImage("weapon", "badsword");
	
	private int durability;
	private ItemModifier modifier;
	
	public ItemWeaponTestSword()
	{
		int rand = Game.rand.nextInt(10);
		if(rand < 6) modifier = ItemModifier.NONE;
		else if(rand < 9) modifier = ItemModifier.CURSED;
		else modifier = ItemModifier.SHARPENED;
		
		durability = getMaxDurability();
	}
	
	public ItemWeaponTestSword(ItemModifier mod, int dur)
	{
		modifier = mod;
		durability = dur;
	}
	
	@Override
	public boolean use(Entity source, Direction dir)
	{
		//TODO: check for hitting something
		if(durability <= 0) return false;
		durability--;
		return true;
	}
	@Override
	public String getWeaponDamageRange()
	{
		if(modifier == ItemModifier.SHARPENED) return "1d6";
		return "1d4";
	}
	@Override
	public int getMaxDurability()
	{
		return 200;
	}

	@Override
	public int getCurrentDurability()
	{
		return durability;
	}

	@Override
	public DamageType getAttackType()
	{
		return DamageType.SHARP;
	}
	@Override
	public ItemModifier getModifier()
	{
		return modifier;
	}
	@Override
	public String getName()
	{
		if(modifier == null || modifier == ItemModifier.NONE) return "Test Sword";
		if(modifier == ItemModifier.CURSED) return "Cursed Test Sword";
		if(modifier == ItemModifier.RESTRICTIVE)
		{
			modifier = ItemModifier.NONE;
			return "Test Sword";
		}
		if(modifier == ItemModifier.SHARPENED) return "Sharpened Test Sword";
		if(modifier == ItemModifier.WEIGHTED)
		{
			modifier = ItemModifier.NONE;
			return "Test Sword";
		}
		return "???";
	}
	@Override
	public int getItemID()
	{
		return 0;
	}
	@Override
	public void tick(Entity entity, boolean equipped) {}
	@Override
	public boolean canMove()
	{
		return !(modifier == ItemModifier.CURSED);
	}

	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		batch.draw(tex, x, y);
	}
}