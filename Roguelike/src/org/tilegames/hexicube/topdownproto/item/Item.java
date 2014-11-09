package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.item.weapon.DamageType;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Item
{
	private boolean needsDeleting;
	public void delete()
	{
		needsDeleting = true;
	}
	public boolean needsDeletion()
	{
		return needsDeleting;
	}
	
	public abstract String[] getCustomActions(Item other);
	public abstract void handleCustomAction(String action, Item other);
	
	public abstract boolean isWeapon();
	
	public abstract DamageType getAttackType();
	
	public abstract ItemModifier getModifier();
	
	public abstract String getName();
	
	public abstract void tick(Entity entity, boolean equipped);
	
	public abstract int getMaxDurability();
	
	public abstract int getCurrentDurability();
	
	public abstract boolean canMove();
	public abstract boolean purify();
	
	public abstract Color getInvBorderCol();
	
	public abstract void render(SpriteBatch batch, int x, int y, boolean equipped);
}