package org.tilegames.hexicube.topdownproto.item.usable;

import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.item.Item;

public abstract class ItemUsable extends Item
{
	public abstract boolean use(Entity source, Direction dir);
	
	public abstract int useDelay();
}