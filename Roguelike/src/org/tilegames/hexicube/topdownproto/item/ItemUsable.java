package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.Entity;

public abstract class ItemUsable extends ItemExpirable
{
	public abstract boolean use(Entity source, int direction);
}