package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Item
{
	public abstract boolean isWeapon();
	
	public abstract DamageType getAttackType();
	
	public abstract ItemModifier getModifier();
	
	public abstract String getName();
	
	public abstract void tick(Entity entity, boolean equipped);
	
	public abstract int getMaxDurability();
	
	public abstract int getCurrentDurability();
	
	public abstract boolean canMove();
	
	public abstract void render(SpriteBatch batch, int x, int y, boolean equipped);
}