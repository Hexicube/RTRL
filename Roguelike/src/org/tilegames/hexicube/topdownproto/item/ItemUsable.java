package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;

public abstract class ItemUsable extends Item
{
	public abstract boolean use(Entity source, Direction dir);
	public abstract int getUseCooldown();
}